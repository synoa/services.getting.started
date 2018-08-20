package de.synoa.getting.started.person.out.file.configurations;

import org.apache.camel.component.mongodb3.MongoDbOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class MongoDB {

    private static final String MONGODB_BASE = "mongodb3:mongo?database={{spring.data.mongodb.database}}&collection=%s&operation=";
    public static final String MONGODB_FIND_ONE_BY_QUERY = MONGODB_BASE + MongoDbOperation.findOneByQuery;

}
