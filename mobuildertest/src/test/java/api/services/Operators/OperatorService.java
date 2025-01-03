package api.services.Operators;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    // Metoda pentru a stoca operatori în masă

    public Response bulkStoreOperators(String bulkOperatorsJson) {
        return given()
                .contentType(ContentType.JSON)
                .body(bulkOperatorsJson)
                .when()
                .post("/operators/bulk-store")
                .then()
                .extract()
                .response();
    }

    public List<String> extractOperatorIdsByBrandNames(List<String> brandNames) {
        Response listResponse = getOperators();
        List<Map<String, Object>> operators = listResponse.jsonPath().getList("$");

        return brandNames.stream()
                .map(brandName -> operators.stream()
                        .filter(op -> brandName.equals(op.get("brand_name")))
                        .map(op -> (String) op.get("id"))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    public Response bulkDeleteOperatorsByNames(List<String> brandNames) {
        List<String> operatorIds = extractOperatorIdsByBrandNames(brandNames);

        if (operatorIds.isEmpty()) {
            throw new IllegalArgumentException("No valid operator IDs found for the provided brand names.");
        }

        Map<String, List<String>> requestBody = Map.of("ids", operatorIds);

        System.out.println("Request body: " + requestBody);

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/operators/bulk-delete")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

}
