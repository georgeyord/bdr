package com.webwonder.bluebanana.bidder.models;

public class ResponseBuilder {
    private String id;
    private Bid bid;

    public ResponseBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ResponseBuilder setBid(Bid bid) {
        this.bid = bid;
        return this;
    }

    public Response createBidResponse() {
        return new Response(id, bid);
    }
}