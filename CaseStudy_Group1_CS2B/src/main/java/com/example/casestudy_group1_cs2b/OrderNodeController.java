package com.example.casestudy_group1_cs2b;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class OrderNodeController{

    @FXML
    private HBox orderNode; // The root node of this OrderNode (e.g., an HBox or VBox)
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField rideQtyTextField;
    @FXML
    private Button plusButton; // renamed from addButton
    @FXML
    private Button minusButton; // renamed from updateButton
    @FXML
    private Button deleteButton; // renamed from removeButton

    private TicketsController ticketsController;
    ArrayList<OrderData> orderList = new ArrayList<>();

    private OrderData orderData;


    public HBox getRideNode() {
        return orderNode;
    }
    public void setTicketsController(TicketsController ticketsController) {
        this.ticketsController = ticketsController;
    }
    public void setOrderData(OrderData orderData){
        this.orderData = orderData;
    }
    // Method to calculate and update the total

    @FXML
    public void initialize() {
        // Button event handlers
        deleteButton.setOnAction(event -> handleRemove());
        DelayHandler.executeWithDelay(() -> {
            if(orderData.getQuantity() == 0){
                rideQtyTextField.setText("1");
                nameLabel.setText(orderData.getRideName());
                priceLabel.setText(String.valueOf(orderData.getCurrentPriceByName(orderData.getRideName())));
            }
            else{
                nameLabel.setText(orderData.getRideName());
                rideQtyTextField.setText(String.valueOf(orderData.getQuantity()));
                int initialPrice = orderData.getQuantity() * orderData.getCurrentPriceByName(orderData.getRideName());
                System.out.println(initialPrice);
                priceLabel.setText(String.valueOf(initialPrice));

            }

            // Add a listener to the qtyTextField to update the price when changed
            rideQtyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (isValidQuantity(newValue)) {
                    int quantity = Integer.parseInt(newValue);
                    // Update the price based on the new quantity
                    int newPrice = orderData.getCurrentPriceByName(orderData.getRideName()) * quantity;
                    priceLabel.setText(String.valueOf(newPrice));
                } else {
                    // Optionally reset the price if the input is invalid
                    priceLabel.setText("Invalid Quantity");
                }
            });
        }, 100);
    }
    public void setOrderList(ArrayList<OrderData> orderList){
        this.orderList = orderList;
    }

    public void handleRemove() {
        if (ticketsController != null) {
            // Update the order list in the tickets controller and recalculate total
            ticketsController.updateTotal(Integer.parseInt(priceLabel.getText()));

            ticketsController.removeFromSelectedRides(nameLabel.getText(), Integer.parseInt(priceLabel.getText())); // Update any other necessary data
            ticketsController.removeOrderNode(this); // Remove the UI node
        } else {
            System.out.println("Parent controller not set.");
        }
    }


    // Validate that the quantity entered is a valid number
    private boolean isValidQuantity(String qtyText) {
        try {
            int quantity = Integer.parseInt(qtyText);
            return quantity > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
