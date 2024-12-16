package com.example.casestudy_group1_cs2b;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class ButtonHandler {

    public static void handleActionButton(ActionEvent actionEvent) throws IOException {
        // Get the source of the action event (button)
        Button sourceButton = (Button) actionEvent.getSource();

        // Get the text of the button
        String buttonText = sourceButton.getText();

        // Use a switch case to handle different button texts
        switch (buttonText) {
            case "Home", "Cancel":
                // Do something when "Home" button is clicked
                switchScene("main.fxml", sourceButton,null);
                break;
            case "Rides", "SHOW ALL RIDES →":
                // Do something when "Rides button is clicked
                switchScene("rides.fxml", sourceButton, null);
                break;
            case "Promos", "SHOW ALL PROMOS →":
                switchScene("promos.fxml", sourceButton, null);
                break;
            case "Tickets":
                AlertHandler.showErrorAlert("", "Pick an order first.");
                break;
            case "PROCEED":
                switchScene("tickets.fxml", sourceButton, null);
                break;
            case "About":
                switchScene("about.fxml", sourceButton, null);
                break;
            case "Login":
                switchScene("login.fxml", sourceButton, null);
                break;
            case "Sign up":
                switchScene("register.fxml", sourceButton, null);
                break;
            default:
                System.out.println("Unknown button clicked");
                break;
        }
    }

    public static void switchScene(String fxmlFile, Node eventSource, OrderData orderData) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(ButtonHandler.class.getResource(fxmlFile));

        // Load the parent (root node) of the new FXML scene
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) eventSource.getScene().getWindow();

        Object controller = loader.getController();
        // Check if the controller implements DataReceiver
        if (controller instanceof DataReceiver) {
            ((DataReceiver) controller).setOrderData(orderData);
        }

        // Create a new scene with the new FXML root
        Scene scene = new Scene(root);

        // Set the new scene to the stage
        stage.setScene(scene);


        // Show the stage
        stage.show();
    }

    public static LabelData getPromoPriceData(Button clickedButton) {
        // Get the parent container (VBox in this case)
        VBox parent = (VBox) clickedButton.getParent();

        // Find the sibling VBox (sibling of the clicked button)
        int clickedIndex = parent.getChildren().indexOf(clickedButton);
        if (clickedIndex > 0) {
            // Get the sibling node (assumed to be a VBox)
            VBox siblingVBox = (VBox) parent.getChildren().get(clickedIndex - 1);

            // Ensure the sibling VBox has at least two children
            if (siblingVBox.getChildren().size() >= 2) {
                // Get the first child (assumed to be a label or similar node containing the price)
                var firstChild = siblingVBox.getChildren().get(0);

                // Check if the first child is a Label containing the price
                if (firstChild instanceof Label priceLabel) {
                    String priceText = priceLabel.getText();

                    // Remove the peso sign and parse the remaining string as an integer
                    if (priceText.startsWith("₱")) {
                        priceText = priceText.substring(1); // Remove the peso sign
                    }

                    try {
                        // Parse the remaining string as an integer for the price
                        int promoPrice = Integer.parseInt(priceText);

                        // Get the second child (assumed to be a label containing the promoName)
                        var secondChild = siblingVBox.getChildren().get(1);

                        // Check if the second child is a Label containing the promo name
                        if (secondChild instanceof Label nameLabel) {
                            String promoName = nameLabel.getText();

                            // Return the LabelData instance with the promo name and price
                            return new LabelData(promoName, promoPrice);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price format: " + priceText);
                    }
                }
            }
        }

        System.out.println("Could not retrieve promo price or name.");
        return null;
    }


    public static LabelData getLabelData(Button clickedButton) {
        // Get the parent container (VBox in this case)
        VBox parent = (VBox) clickedButton.getParent();

        // Find the sibling HBox (sibling of the clicked button)
        int clickedIndex = parent.getChildren().indexOf(clickedButton);
        if (clickedIndex > 0) {
            // Get the sibling node (assumed to be an HBox)
            HBox siblingHBox = (HBox) parent.getChildren().get(clickedIndex - 1);

            // Initialize variables for rideName and ridePrice
            String rideName = null;
            int ridePrice = 0;

            // Iterate through the HBox children to find the labels
            int labelIndex = 0;
            for (var node : siblingHBox.getChildren()) {
                if (node instanceof Label label) {
                    if (labelIndex == 0) {
                        rideName = label.getText(); // First label is rideName
                    } else if (labelIndex == 1) {
                        // Remove the peso sign and parse the remaining string as an integer
                        String priceText = label.getText().substring(1); // Remove the first character
                        try {
                            ridePrice = Integer.parseInt(priceText);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price format: " + priceText);
                        }
                    }
                    labelIndex++;
                }
            }

            // Return the retrieved values as a LabelData object
            if (rideName != null) {
                return new LabelData(rideName, ridePrice);
            }
        }

        System.out.println("Could not retrieve label data.");
        return null;
    }
}
