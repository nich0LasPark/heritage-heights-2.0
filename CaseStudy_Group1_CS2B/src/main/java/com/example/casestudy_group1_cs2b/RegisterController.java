package com.example.casestudy_group1_cs2b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterController {

    // FXML fields for the username, password, and re-enter password
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField reenterPasswordField; // Re-enter password field


    public void handleActionButton(ActionEvent actionEvent) throws IOException {
        // Get the source of the action event (button)
        Button sourceButton = (Button) actionEvent.getSource();

        // Get the text of the button
        String buttonText = sourceButton.getText();
        if (buttonText.equals("Login")) {
            ButtonHandler.switchScene("login.fxml", sourceButton, null);
        } else {
            // If registration is successful, go to the main scene
            if (validateRegistration()) {
                MainController.setUsername(usernameField.getText());
                ButtonHandler.switchScene("main.fxml", sourceButton, null);
            }
        }
    }

    // Method to validate the registration form
    public boolean validateRegistration() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String reenteredPassword = reenterPasswordField.getText();

        // Validate username length (at least 8 characters)
        if (username.length() < 8) {
            AlertHandler.showErrorAlert("Invalid input","Username must be at least 8 characters long." );
            return false;
        }

        // Validate password length and conditions (at least 8 characters, 1 uppercase, 1 number, 1 special character)
        if (password.length() < 8) {
            AlertHandler.showErrorAlert("Invalid input","Password must be at least 8 characters long.");
            return false;
        }
        if (!containsUppercase(password)) {
            AlertHandler.showErrorAlert("Invalid input","Password must contain at least one uppercase letter.");
            return false;
        }
        if (!containsNumber(password)) {
            AlertHandler.showErrorAlert("Invalid input","Password must contain at least one number.");
            return false;
        }

        if (!containsSpecialCharacter(password)) {
            AlertHandler.showErrorAlert("Invalid input",("Password must contain at least one special character."));
            return false;
        }

        // Validate that the passwords match
        if (!password.equals(reenteredPassword)) {
            AlertHandler.showErrorAlert("Invalid input",("Passwords do not match."));
            return false;
        }

        // If all validations pass
        return true;
    }

    // Helper methods to check for uppercase, number, and special character
    private boolean containsUppercase(String password) {
        return Pattern.compile("[A-Z]").matcher(password).find();
    }

    private boolean containsNumber(String password) {
        return Pattern.compile("[0-9]").matcher(password).find();
    }

    private boolean containsSpecialCharacter(String password) {
        return Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
    }

}