package configs;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestConfig {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://mo.trafficfilter.pro/dev/api";
    }

    protected String readFileAsString(String filePath) {
        try {
            return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }

}