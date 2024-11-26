package api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CampaignApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://mo.trafficfilter.pro/dev/api/campaigns";
    }

    @Test
    public void testCreateCampaignAndVerifyResponse() {
        // JSON-ul campaniei pentru cererea POST
        String campaignJson = "{ " +
                "\"name\": \"CampaignApiTest\", " +
                "\"geo\": \"uk\", " +
                "\"lang\": \"en\", " +
                "\"type\": \"blackhat\", " +
                "\"template_name\": \"template_name\"" +
                "}";
        /*
         * {
         * "name":"tesdajkdksakjdsalkm",
         * "geo":"uk",
         * "lang":"en",
         * "type":"whitehat",
         * "template_name":"standart",
         * "offerIds":[
         * "9d92f04a-276a-4026-9d00-b17791a7a12c"
         * ],
         * "operatorId":
         * "9d93008f-b586-4ed6-b080-a4230cec737b",
         * "benefitIds":[
         * "9d92ca73-5fda-41bf-a9c9-3daf78496792",
         * "9d92ca73-60bf-4690-b41c-cdffce867bf1",
         * "9d92ca73-6102-41e4-b488-91ea86de10d1"
         * 
         * ],
         * "bonuses":[
         * "Bonus 1",
         * "Bonus 2",
         * "Bonus 3",
         * "Bonus 4",
         * 
         * ]
         * }
         */
        // POST
        Response response = given()
                .contentType(ContentType.JSON)
                .body(campaignJson)
                .when()
                .post("")
                .then()
                .statusCode(201)
                .extract()
                .response();

        String campaignId = response.path("id");

        // Get Request
        given()
                .when()
                .get("/" + campaignId)
                .then()
                .log().all()
                .statusCode(200);

        // Delete Request

        given()
                .when()
                .delete("/" + campaignId)
                .then()
                .log().all()
                .statusCode(200);
    }
}