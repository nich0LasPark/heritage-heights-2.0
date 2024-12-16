package com.example.casestudy_group1_cs2b;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TicketsController implements DataReceiver {
    private OrderData orderData;
    @FXML
    private TextField ordernameTextfield;
    @FXML
    private ComboBox<String> rideComboBox;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactnoField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField quantityField;
    @FXML
    public TextField amountField;
    @FXML
    private ComboBox<String> mopComboBox;
    @FXML
    private Button submitButton;
    @FXML
    private TextField newQty;
    @FXML
    private VBox OrderVBox;
    @FXML
    private Label countdownLabel;
    ArrayList<OrderData> orderList = new ArrayList<>();
    private ObservableList<String> rideOptions; // List of available rides
    private ObservableList<String> selectedRides; // List to track selected rides
    public int totalAmount = 0, qty = 0;
    private Timer timer;


    public void initialize() {
        startTimer();
        // Add listeners to form fields to check if all fields are filled
        firstnameField.textProperty().addListener((_, _, _) -> checkFormCompletion());
        lastnameField.textProperty().addListener((_, _, _) -> checkFormCompletion());
        ageField.textProperty().addListener((_, _, _) -> checkFormCompletion());
        emailField.textProperty().addListener((_, _, _) -> checkFormCompletion());
        contactnoField.textProperty().addListener((_, _, _) -> checkFormCompletion());
        dateField.valueProperty().addListener((_, _, _) -> checkFormCompletion());
        quantityField.textProperty().addListener((_, _, _) -> checkFormCompletion());
        amountField.textProperty().addListener((_, _, _) -> checkFormCompletion());
        mopComboBox.valueProperty().addListener((_, _, _) -> checkFormCompletion());

        // Add options to ComboBox for payment methods
        ObservableList<String> mopOptions = FXCollections.observableArrayList(
                "GCash",
                "Maya",
                "Visa",
                "Paypal"
        );
        mopComboBox.setItems(mopOptions); // Populate the ComboBox with options

        rideOptions = FXCollections.observableArrayList(
                "Ferris Wheel",
                "Roller Coaster",
                "Carousel",
                "Bump Cars",
                "Fun Slide",
                "Drop Tower",
                "Rides All You Want",
                "Holiday Special",
                "Barkada Bonding",
                "Couple Goals",
                "Weekend Adventure"
        );

        // Initialize the selected rides list
        selectedRides = FXCollections.observableArrayList();

        // Populate the ComboBox with available rides (initially all rides)
        rideComboBox.setItems(rideOptions);

        // Add listener for ride selection
        rideComboBox.setOnAction(_ -> handleRideSelection());

        // Handle any selected ride
        DelayHandler.executeWithDelay(() -> {
            totalAmount = totalAmount + orderData.getRidePrice();
            amountField.setText(String.valueOf(totalAmount));
            ordernameTextfield.setText(orderData.getRideName());
            orderList.add(orderData);

            createInitialOrderNode(); // If you need to create an initial order node

            String rideToRemove = orderData.getRideName();
            if (rideOptions.contains(rideToRemove)) {
                removeRide(rideToRemove); // Remove the ride from the list
            }
            // Update ComboBox with the available rides after removal
            updateRideOptions();
        }, 100); // Delay of 100 milliseconds
    }
    // Method to start the timer
    private void startTimer() {
        int totalTime = 60; // Total countdown time in seconds

        timer = new Timer();

        // Schedule the task to be run every second
        timer.scheduleAtFixedRate(new TimerTask() {
            int secondsRemaining = totalTime;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (secondsRemaining > 0) {
                        // Update the countdown label with the remaining seconds
                        countdownLabel.setText("Time remaining: " + secondsRemaining + "s");
                        secondsRemaining--;
                    } else {
                        // Time's up, switch the scene
                        timer.cancel(); // Stop the timer
                        try {
                            switchScene("main.fxml");
                            AlertHandler.showInfoAlert();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }, 0, 1000L); // Start immediately, update every 1000ms (1 second)
    }
    public void stopTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel the timer
            timer = null;   // Set it to null to avoid accidental reuse
        }
    }

    public static void switchScene(String fxmlFile) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(TicketsController.class.getResource(fxmlFile));

        // Load the parent (root node) of the new FXML scene
        Parent root = loader.load();

        // Get the current stage from any node in the current scene
        Stage stage = (Stage) Stage.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active stage found"));

        // Create a new scene with the new FXML root
        Scene scene = new Scene(root);

        // Set the new scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    public void updateTotal(int price) {
        totalAmount = totalAmount - price;
        amountField.setText(String.valueOf(totalAmount)); // Update the UI field
    }
    @FXML
    public void handleAddOrderButton() throws IOException {
        // Check if rideComboBox has a value selected and if newQty is not null or empty
        if (rideComboBox.getValue() == null || rideComboBox.getValue().isEmpty()) {
            AlertHandler.showErrorAlert("Invalid input", "Please select a ride before adding the order");
        } else if (newQty.getText() == null || newQty.getText().isEmpty()) {
            AlertHandler.showErrorAlert("Invalid input", "Please enter a quantity before adding the order.");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderNode.fxml"));
                Node orderNode = loader.load();
                String rideName = rideComboBox.getValue();
                removeRide(rideName);

                //quantity of the new order
                qty = Integer.parseInt(newQty.getText());

                orderData.setRideName(rideName);
                orderData.setRidePrice(orderData.getCurrentPriceByName(rideName));
                orderData.setQuantity(qty);
                orderList.add(orderData);

                totalAmount = totalAmount + (orderData.getCurrentPriceByName(rideName) * qty);
                amountField.setText(String.valueOf(totalAmount));
                // Access the OrderNodeController
                OrderNodeController controller = loader.getController();
                // Set the parent TicketsController reference in the OrderNodeController
                controller.setTicketsController(this);
                controller.setOrderData(orderData);
                controller.setOrderList(orderList);
                rideComboBox.setValue("");
                newQty.setText("");
                // Add the node to the container
                OrderVBox.getChildren().add(orderNode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeOrderNode(OrderNodeController orderNodeController) {
        if (orderNodeController != null) {
            OrderVBox.getChildren().remove(orderNodeController.getRideNode());
        } else {
            System.out.println("Failed to remove OrderNode, controller is null.");
        }
    }

    @FXML
    public void handleActionButton(ActionEvent actionEvent) throws IOException {
        // Delegate the action to the ButtonHandler class
        ButtonHandler.handleActionButton(actionEvent);
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }

    private void checkFormCompletion() {
        boolean isComplete = isEmpty(firstnameField) && isEmpty(lastnameField) && isEmpty(ageField) &&
                isEmpty(emailField) && isEmpty(contactnoField) && dateField.getValue() != null &&
                isEmpty(quantityField) && isEmpty(amountField) && mopComboBox.getValue() != null;

        // Enable or disable submit button based on form completion
        submitButton.setDisable(!isComplete);
    }

    // Helper method to check if a TextField is empty
    private boolean isEmpty(TextField field) {
        return !field.getText().trim().isEmpty();
    }
    // Handle the submit button click
    public void handleSubmitButton(ActionEvent actionEvent) throws IOException {
        // Get the source of the action event (button)
        Button sourceButton = (Button) actionEvent.getSource();

        if (validateForm()) {
            stopTimer();
            showSuccessAlert();
            ButtonHandler.switchScene("main.fxml", sourceButton, null);
        } else {
            AlertHandler.showErrorAlert("Invalid Data", "Please fill out all fields correctly.");
        }
    }
    // Show success alert displaying all form data

    private void showSuccessAlert() {
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Ticket Submitted Successfully");
        successAlert.setHeaderText("Ticket Details");
        successAlert.setContentText("Thank you for purchasing the Heritage Heights pass. See you there!");
        successAlert.showAndWait();
    }

    // Validate the form
    private boolean validateForm() {
        return isNotEmpty(firstnameField) && isNotEmpty(lastnameField) && isValidAge(ageField) &&
                isValidEmail(emailField) && isNotEmpty(contactnoField) && isValidDate(dateField) &&
                isValidQuantity(quantityField) && isValidAmount(amountField) && isValidPaymentMethod(mopComboBox);
    }
    // Helper methods for validation
    private boolean isNotEmpty(TextField field) {
        return !field.getText().trim().isEmpty();
    }
    private boolean isValidAge(TextField field) {
        try {
            int age = Integer.parseInt(field.getText());
            return age >= 18 && age <= 120;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidEmail(TextField field) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return field.getText().matches(emailRegex);
    }
    private boolean isValidDate(DatePicker field) {
        return field.getValue() != null;
    }
    private boolean isValidQuantity(TextField field) {
        try {
            int qty = Integer.parseInt(field.getText());
            return qty > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidAmount(TextField field) {
        try {
            double amt = Double.parseDouble(field.getText());
            return amt >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidPaymentMethod(ComboBox<String> field) {
        return field.getValue() != null && !field.getValue().trim().isEmpty();
    }


    private void createInitialOrderNode() {
        try {
            // Load the OrderNode FXML and get the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderNode.fxml"));
            Node orderNode = loader.load();

            // Get the OrderNodeController from the loaded FXML
            OrderNodeController orderNodeController = loader.getController();

            // Set the OrderData in the controller
            orderNodeController.setOrderData(orderData);

            // Set the parent TicketsController reference in the OrderNodeController
            orderNodeController.setTicketsController(this);

            // Add the orderNode to the VBox
            OrderVBox.getChildren().add(orderNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handles when a ride is selected from the ComboBox
    private void handleRideSelection() {
        String selectedRide = rideComboBox.getValue();
        if (selectedRide != null && !selectedRides.contains(selectedRide)) {
            // Add selected ride to the list of selected rides
            selectedRides.add(selectedRide);
            // You can call the removeRide method here if you want to remove the ride immediately
            // removeRide(selectedRide);
        }
    }

    // This method removes a ride from the rideOptions list when explicitly called
    public void removeRide(String rideName) {
        if (rideOptions.contains(rideName)) {
            rideOptions.remove(rideName); // Remove the ride from available options
            updateRideOptions(); // Update the ComboBox to reflect the changes
        }
    }

    // Updates the ComboBox with the remaining available rides
    private void updateRideOptions() {
        // Create a list of available rides that haven't been selected yet
        ObservableList<String> availableRides = FXCollections.observableArrayList();
        for (String ride : rideOptions) {
            if (!selectedRides.contains(ride)) {
                availableRides.add(ride);
            }
        }
        // Update ComboBox with remaining available rides, ensuring no empty list is passed
        rideComboBox.setItems(availableRides);
    }

    // Optionally, you could add a method to remove a ride from selectedRides list
    public void removeFromSelectedRides(String name, int price) {
        selectedRides.remove(name);
        rideOptions.add(name);
        // Optionally, add back to rideOptions if it gets unselected
        updateRideOptions();
    }

}

