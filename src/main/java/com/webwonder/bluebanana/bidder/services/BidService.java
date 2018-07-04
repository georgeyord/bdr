package com.webwonder.bluebanana.bidder.services;

import com.webwonder.bluebanana.bidder.enums.Country;
import com.webwonder.bluebanana.bidder.gateways.CampaignGateway;
import com.webwonder.bluebanana.bidder.models.Campaign;
import com.webwonder.bluebanana.bidder.models.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BidService {
    private CampaignGateway campaignGateway;

    @Autowired
    public BidService(CampaignGateway campaignGateway) {
        this.campaignGateway = campaignGateway;
    }

    public Campaign findBid(Request request) {
        List<Campaign> campaigns = campaignGateway.fetch();

        return filterAndMatch(campaigns, request.getCountry());
    }

    private Campaign filterAndMatch(List<Campaign> campaigns, Country country) {
        return campaigns
            .stream()
            .filter(bid -> matchCountry(bid, country))
            .max(Comparator.comparing(Campaign::getPrice))
            .orElse(null);
    }

    private Boolean matchCountry(Campaign bid, Country country) {
        return bid.getTargetedCountries().contains(country);
    }
}
