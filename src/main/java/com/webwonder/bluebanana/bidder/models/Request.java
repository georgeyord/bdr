package com.webwonder.bluebanana.bidder.models;

import com.webwonder.bluebanana.bidder.enums.Country;
import com.webwonder.bluebanana.bidder.models.request.App;
import com.webwonder.bluebanana.bidder.models.request.Device;

public class Request {
    private String id;
    private App app;
    private Device device;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Country getCountry() {
        try {
            return getDevice().getGeo().getCountry();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
