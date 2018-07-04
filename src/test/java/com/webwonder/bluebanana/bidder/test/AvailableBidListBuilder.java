package com.webwonder.bluebanana.bidder.test;

import com.webwonder.bluebanana.bidder.enums.Country;
import com.webwonder.bluebanana.bidder.models.Campaign;
import com.webwonder.bluebanana.bidder.models.CampaignBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AvailableBidListBuilder {
    private List<Campaign> bids = new ArrayList<>();

    public AvailableBidListBuilder add(String id, Country countries, float price) {
        this.bids.add(
            new CampaignBuilder()
                .setId(id)
                .setTargetedCountries(Arrays.asList(countries))
                .setPrice(price)
                .createAvailableBid()
        );
        return this;
    }

    public List<Campaign> create() {
        return this.bids;
    }
}
