package com.example.casestudy_group1_cs2b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController{
    public OrderData orderData;
    static String username = "Hello, visitor!";
    @FXML
    private Label greetingsLabel;
    // Method to set the username
    public static void setUsername(String username) {
        MainController.username = "Hello, " + username + "!";
    }

    // This will handle button clicks for Home, Rides, Promos, Tickets, and About
    @FXML
    public void handleActionButton(ActionEvent actionEvent) throws IOException {
        // Delegate the action to the ButtonHandler class
        ButtonHandler.handleActionButton(actionEvent);
    }

    @FXML
    public void getLabelText(ActionEvent actionEvent) throws IOException{
        Button clickedButton = (Button) actionEvent.getSource();

        LabelData labelData = ButtonHandler.getLabelData(clickedButton);

        if (labelData != null) {
            orderData = new OrderData(labelData.getRideName(), labelData.getRidePrice(), username);
            ButtonHandler.switchScene("lobby.fxml", clickedButton, orderData);
        } else {
            System.out.println("Label data not found.");
        }
    }
    @FXML
    public void getPromoData(ActionEvent actionEvent) throws IOException{
        Button clickedButton = (Button) actionEvent.getSource();

        LabelData labelData = ButtonHandler.getPromoPriceData(clickedButton);

        if (labelData != null) {
            orderData = new OrderData(labelData.getRideName(), labelData.getRidePrice(), username);
            ButtonHandler.switchScene("lobby.fxml", clickedButton, orderData);

        } else {
            System.out.println("Label data not found.");
        }
    }

    @FXML
    public void initialize() {
        greetingsLabel.setText(username);
    }
}
