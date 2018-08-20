package de.synoa.getting.started.helloworld.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

@ChangeLog
public class SchemaChangelog {

    @ChangeSet(order = "001", author = "synoa", id = "initialVersion")
    public void initalVersion(MongoDatabase database) {

        final MongoCollection<Document> persons = database.getCollection("persons");

        IndexOptions options = new IndexOptions().unique(true);
        persons.createIndex(Indexes.ascending("personalNumber"), options);
    }
}
