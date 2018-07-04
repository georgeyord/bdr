package com.webwonder.bluebanana.bidder.controllers;

import com.webwonder.bluebanana.bidder.models.Campaign;
import com.webwonder.bluebanana.bidder.models.Request;
import com.webwonder.bluebanana.bidder.models.Response;
import com.webwonder.bluebanana.bidder.services.BidService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BidControllerShould {

    private static final String EMPTY_STRING = "";

    @Mock
    Request request;
    @Mock
    BidService bidService;
    @Mock
    Campaign campaign;
    BidController bidController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bidController = new BidController(bidService);
    }

    @Test
    public void alwaysCallBidService() {
        bidController.postBid(request);

        verify(bidService).findBid(request);
    }

    @Test
    public void returnResponseWithBidIfBidServiceFindsOne() {
        when(bidService.findBid(request)).thenReturn(campaign);
        when(campaign.getId()).thenReturn("5a3dce46");
        when(campaign.getPrice()).thenReturn(1.23);

        ResponseEntity response = bidController.postBid(request);
        Response bidResponse = (Response) response.getBody();

        assert (response.getStatusCode()).is2xxSuccessful();
        assertEquals(bidResponse.getBid().getPrice(), 1.23, 2);
        assertEquals(bidResponse.getBid().getCampaignId(), "5a3dce46");
    }

    @Test
    public void returnResponseWithNoContentStatusIfBidServiceFindsNothing() {
        ResponseEntity response = bidController.postBid(request);

        assert (response.getStatusCode()).is2xxSuccessful();
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
        assertNull(response.getBody());
    }
}