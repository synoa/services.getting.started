package de.synoa.getting.started.helloworld.configurations;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.apache.camel.component.mongodb3.MongoDbOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class MongoDB {

    @Autowired
    private MongoClient mongoClient;

    private static final String MONGODB_BASE = "mongodb3:mongo?database={{spring.data.mongodb.database}}&operation=";
    public static final String MONGODB_DB_UPDATE = MONGODB_BASE + MongoDbOperation.update + "&collection=%s";

    @Bean
    public Mongobee mongobee(@Value("${spring.data.mongodb.database}") String database) {

        Mongobee runner = new Mongobee(mongoClient);
        runner.setDbName(database);
        // the package to be scanned for changesets
        runner.setChangeLogsScanPackage("de.synoa.getting.started.helloworld.changelogs");
        // collection with applied change sets
        runner.setChangelogCollectionName("helloworld.changelog");
        // collection used during migration process
        runner.setLockCollectionName("helloworld.lock");

        return runner;
    }
}
