package api.tests;

import configs.TestConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import api.services.Operators.OperatorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

public class OperatorApiTest extends TestConfig {

    private final OperatorService operatorService = new OperatorService();

    @Test
    public void test01_CreateUpdateVerifyAndDeleteOperator() {
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

    // Step 7: Bulk Store Operators
    @Test
    public void test02_BulkStoreOperators() {
        String bulkOperatorsJson = readFileAsString("src/test/resources/Operators/bulkOperators.json");
        Response bulkStoreResponse = operatorService.bulkStoreOperators(bulkOperatorsJson);
        assertThat("Bulk store response status code should be 201", bulkStoreResponse.statusCode(), equalTo(201));

    }
   // Step 8: Bulk Delete Operators
    @Test
    public void test03_BulkDeleteOperators() {
        List<String> operatorNames = List.of("Casino 1 Operator API Test Automation",
                "Casino 2 Operator API Test Automation");
        operatorNames.forEach(name -> {
            String operatorId = operatorService.extractOperatorIdByBrandName(name);
            assertThat("Operator ID should not be null for " + name, operatorId, notNullValue());
        });

        Response deleteResponse = operatorService.bulkDeleteOperatorsByNames(operatorNames);
        assertThat("Bulk delete should be successful", deleteResponse.getStatusCode(), is(200));
    }

}
