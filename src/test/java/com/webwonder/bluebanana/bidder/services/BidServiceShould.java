package com.webwonder.bluebanana.bidder.services;

import com.webwonder.bluebanana.bidder.enums.Country;
import com.webwonder.bluebanana.bidder.gateways.CampaignGateway;
import com.webwonder.bluebanana.bidder.models.Campaign;
import com.webwonder.bluebanana.bidder.models.Request;
import com.webwonder.bluebanana.bidder.test.AvailableBidListBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BidServiceShould {

    @Mock
    Request request;
    @Mock
    CampaignGateway campaignGateway;
    BidService bidService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bidService = new BidService(campaignGateway);
    }

    @Test
    public void alwaysCallCampaignApi() {
        bidService.findBid(request);

        verify(campaignGateway).fetch();
    }

    @Test
    public void matchOnlyAvailableBidsFromASpecificCountry() {
        when(request.getCountry()).thenReturn(Country.CYP);
        when(campaignGateway.fetch()).thenReturn(
            new AvailableBidListBuilder()
                .add("bar", Country.USA, 2)
                .add("foo", Country.CYP, 1)
                .create()
        );

        Campaign campaign = bidService.findBid(request);

        assertEquals(campaign.getId(), "foo");
    }

    @Test
    public void matchTheHighestPriceOfTheAvailableBidsFromASpecificCountry() {
        when(request.getCountry()).thenReturn(Country.USA);
        when(campaignGateway.fetch()).thenReturn(
            new AvailableBidListBuilder()
                .add("bar", Country.USA, 7)
                .add("foo", Country.CYP, 3)
                .add("abc", Country.USA, 4)
                .add("lala", Country.CYP, 1)
                .add("lol", Country.USA, 8)
                .add("moo", Country.CYP, 2)
                .create()
        );

        Campaign campaign = bidService.findBid(request);

        assertEquals(campaign.getId(), "lol");
    }

    @Test
    public void returnNullInNoAvailableBidsExistFromASpecificCountry() {
        when(request.getCountry()).thenReturn(Country.EGY);
        when(campaignGateway.fetch()).thenReturn(
            new AvailableBidListBuilder()
                .add("bar", Country.USA, 2)
                .add("foo", Country.CYP, 1)
                .create()
        );

        Campaign campaign = bidService.findBid(request);

        assertNull(campaign);
    }
}