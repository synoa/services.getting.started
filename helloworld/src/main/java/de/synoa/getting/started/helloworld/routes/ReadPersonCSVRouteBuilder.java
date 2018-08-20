package de.synoa.getting.started.helloworld.routes;

import de.synoa.getting.started.helloworld.handlers.ConvertBirthdayToDate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb3.MongoDbConstants;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

import static com.mongodb.client.model.Filters.eq;
import static de.synoa.getting.started.helloworld.configurations.ActiveMQ.QUEUE_PERSONS_NO_TX;
import static de.synoa.getting.started.helloworld.configurations.MongoDB.MONGODB_DB_UPDATE;
import static java.util.Arrays.asList;
import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class ReadPersonCSVRouteBuilder extends RouteBuilder {

    private static final String PERSONAL_NUMBER = "personalNumber";

    @Autowired
    private CsvDataFormat csv;

    @Override
    public void configure() throws Exception {
        // @formatter:off

        from("file:data/in").routeId("personCSVIn")

            .log(INFO, "Read new file '${header.CamelFileName}'")
            .unmarshal(csv)

            .setHeader(MongoDbConstants.UPSERT, constant(true))
            .split().body()
                .convertBodyTo(Document.class)
                .setProperty(PERSONAL_NUMBER).body(Document.class, p -> p.get(PERSONAL_NUMBER))
                .doTry()
                    .setBody().method(ConvertBirthdayToDate.class)
                    .setBody().exchange(e -> asList(eq(PERSONAL_NUMBER, e.getProperty(PERSONAL_NUMBER)), new Document("$set", e.getIn().getBody())))
                    .toF(MONGODB_DB_UPDATE, "persons")
                    .setBody().exchangeProperty(PERSONAL_NUMBER)
//                    .to(QUEUE_PERSONS)
                    .to(QUEUE_PERSONS_NO_TX)
                .endDoTry()
                .doCatch(ParseException.class)
                    .log(ERROR, "Failed to import Person with Id '${exchangeProperty.personalNumber}': ${exception.message}")
                .end()
            .end()
            .removeHeader(MongoDbConstants.UPSERT)
            .log(INFO, "Finished reading file '${header.CamelFileName}'")
        ;

        // @formatter:on
    }
}
