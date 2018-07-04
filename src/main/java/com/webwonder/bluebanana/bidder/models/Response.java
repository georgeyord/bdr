package com.webwonder.bluebanana.bidder.models;

public class Response {
    private String id;
    private Bid bid;

    public Response(String id, Bid bid) {
        this.id = id;
        this.bid = bid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
