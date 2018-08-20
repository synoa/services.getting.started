package de.synoa.getting.started.person.out.file.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.synoa.getting.started.person.out.file.aggregate.PersonListAggregationStrategy;
import de.synoa.getting.started.person.out.file.handlers.MapToPerson;
import de.synoa.getting.started.person.out.file.model.Person;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.mongodb.client.model.Filters.eq;
import static de.synoa.getting.started.person.out.file.configurations.ActiveMQ.QUEUE_PERSONS;
import static de.synoa.getting.started.person.out.file.configurations.MongoDB.MONGODB_FIND_ONE_BY_QUERY;
import static org.apache.camel.LoggingLevel.DEBUG;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class JsonOutRouteBuilder extends RouteBuilder {

    private static final String PERSONAL_NUMBER = "personalNumber";

    @Override
    public void configure() throws Exception {
        // @formatter:off

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        final JacksonDataFormat json = new JacksonDataFormat(objectMapper, Person.class);

        from(QUEUE_PERSONS).routeId("jsonOut")

            .log(DEBUG, "Received Person '${body}' to export")

            .setProperty(PERSONAL_NUMBER).body()

            .setBody().body(String.class, no -> eq(PERSONAL_NUMBER, no))
            .toF(MONGODB_FIND_ONE_BY_QUERY, "persons")

            .setBody().method(MapToPerson.class)

            .aggregate(constant(true), new PersonListAggregationStrategy()).completionSize(100).completionTimeout(2000)

                .marshal(json)

                .to("file:data/out?fileName=person-${date:now:yyyy-MM-dd'T'HH:mm:ss.SSSZ}.json")
                .log(INFO, "Written file ${header.CamelFileName}")
            .end()
        ;

        // @formatter:on
    }
}
