package constants;

//This class is created so that it can hold header variables whose values can be used by multiple methods of APIClient class

public class Headers {
    // The variables are declared as final so that the variable value can't change once assigned.
    // The variables are declared as static so that these variables can be used by other class without creating the object of this class (Header class).
    public static final String contentType = "application/json";
    public static final String apikey = "reqres_1e4abb4fd5cf48898049ef9abfcaba9b";
}
