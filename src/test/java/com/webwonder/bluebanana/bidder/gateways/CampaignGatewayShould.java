package com.webwonder.bluebanana.bidder.gateways;

import com.webwonder.bluebanana.bidder.enums.Country;
import com.webwonder.bluebanana.bidder.models.Campaign;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CampaignGatewayShould {

    CampaignGateway campaignGateway;

    @Before
    public void setUp() throws Exception {
        campaignGateway = new CampaignGateway();
    }

    @Test
    public void fetch() {
        List<Campaign> bids = campaignGateway.fetch();

        assertEquals(5, bids.size());
        assertFalse(bids.isEmpty());

        Campaign bid = bids.get(0);

        assertEquals(bid.getPrice(), 0.45, 2);
        assertEquals(bid.getTargetedCountries(), Arrays.asList(Country.BRA, Country.EGY));
    }
}