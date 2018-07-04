package com.webwonder.bluebanana.bidder.integrationtest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.webwonder.bluebanana.bidder.controllers.BidController;
import com.webwonder.bluebanana.bidder.services.BidService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidControllerShould {

    static final String EMTPY_STRING = "";

    static String INPUT_WITH_BID_PATH = "fixtures/with-bid/input.json";
    static String OUTPUT_WITH_BID_PATH = "fixtures/with-bid/expected-output.json";
    static String INPUT_WITHOUT_BID_PATH = "fixtures/without-bid/input.json";
    static String INPUT_WITH_INVALID_COUNTRY_PATH = "fixtures/with-invalid-country/input.json";

    @Autowired
    BidService bidService;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        BidController controller = new BidController(bidService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void respondWithABidForTheHighestPayingCampaignThatMatchesTheTargetingCriteria() throws Exception {
        MvcResult result = mockMvc.perform(post(BidController.BID_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(loadJson(INPUT_WITH_BID_PATH)))
            .andExpect(status().is2xxSuccessful())
            .andDo(print())
            .andExpect(jsonPath("$.id").value("e7fe51ce4f6376876353ff0961c2cb0d"))
            .andExpect(jsonPath("$.bid.campaignId").value("5a3dce46"))
            .andExpect(jsonPath("$.bid.price").value(1.23))
            .andExpect(content().json(loadJson(OUTPUT_WITH_BID_PATH)))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(content, loadJson(OUTPUT_WITH_BID_PATH));
    }

    @Test
    public void respondWithoutABidIfThereNoAvailableOrMatchingCampaigns() throws Exception {
        mockMvc.perform(post(BidController.BID_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(loadJson(INPUT_WITHOUT_BID_PATH)))
            .andExpect(status().is(204))
            .andExpect(content().string(EMTPY_STRING));
    }

    @Test
    public void respondWithA400StatusWhenCountryCodeDoesNotExist() throws Exception {
        mockMvc.perform(post(BidController.BID_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(loadJson(INPUT_WITH_INVALID_COUNTRY_PATH)))
            .andExpect(status().is4xxClientError());
    }

    String loadJson(String path) {
        String json;
        try {
            json = Resources.toString(Resources.getResource(path), Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Could not load json from " + path);
        }
        return json;
    }
}
