package com.example.casestudy_group1_cs2b;

public class LabelData {
    private final String rideName;
    private final int ridePrice;

    public LabelData(String rideName, int ridePrice) {
        this.rideName = rideName;
        this.ridePrice = ridePrice;
    }

    public String getRideName() {
        return rideName;
    }

    public int getRidePrice() {
        return ridePrice;
    }
}

