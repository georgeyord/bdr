package com.webwonder.bluebanana.bidder.models.request;

import com.webwonder.bluebanana.bidder.enums.Country;

public class Geolocation {
    private Country country;
    private float lat;
    private float lon;

    public Geolocation() {
    }

    public Geolocation(Country country, float lat, float lon) {
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
