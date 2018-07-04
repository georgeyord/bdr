package com.webwonder.bluebanana.bidder.controllers;

import com.webwonder.bluebanana.bidder.models.Campaign;
import com.webwonder.bluebanana.bidder.models.Request;
import com.webwonder.bluebanana.bidder.models.Response;
import com.webwonder.bluebanana.bidder.models.ResponseBuilder;
import com.webwonder.bluebanana.bidder.models.Bid;
import com.webwonder.bluebanana.bidder.models.BidBuilder;
import com.webwonder.bluebanana.bidder.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class BidController {
    public static final String BID_ACTION = "/bid";

    BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @RequestMapping(method = RequestMethod.POST, value = BID_ACTION)
    @ResponseBody
    public ResponseEntity<Response> postBid(@RequestBody Request request) {
        Campaign match = bidService.findBid(request);

        if (match != null) {
            Bid bid = new BidBuilder()
                .setCampaignId(match.getId())
                .setPrice(match.getPrice())
                .setAdm(match.getAdm())
                .createBid();

            Response response = new ResponseBuilder()
                .setId(request.getId())
                .setBid(bid)
                .createBidResponse();

            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.noContent().build();
    }
}
