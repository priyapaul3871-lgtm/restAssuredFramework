package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;

public class Helper {
    public static String readSchemaJsonFile(String filename) {
        try {
            Path path = Paths.get("src/test/resources/expectedResponse/"+ filename);
            if(!Files.exists(path))
            {
                throw new RuntimeException("JSON file not found: " + path);
            }
            byte[] filedata = Files.readAllBytes(path);
            String expected = new String(filedata);
            return expected;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
