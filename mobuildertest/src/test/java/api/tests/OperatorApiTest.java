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
        operatorService.createOperator(operatorJson);

        // Step 2: Extract Operator ID by Brand Name
        String operatorId = operatorService.extractOperatorIdByBrandName("OperatorApiTest");

        // Step 3: Verify Operator Details
        Response getResponse = operatorService.getOperator(operatorId);
        String operatorName = getResponse.jsonPath().getString("brand_name");
        assertThat(operatorName, equalTo("OperatorApiTest"));

        // Step 4: Update Operator
        String updatedJson = readFileAsString("src/test/resources/Operators/updated_operator.json");
        operatorService.updateOperator(operatorId, updatedJson);

        // Step 5: Verify Updated Operator
        Response updatedResponse = operatorService.getOperator(operatorId);
        String updatedOperatorName = updatedResponse.jsonPath().getString("brand_name");
        assertThat(updatedOperatorName, equalTo("UpdatedOperatorAPITest"));

        // Step 6: Delete Operator
        operatorService.deleteOperator(operatorId);
    }
}
