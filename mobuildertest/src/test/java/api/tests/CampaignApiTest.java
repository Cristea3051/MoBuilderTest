package api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CampaignApiTest {

    private static final String BASE_URL = "https://mo.trafficfilter.pro/dev/api/campaigns";
    private static final String CAMPAIGN_JSON = "{ " +
            "\"name\": \"CampaignApiTest\", " +
            "\"geo\": \"uk\", " +
            "\"lang\": \"en\", " +
            "\"type\": \"blackhat\", " +
            "\"template_name\": \"template_name\"" +
            "}";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreateUpdateVerifyAndDeleteCampaign() {
        // Step 1: Create campaign
        Response response = createCampaign(CAMPAIGN_JSON);
        String campaignId = response.path("id");
        String name = response.path("name");
        String geo = response.path("geo");
        String type = response.path("type");

        // Step 2: Verify campaign details
        verifyCampaignDetails(campaignId, name, geo, type);

        // Step 3: Update campaign
        String updatedName = "UpdatedCampaignName";
        updateCampaign(campaignId, updatedName);

        // Step 4: Verify updated campaign
        verifyUpdatedCampaign(campaignId, updatedName);

        // Step 5: Delete campaign
        deleteCampaign(campaignId);
    }

    private Response createCampaign(String campaignJson) {
        return given()
                .contentType(ContentType.JSON)
                .body(campaignJson)
                .when()
                .post("")
                .then()
                .statusCode(201)
                .extract()
                .response();
    }

    private void verifyCampaignDetails(String campaignId, String name, String geo, String type) {
        given()
                .when()
                .get("/" + campaignId)
                .then()
                .log().all()
                .statusCode(200);

        assertThat(name, equalTo("CampaignApiTest"));
        assertThat(geo, equalTo("uk"));
        assertThat(type, equalTo("blackhat"));
    }

    private void updateCampaign(String campaignId, String updatedName) {
        String updatedCampaignJson = "{ " +
                "\"name\": \"" + updatedName + "\", " +
                "\"geo\": \"uk\", " +
                "\"lang\": \"en\", " +
                "\"type\": \"blackhat\", " +
                "\"template_name\": \"template_name\"" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(updatedCampaignJson)
                .when()
                .put("/" + campaignId)
                .then()
                .log().all()
                .statusCode(200);
    }

    private void verifyUpdatedCampaign(String campaignId, String name) {
        given()
                .when()
                .get("/" + campaignId)
                .then()
                .log().all()
                .statusCode(200);

        assertThat(name, equalTo("UpdatedCampaignName"));
    }
    private void deleteCampaign(String campaignId) {
        given()
                .when()
                .delete("/" + campaignId)
                .then()
                .log().all()
                .statusCode(200);
    }
}
