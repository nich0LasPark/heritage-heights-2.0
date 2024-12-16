package com.example.casestudy_group1_cs2b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.example.casestudy_group1_cs2b.MainController.username;

public class RideController{

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
            OrderData orderData = new OrderData(labelData.getRideName(), labelData.getRidePrice(), username);
            ButtonHandler.switchScene("lobby.fxml", clickedButton, orderData);

        } else {
            System.out.println("Label data not found.");
        }
    }
}



