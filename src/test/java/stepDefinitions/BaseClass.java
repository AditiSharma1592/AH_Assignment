package stepDefinitions;

public class BaseClass {

   String BASE_URL = "https://www.rijksmuseum.nl/api";
   String LANGUAGE = "en";

    public static String apiKey() {
        String env = System.getenv("RIJKS_API_KEY");
        return env != null && !env.isBlank() ? env : "0fiuZFh4";
    }
}
