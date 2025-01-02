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
    }
    
