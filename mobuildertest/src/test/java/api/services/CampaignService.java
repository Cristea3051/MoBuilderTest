package api.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CampaignService {

    public Response createCampaign(String campaignJson) {
        return given()
                .contentType(ContentType.JSON)
                .body(campaignJson)
                .when()
                .post("/campaigns")
                .then()
                .statusCode(201)
                .extract()
                .response();
    }

    public Response getCampaign(String campaignId) {
        return given()
        .accept(ContentType.JSON) // Specifică JSON ca format așteptat
        .when()
        .get("/campaigns/" + campaignId)
        .then()
        .statusCode(200)
        .extract()
        .response();
    }

    public void updateCampaign(String campaignId, String updatedJson) {
        given()
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .when()
                .put("/campaigns/" + campaignId)
                .then()
                .statusCode(200);
    }

    public void deleteCampaign(String campaignId) {
        given()
                .when()
                .delete("/campaigns/" + campaignId)
                .then()
                .statusCode(200);
    }
}

