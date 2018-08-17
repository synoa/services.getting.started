package de.synoa.getting.started.helloworld.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;

@ChangeLog
public class SchemaChangelog {

    @ChangeSet(order = "001", author = "genisys.archetype", id = "initialVersion")
    public void initalVersion(MongoDatabase database) {
        database.createCollection("helloworld");
    }
}
