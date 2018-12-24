package com.github.tax1driver.sectormanager.objects;

import java.util.HashMap;
import java.util.List;

public class Configuration {
    private String mongoDBAddress;
    private String mongoDBPassword;
    private String mongoDBDatabaseName;

    private HashMap<String, List<String>> publicKeys;

    public HashMap<String, List<String>> getPublicKeys() {
        return publicKeys;
    }

    public String getMongoDBAddress() {
        return mongoDBAddress;
    }

    public void setMongoDBAddress(String mongoDBAddress) {
        this.mongoDBAddress = mongoDBAddress;
    }

    public String getMongoDBPassword() {
        return mongoDBPassword;
    }

    public void setMongoDBPassword(String mongoDBPassword) {
        this.mongoDBPassword = mongoDBPassword;
    }

    public String getMongoDBDatabaseName() {
        return mongoDBDatabaseName;
    }

    public void setMongoDBDatabaseName(String mongoDBDatabaseName) {
        this.mongoDBDatabaseName = mongoDBDatabaseName;
    }
}
