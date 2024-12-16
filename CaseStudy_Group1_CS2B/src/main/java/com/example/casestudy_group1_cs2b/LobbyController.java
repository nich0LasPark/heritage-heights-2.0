package com.example.casestudy_group1_cs2b;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public class LobbyController implements DataReceiver{
    @FXML
    private Label lineLabel;
    @FXML
    private Button proceedBtn;

    private Timeline timeline; // Declare the Timeline as a class-level variable
    private final Queue queue = new Queue(10); // Create a queue with a maximum size of 10
    private OrderData orderData;
    // Handle the button action (if needed for other functionalities)
    @FXML
    public void handleActionButton(ActionEvent actionEvent) throws IOException {

        // Get the source of the action event (button)
        Button sourceButton = (Button) actionEvent.getSource();

        // Get the text of the button
        String buttonText = sourceButton.getText();

        // Use a switch case to handle different button texts
        switch (buttonText) {
            case "Home":
                // Do something when "Home" button is clicked
                if(AlertHandler.showConfirmationAlert()){
                    ButtonHandler.switchScene("main.fxml", sourceButton, null);
                }
                break;
            case "Rides", "SHOW ALL RIDES →":
                // Do something when "Rides button is clicked
                if(AlertHandler.showConfirmationAlert()){
                    ButtonHandler.switchScene("rides.fxml", sourceButton, null);
                }
                break;
            case "Promos", "SHOW ALL PROMOS →":
                if(AlertHandler.showConfirmationAlert()){
                    ButtonHandler.switchScene("promos.fxml", sourceButton, null);
                }
                break;
            case "Tickets":
                ButtonHandler.switchScene("lobby.fxml", sourceButton, orderData);
                break;
            case "PROCEED":
                ButtonHandler.switchScene("tickets.fxml", sourceButton, orderData);
                break;
            case "About":
                if(AlertHandler.showConfirmationAlert()){
                    ButtonHandler.switchScene("about.fxml", sourceButton, null);
                }
                break;
            case "Login":
                if(AlertHandler.showConfirmationAlert()){
                    ButtonHandler.switchScene("login.fxml", sourceButton, null);
                }
                break;
            case "Sign up":
                if(AlertHandler.showConfirmationAlert()){
                    ButtonHandler.switchScene("register.fxml", sourceButton, null);
                }
                break;
            default:
                System.out.println("Unknown button clicked");
                break;
        }
    }

    // Generate the initial position of the "New Person" in the queue (5th to 10th)
    public int generateRandomLine() {
        // Add the "New Person" only if not already added
        if (!queue.newPersonAdded) {
            queue.enqueueRandom("User");
        }

        // Get the position of the "New Person"
        return queue.getPosition("User");
    }

    // Update the line label with the current position of the "New Person"
    public void changeLine() {
        int position = generateRandomLine();
        lineLabel.setText("You are now in line " + position);
        System.out.println("Position " + position);

    }

    // Method to automatically dequeue and update the label every 10 seconds
    public void startAutoQueueUpdate() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), _ -> {
                    // Dequeue a person
                    String dequeued = queue.dequeue();
                    System.out.println("\nDequeued: " + dequeued);

                    // Update the queue
                    queue.printQueue();

                    // Update the label based on the "New Person" position
                    changeLine();

                    if (generateRandomLine() == 1) {
                        proceedBtn.setDisable(false);
                        timeline.stop(); // Stop the timeline
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        timeline.play(); // Start the timeline
    }

    @FXML
    public void initialize() {
        proceedBtn.setDisable(true);
        queue.clearQueue();
        queue.fillQueue(); // Pre-fill the queue with 9 people initially
        changeLine(); // Initial line update
        startAutoQueueUpdate(); // Start the automatic queue update
        // Call the delayed method
    }

    @Override
    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }
}
