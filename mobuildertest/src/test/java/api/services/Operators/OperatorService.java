package api.services.Operators;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OperatorService {

    public Response createOperator(String operatorJson) {
        return given()
                .contentType(ContentType.JSON)
                .body(operatorJson)
                .when()
                .post("/operators")
                .then()
                .statusCode(201)
                .extract()
                .response();
    }

    public Response getOperator(String operatorId) {
        return given()
                .accept(ContentType.JSON) // Specifică JSON ca format așteptat
                .when()
                .get("/operators/" + operatorId)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public void updateOperator(String operatorId, String updatedJson) {
        given()
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .when()
                .put("/operators/" + operatorId)
                .then()
                .statusCode(200);
    }

    public void deleteOperator(String operatorId) {
        given()
                .when()
                .delete("/operators/" + operatorId)
                .then()
                .statusCode(200);
    }

    // Obține lista de operatori
    public Response getOperators() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/operators")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    // Găsește operatorul după `brand_name` și returnează ID-ul
    public String extractOperatorIdByBrandName(String brandName) {
        Response listResponse = getOperators();
        return listResponse.jsonPath()
                .getString("find { it.brand_name == '" + brandName + "' }.id");
    }
}
