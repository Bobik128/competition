package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class PathCalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the TextFields for the paths
        TextField pathAField = new TextField();
        pathAField.setPromptText("Enter the first directory path");

        TextField pathBField = new TextField();
        pathBField.setPromptText("Enter the second directory path");

        // Create a Button to calculate the relative path
        Button calculateButton = new Button("Calculate Relative Path");

        // Create a Label to display the result
        Label resultLabel = new Label("Relative Path: ");

        // Define the action when the button is clicked
        calculateButton.setOnAction(e -> {
            String pathA = pathAField.getText();
            String pathB = pathBField.getText();
            String relativePath = getRelativeWay(pathA, pathB);
            resultLabel.setText("Relative Path: " + relativePath);
        });

        // Layout the components
        VBox layout = new VBox(10, pathAField, pathBField, calculateButton, resultLabel);
        layout.setPadding(new Insets(20));

        // Set up the scene and the stage
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Relative Path Calculator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Function to calculate the relative path between two paths
    public static String getRelativeWay(String A, String B) {
        LinkedList<String> aList = new LinkedList<>(Arrays.asList(A.split("\\\\")));
        LinkedList<String> bList = new LinkedList<>(Arrays.asList(B.split("\\\\")));

        // Find the common prefix and remove it from both lists
        while (!bList.isEmpty() && !aList.isEmpty() && Objects.equals(bList.getFirst(), aList.getFirst())) {
            bList.removeFirst();
            aList.removeFirst();
        }

        StringBuilder result = new StringBuilder();

        // Add ".." for each directory we need to go back
        while (!aList.isEmpty()) {
            aList.removeFirst();
            result.append("\\..");
        }

        // Add the remaining path from B
        while (!bList.isEmpty()) {
            result.append("\\").append(bList.getFirst());
            bList.removeFirst();
        }

        return result.toString();
    }
}

