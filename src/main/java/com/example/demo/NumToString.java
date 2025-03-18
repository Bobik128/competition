package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NumToString extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        TextField number = new TextField();
        number.setPromptText("Write number");

        Button button = new Button("Compute");

        Label result = new Label("");

        button.setOnAction(e -> {
            result.setText(compute(number.getText()));
        });

        // Layout the components
        VBox layout = new VBox(10, number, button, result);
        layout.setPadding(new Insets(20));

        // Set up the scene and the stage
        Scene scene = new Scene(layout, 400, 200);
        stage.setScene(scene);
        stage.setTitle("NumToString converter");
        stage.show();
    }

    private String compute(String number) {
        switch (number) {// numbers, that are different
            case "10":
                return "deset";
            case "15":
                return "patnáct";
        }

        StringBuilder result = new StringBuilder();

        int length = number.length();
        for (int i = 0; i < length; i++) {
            String nowNumber = number.substring(i, i + 1);// get only one character
            String numberName = getNumberName(nowNumber);

            if (nowNumber.equals("0") && length == 1) {// only when 0 is there
                return "nula";
            } else if (nowNumber.equals("0")) continue;// don't write anything

            if (nowNumber.equals("1") && length - i == 1) {// 1 is not usually written
                result.append(numberName);
                break;
            } else if (!nowNumber.equals("1")) {
                result.append(numberName);
            }

            if (length == 1) return result.toString();

            if (length - i == 2 && nowNumber.equals("1")) {// this means, it's teen number (its just said differently)
                String afternumber = number.substring(number.length() - 1);
                switch (afternumber) {// another wierd numbers
                    case "5" -> {
                        result.append("patnáct");
                        return result.toString();
                    }
                    case "2" -> {
                        result.append("dvanáct");
                        return result.toString();
                    }
                    case "1" -> {
                        result.append("jedenáct");
                        return result.toString();
                    }
                }
                result.append("náct");
                return result.toString();
            }

            switch (length - i) {
                case 1:// nothing special
                    break;
                case 2:// tenths
                    if (nowNumber.equals("3") || nowNumber.equals("4")) {
                        result.append("cet");
                    } else if (nowNumber.equals("2")) {
                        result.replace(result.length() - 3, result.length(), "dvacet");
                    } else {
                        result.replace(result.length() - numberName.length(), result.length(), "");
                        switch (nowNumber) {// just wierd
                            case "5" :
                                result.append("padesát");
                                break;
                            case "6" :
                                result.append("šedesát");
                                break;
                            case "7" :
                                result.append("sedmdesát");
                                break;
                            case "8" :
                                result.append("osmdesát");
                                break;
                            case "9" :
                                result.append("devadesát");
                                break;
                        }
                    }
                    break;
                case 3:// hundreds
                    if (nowNumber.equals("1")) {
                        result.append("sto");
                    } else if (nowNumber.equals("2") || nowNumber.equals("3") || nowNumber.equals("4")) {
                        result.append(" sta");
                    } else result.append(" set");
                    break;
                case 4:// thousands
                    if (nowNumber.equals("2")) {
                        result.replace(result.length() - 3, result.length(), "dva");
                    }

                    if (nowNumber.equals("2") || nowNumber.equals("3") || nowNumber.equals("4")) {
                        result.append(" tisíce");
                    } else result.append(" tisíc");
                    break;
                default:
                    return "Out of range!";
            }

            result.append(" ");
        }
        return result.toString();
    }

    private String getNumberName(String number) {
        return switch (number) {
            case "1" -> "jedna";
            case "2" -> "dvě";
            case "3" -> "tři";
            case "4" -> "čtyři";
            case "5" -> "pět";
            case "6" -> "šest";
            case "7" -> "sedm";
            case "8" -> "osm";
            case "9" -> "devět";
            default -> "";
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
