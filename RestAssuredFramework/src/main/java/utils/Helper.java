package utils;
import java.io.IOException;
import java.nio.file.Files;                     //nio stands for New Input/Output
import java.nio.file.Path;
import java.nio.file.Paths;


public class Helper {
    public static String readJsonResponseFile(String filename) {
        try {

            //Path is an interface from the java.nio.file package. It represents the location of a file or directory.
            // Paths is a utility class which provides methods to create a Path object.
            // The get() method converts a String path into a Path object.

            Path path = Paths.get("src/test/resources/expectedResponse/"+ filename);    //creates the complete path (location) of the file that you want to read.
            if(!Files.exists(path))                 //Checks whether the file exists.
            {
                throw new RuntimeException("JSON file not found: " + path);
            }
            byte[] filedata = Files.readAllBytes(path);         //Reads the entire file into a byte array.
            String expected = new String(filedata);             //Converts the byte array into a String.
            return expected;
        }
        catch (IOException e) {
            throw new RuntimeException(e);                      //It fails the test and stops the execution immediately.
        }

    }
}
