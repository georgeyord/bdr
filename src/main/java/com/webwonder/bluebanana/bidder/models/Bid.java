package com.webwonder.bluebanana.bidder.models;

public class Bid {
    protected String campaignId;
    protected double price;
    protected String adm;

    public Bid() {
    }

    public Bid(String campaignId, double price, String adm) {
        this.campaignId = campaignId;
        this.price = price;
        this.adm = adm;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getAdm() {
        return adm;
    }

}
