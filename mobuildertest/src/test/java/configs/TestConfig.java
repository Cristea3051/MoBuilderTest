package configs;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestConfig {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://mo.trafficfilter.pro/dev/api";
    }

    protected String readFileAsString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }
}
