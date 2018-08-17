package de.synoa.getting.started.helloworld.routes;

import de.synoa.getting.started.helloworld.handlers.ConvertBirthdayToDate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

import static de.synoa.getting.started.helloworld.configurations.MongoDB.MONGODB_DB_INSERT;
import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class ReadPersonCSVRouteBuilder extends RouteBuilder {

    @Autowired
    private CsvDataFormat csv;

    @Override
    public void configure() throws Exception {
        // @formatter:off

        from("file:data/in").routeId("personCSVIn")

            .log(INFO, "Read new file '${header.CamelFileName}'")
            .unmarshal(csv)

            .split().body()
                .convertBodyTo(Document.class)
                .setProperty("personalNumber").body(Document.class, p -> p.get("personalNumber"))
                .doTry()
                    .setBody().method(ConvertBirthdayToDate.class)
                    .toF(MONGODB_DB_INSERT, "persons")
                .endDoTry()
                .doCatch(ParseException.class)
                    .log(ERROR, "Failed to import Person with Id '${exchangeProperty.personalNumber}': ${exception.message}")
                .end()
            .end()
            .log(INFO, "Finished reading file '${header.CamelFileName}'")
        ;

        // @formatter:on
    }
}
