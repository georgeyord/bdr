package com.webwonder.bluebanana.bidder.models;

public class BidBuilder {
    private String campaignId;
    private double price;
    private String adm;

    public BidBuilder setCampaignId(String campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public BidBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public BidBuilder setAdm(String adm) {
        this.adm = adm;
        return this;
    }

    public Bid createBid() {
        return new Bid(campaignId, price, adm);
    }
}