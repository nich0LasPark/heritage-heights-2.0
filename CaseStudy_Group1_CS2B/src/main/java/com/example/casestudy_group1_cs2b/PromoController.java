package com.example.casestudy_group1_cs2b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.example.casestudy_group1_cs2b.MainController.username;

public class PromoController {

    @FXML
    public void handleActionButton(ActionEvent actionEvent) throws IOException {
        // Delegate the action to the ButtonHandler class
        ButtonHandler.handleActionButton(actionEvent);
    }
    @FXML
    public void getPromoData(ActionEvent actionEvent) throws IOException{
        System.out.println("Test");
        Button clickedButton = (Button) actionEvent.getSource();

        LabelData labelData = ButtonHandler.getPromoPriceData(clickedButton);

        if (labelData != null) {
            OrderData orderData = new OrderData(labelData.getRideName(), labelData.getRidePrice(), username);
            ButtonHandler.switchScene("lobby.fxml", clickedButton, orderData);

        } else {
            System.out.println("Label data not found.");
        }
    }
}
