package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TxtUnicode extends Application {
    private static File startFile;
    private static File endFile;

    @Override
    public void start(Stage stage) throws Exception {
        TextField startPath = new TextField();
        startPath.setPromptText("Path to .txt to rewrite");

        TextField endPath = new TextField();
        endPath.setPromptText("Path where will the result file be");

        Button button = new Button("Compute");

        Label result = new Label("");

        button.setOnAction(e -> {
            String sp = startPath.getText();
            if (!sp.endsWith(".txt")) sp = startPath.getText() + ".txt";
            String ep = endPath.getText();

            if (endPath.getText().isBlank()) {
                ep = "unique_emails.txt";
            }

            if (!ep.endsWith(".txt")) ep = endPath.getText() + ".txt";

            startFile = new File(sp);
            endFile = new File(ep);

            if (!startFile.exists()) {
                result.setText("File: " + startPath.getText() + " is not valid");
                return;
            }

            if (endFile.exists()) {
                clearFile(endFile);
            } else {
                try {
                    if (!endFile.createNewFile()) {
                        result.setText("Could not create: " + endPath.getText() + " file");
                        return;
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            LinkedList<String> lines = readLinesAsList(startFile);

            LinkedList<String> resultLines = new LinkedList<>();
            for (String line : lines) {
                if (!resultLines.contains(line)) {
                    resultLines.add(line);
                }
            }

            writeLinesAsList(endFile, resultLines);
        });

        // Layout the components
        VBox layout = new VBox(10, startPath, endPath, button, result);
        layout.setPadding(new Insets(20));

        // Set up the scene and the stage
        Scene scene = new Scene(layout, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Unique email");
        stage.show();
    }

    public static void writeLinesAsList(File file, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static LinkedList<String> readLinesAsList(File file) {
        LinkedList<String> result = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public static void clearFile(File file) {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
