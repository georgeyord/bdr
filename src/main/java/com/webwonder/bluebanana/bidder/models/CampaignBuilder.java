package com.webwonder.bluebanana.bidder.models;

import com.webwonder.bluebanana.bidder.enums.Country;

import java.util.List;

public class CampaignBuilder {
    private String id;
    private String name;
    private double price;
    private String adm;
    private List<Country> targetedCountries;

    public CampaignBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CampaignBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CampaignBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public CampaignBuilder setAdm(String adm) {
        this.adm = adm;
        return this;
    }

    public CampaignBuilder setTargetedCountries(List<Country> targetedCountries) {
        this.targetedCountries = targetedCountries;
        return this;
    }

    public Campaign createAvailableBid() {
        return new Campaign(id, name, price, adm, targetedCountries);
    }
}