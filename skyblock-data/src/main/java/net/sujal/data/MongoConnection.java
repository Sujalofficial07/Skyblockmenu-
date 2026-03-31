// Path: /skyblock-data/src/main/java/net/sujal/data/MongoConnection.java
package net.sujal.data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.sujal.api.service.Service;

public class MongoConnection implements Service {

    private final String connectionString;
    private final String databaseName;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoConnection(String connectionString, String databaseName) {
        this.connectionString = connectionString;
        this.databaseName = databaseName;
    }

    @Override
    public void onEnable() {
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase(databaseName);
    }

    @Override
    public void onDisable() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
