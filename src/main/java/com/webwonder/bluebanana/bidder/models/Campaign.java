package com.webwonder.bluebanana.bidder.models;

import com.webwonder.bluebanana.bidder.enums.Country;

import java.util.List;

public class Campaign {
    private String id;
    private String name;
    private double price;
    private String adm;
    private List<Country> targetedCountries;

    public Campaign() {
    }

    public Campaign(String id, String name, double price, String adm, List<Country> targetedCountries) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.adm = adm;
        this.targetedCountries = targetedCountries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public List<Country> getTargetedCountries() {
        return targetedCountries;
    }

    public void setTargetedCountries(List<Country> targetedCountries) {
        this.targetedCountries = targetedCountries;
    }
}
