package com.example.casestudy_group1_cs2b;

import java.util.Date;

public class OrderData {
    private String rideName;
    private int ridePrice;
    private String userName;
    private String firstName;
    private String lastName;
    private int age;
    private String emailAddress;
    private long contactNo;
    private int quantity;

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    private int totalAmount;
    private String ModeOfPayment;
    private Date date;


    public OrderData(String rideName, int ridePrice, String userName) {
        this.rideName = rideName;
        this.ridePrice = ridePrice;
        this.userName = userName;
    }

    public int getRidePrice() {
        return ridePrice;
    }
    public String getRideName() {
        return rideName;
    }
    public String getUserName() {
        return userName;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setRideName(String rideName){
        this.rideName = rideName;
    }
    public void setRidePrice(int ridePrice) {
        this.ridePrice = ridePrice;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    // Method to get the current price by ride name
    public int getCurrentPriceByName(String rideName) {
        // Here, you can add a list or database of rides and prices.
        // For simplicity, this will just return a price for a specific name.
        return switch (rideName.toLowerCase()) {
            case "roller coaster" -> 250;
            case "ferris wheel" -> 250;
            case "fun slide" -> 200;
            case "carousel" -> 150;
            case "bump cars" -> 200;
            case "drop tower" -> 200;
            case "rides all you want" -> 1100;
            case "holiday special" -> 875;
            case "barkada bonding" -> 1050;
            case "couple goals" -> 1175;
            case "weekend adventure" -> 1000;
            default -> 0; // Return 0 if ride name is not recognized
        };
    }

}
