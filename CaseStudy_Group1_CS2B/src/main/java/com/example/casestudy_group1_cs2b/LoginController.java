package com.example.casestudy_group1_cs2b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void handleActionButton(ActionEvent actionEvent) throws IOException {
        // Get the source of the action event (button)
        Button sourceButton = (Button) actionEvent.getSource();

        // Get the text of the button
        String buttonText = sourceButton.getText();
        if (buttonText.equals("Login") && hasCorrectInput()) {
            MainController.setUsername("User");
            ButtonHandler.switchScene("main.fxml", sourceButton, null);
        }
        else if(buttonText.equals("Register")){
            ButtonHandler.switchScene("register.fxml", sourceButton, null);
        }

    }
    public boolean hasCorrectInput() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        // Validate the email and password
        if (email.equals("usersample123") && password.equals("userpassword456")) {
            return true;
        } else {
            // Optionally, show an error message if the login fails
            AlertHandler.showErrorAlert("Invalid input", "Sorry your username or password must be correct.");
            return false;
        }
    }

}
