package config;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;


public class ConfigReader {

//Reading properties from config.properties file present in the RestAssuredFramework project folder
    /*public static String readPropertyFile(String propertyName) {
        try {
            //Creates a Properties object.
            Properties prop = new Properties();   //The Properties object stores file content as key-value pairs.

            //Creates a FileReader which opens the config.properties file
            FileReader fr = new FileReader("config.properties");

            //It reads the contents of the file and loads them into the Properties object.
            prop.load(fr);

            //Retrieves the value associated with the specified key.
            return prop.getProperty(propertyName);

        }
        //Instead of forcing every caller to handle IOException, it converts it into an unchecked RuntimeException. The test fails immediately if the configuration file cannot be read.
        catch (IOException e) {
            throw new RuntimeException(e);  //It fails the test and stops the execution immediately.
        }*/

    //Reading properties from QA.properties file present in the Properties folder under src/test
    public static String getConfigFromMVN(String propertyName) {
        try {
            String env = System.getProperty("Environment", "QA");
            Properties prop = new Properties();
            FileReader fr = new FileReader("src/test/Properties/"+env+".properties");
            prop.load(fr);
            return prop.getProperty(propertyName);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
