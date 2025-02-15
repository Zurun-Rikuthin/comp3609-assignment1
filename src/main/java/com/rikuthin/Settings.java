package com.rikuthin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static final String SETTINGS_FILE_PATH = "settings.txt";

    private static Settings instance;

    private final Properties properties;

    private Settings() {
        properties = new Properties();
        loadSettings();
    }

    public static synchronized Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    // Load settings from the file
    private void loadSettings() {
        File settingsFile = new File(SETTINGS_FILE_PATH);
        if (settingsFile.exists()) {
            try (FileInputStream inputStream = new FileInputStream(settingsFile)) {
                properties.load(inputStream);
            } catch (IOException e) {
                System.out.println("Error loading settings file: " + e.getMessage());
            }
        }
    }

    // Save settings to the file
    public void saveSettings() {
        try (FileOutputStream outputStream = new FileOutputStream(SETTINGS_FILE_PATH)) {
            properties.store(outputStream, "Game Settings");
        } catch (IOException e) {
            System.out.println("Error saving settings file: " + e.getMessage());
        }
    }

    // Get a setting value
    public String getSetting(String key) {
        return properties.getProperty(key);
    }

    // Set a setting value
    public void setSetting(String key, String value) {
        properties.setProperty(key, value);
    }

    // // Example methods for specific settings
    // public int getVolume() {
    //     String volumeStr = properties.getProperty("volume", "50");  // default volume to 50
    //     return Integer.parseInt(volumeStr);
    // }

    // public void setVolume(int volume) {
    //     setSetting("volume", String.valueOf(volume));
    // }
}
