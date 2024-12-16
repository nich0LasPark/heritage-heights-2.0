package com.example.casestudy_group1_cs2b;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHandler {
    public static void showInfoAlert() {
        // Create an Alert of type INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Notice");
        alert.setHeaderText("Time's up");
        alert.setContentText("Sorry, you ran out of time to fill out the form");

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }

    public static void showWarningAlert() {
        // Create an Alert of type WARNING
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Alert");
        alert.setHeaderText("Warning Header");
        alert.setContentText("This is a warning alert!");

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }

    public static void showErrorAlert(String header, String content) {
        // Create an Alert of type ERROR
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }

    public static boolean showConfirmationAlert() {
        // Create an Alert of type CONFIRMATION
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("You're going to lose your line");
        alert.setContentText("Are you sure you want to continue?");

        // Show the alert and wait for the user to respond
        Optional<ButtonType> result = alert.showAndWait();

        // Check the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("User chose OK.");
            return true; // User confirmed
        } else {
            System.out.println("User cancelled or closed the dialog.");
            return false; // User cancelled
        }
    }
}
