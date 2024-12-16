package com.example.casestudy_group1_cs2b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class AboutController {
    @FXML
    public void handleActionButton(ActionEvent actionEvent) throws IOException {
        // Delegate the action to the ButtonHandler class
        ButtonHandler.handleActionButton(actionEvent);
    }
}
