package com.webwonder.bluebanana.bidder.models.request;

import com.webwonder.bluebanana.bidder.enums.OS;

public class Device {
    private OS os;
    private Geolocation geo;

    public Device() {
    }

    public Device(OS os, Geolocation geo) {
        this.os = os;
        this.geo = geo;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public Geolocation getGeo() {
        return geo;
    }

    public void setGeo(Geolocation geo) {
        this.geo = geo;
    }
}
