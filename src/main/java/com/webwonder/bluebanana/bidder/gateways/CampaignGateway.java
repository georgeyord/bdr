package com.webwonder.bluebanana.bidder.gateways;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.webwonder.bluebanana.bidder.models.Campaign;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CampaignGateway {

    /**
     * Mock service since it is out of the scope
     *
     * @return
     */
    public List<Campaign> fetch() {
        List<Campaign> bids;
        String path = "fixtures/mock-campaign-api-response.json";

        try {
            String json = Resources.toString(Resources.getResource(path), Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            bids = objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, Campaign.class));
        } catch (IOException e) {
            throw new RuntimeException("Could not load json from " + path);
        }

        return bids;
    }
}
