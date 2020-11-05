package com.amazon;

import com.amazon.util.Constants;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class GlobalResource {

    private static Properties properties = null;
    private static GlobalResource instance = null;

    final static Logger logger =  Logger.getLogger(GlobalResource.class);
    private GlobalResource() {}

    /**
     * Create a singleton class and load the config.properties file
     * config.properties file contain
     * desired capabilities, screenshot dir path, implicit & explicit wait
     *
     * @return Returns an object of GlobalResource
     */
    public static GlobalResource getInstance() {
        if(instance == null) {
            logger.info("Create global instance: start");
            instance = new GlobalResource();
            FileReader reader = null;
            try {
                reader = new FileReader(Constants.DRIVER_PROPERTIES_FILE_PATH);
                properties = new Properties();
                properties.load(reader);
            } catch (FileNotFoundException e) {
                logger.error("properties file not found file --> "+Constants.DRIVER_PROPERTIES_FILE_PATH, e);
                System.exit(0);
            } catch (Exception e) {
                logger.error("error reading properties file --> "+Constants.DRIVER_PROPERTIES_FILE_PATH, e);
                System.exit(0);
            }
            logger.info("appium server url : "+properties.getProperty("appium.url"));
            logger.info("Create global instance: end");
        }
        return instance;
    }

    /**
     * Returns the properties object which contains the config.properties content
     * and the data in it may overwritten based on the command line args passed
     * @return the properties object
     */
    public Properties getProperties() {
        // Over writes the default appium url if passed as an argument along with maven command
        if (System.getProperty("appium.url") != null) {
            properties.setProperty("appium.url", System.getProperty("appium.url"));
        }
        return properties;
    }
}
