package com.github.tax1driver.sectormanager.helpers;

import com.github.tax1driver.sectormanager.objects.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

public class ConfigurationManager {
    private static Configuration config;

    public static void initialize() {
        try {
            config = GsonHelper.gson().fromJson(new FileReader("config.json"), Configuration.class);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void save() {
        String json = GsonHelper.gson().toJson(config, Configuration.class);

        try {
            FileWriter writer = new FileWriter("config.json");
            writer.write(json);
            writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Configuration getConfig() {
        return config;
    }
}
