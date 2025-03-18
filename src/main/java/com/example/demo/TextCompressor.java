package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedList;

public class TextCompressor extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        TextField input = new TextField();
        input.setPromptText("write letters");

        Button compressButton = new Button("Compress");
        Button deCompressButton = new Button("Decompress");

        Label label = new Label("Result:");

        TextField result = new TextField("");

        compressButton.setOnAction(e -> {
            result.setText(compress(input.getText()));
        });

        deCompressButton.setOnAction(e -> {
            result.setText(deCompress(input.getText()));
        });

        // Layout the components
        VBox layout = new VBox(10, input, compressButton, deCompressButton, label, result);
        layout.setPadding(new Insets(20));

        // Set up the scene and the stage
        Scene scene = new Scene(layout, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Text compressor");
        stage.show();
    }

    private String deCompress(String input) {
        StringBuilder result = new StringBuilder();
        LinkedList<String> letters = new LinkedList<>(Arrays.asList(input.split("")));

        int num = 0;
        for (String letter : letters) {
            int gotNum = getNumber(letter);
            if (gotNum == -1){
                if (num == 0) {
                    result.append(letter);
                    continue;
                }
                result.append(String.valueOf(letter).repeat(Math.max(0, num)));
                num = 0;
            } else num = gotNum;
        }

        return result.toString();
    }

    private String compress(String input) {
        StringBuilder result = new StringBuilder();
        LinkedList<String> letters = new LinkedList<>(Arrays.asList(input.split("")));

        String lastLetter = "";
        int letterCount = 0;
        int i = 0;
        for (String letter : letters) {
            if (!lastLetter.equals(letter)) {
                if (!lastLetter.isEmpty()) {
                    if (letterCount > 1) result.append(letterCount);
                    result.append(lastLetter);
                }

                lastLetter = letter;
                letterCount = 1;
            } else {
                letterCount++;
            }
            i++;
        }

        if (!lastLetter.isEmpty()) {
            if (letterCount > 1) result.append(letterCount);
            result.append(lastLetter);
        }

        return result.toString();
    }

    public static int getNumber(String str) {
        int num = -1;
        try {
            num = Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return -1;
        }
        return num;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
