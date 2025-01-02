package api.tests;

import configs.TestConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import api.services.Operators.OperatorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OperatorApiTest extends TestConfig {

    private final OperatorService operatorService = new OperatorService();

    @Test
    public void testCreateUpdateVerifyAndDeleteOperator() {
        // Step 1: Create Operator
        String operatorJson = readFileAsString("src/test/resources/Operators/operator.json");
        Response createResponse = operatorService.createOperator(operatorJson);

        // Adaugă acest syout aici
        System.out.println("Operator create response body: " + createResponse.asString());
        
        // Apoi încearcă să extragi id-ul
        if (createResponse.path("id") == null) {
            throw new AssertionError("Response does not contain 'id'. Response body: " + createResponse.asString());
        }
        
        String operatorId = createResponse.path("id");

        System.out.println("Operator ID: " + operatorId);

        // Step 2: Verify Operator Details
        Response getResponse = operatorService.getOperator(operatorId);
        String operatorName = getResponse.jsonPath().getString("brand_name");
        assertThat(operatorName, equalTo("OperatorApiTest"));

        // Step 3: Update Operator
        String updatedJson = readFileAsString("src/test/resources/Operators/updated_operator.json");
        operatorService.updateOperator(operatorId, updatedJson);

        // Step 4: Verify Updated Operator
        Response updatedResponse = operatorService.getOperator(operatorId);
        String updatedoperatorName = updatedResponse.jsonPath().getString("brand_name");
        assertThat(updatedoperatorName, equalTo("UpdatedOperatorAPITest"));

        // Step 5: Delete Operator
        operatorService.deleteOperator(operatorId);
    }
}