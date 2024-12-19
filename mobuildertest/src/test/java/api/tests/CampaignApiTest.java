package api.tests;

import configs.TestConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import api.services.Campaigns.CampaignService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CampaignApiTest extends TestConfig {

    private final CampaignService campaignService = new CampaignService();

    @Test
    public void testCreateUpdateVerifyAndDeleteCampaign() {
        // Step 1: Create Campaign
        String campaignJson = readFileAsString("src/test/resources/Campaigns/campaign.json");
        Response createResponse = campaignService.createCampaign(campaignJson);
        String campaignId = createResponse.path("id");

        // Step 2: Verify Campaign Details
        Response getResponse = campaignService.getCampaign(campaignId);
        String campaignName = getResponse.jsonPath().getString("name");
        assertThat(campaignName, equalTo("CampaignApiTest"));

        // Step 3: Update Campaign
        String updatedJson = readFileAsString("src/test/resources/Campaigns/updated_campaign.json");
        campaignService.updateCampaign(campaignId, updatedJson);

        // Step 4: Verify Updated Campaign
        Response updatedResponse = campaignService.getCampaign(campaignId);
        String updatedcampaignName = updatedResponse.jsonPath().getString("name");
        assertThat(updatedcampaignName, equalTo("UpdatedCampaignName"));

        // Step 5: Delete Campaign
        campaignService.deleteCampaign(campaignId);
    }
}