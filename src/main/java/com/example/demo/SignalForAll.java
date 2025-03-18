package com.example.demo;

import com.almasb.fxgl.core.math.Vec2;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class SignalForAll extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Label ptf = new Label("Path to file:");
        TextField startPath = new TextField();
        startPath.setPromptText("Path to .txt");

        Button button = new Button("Compute");

        Label result = new Label("");

        button.setOnAction(e -> {
            result.setText(String.valueOf(compute(startPath.getText())));
        });

        // Layout the components
        VBox layout = new VBox(10, ptf, startPath, button, result);
        layout.setPadding(new Insets(20));

        // Set up the scene and the stage
        Scene scene = new Scene(layout, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Signal for all");
        stage.show();
    }

    private float compute(String pathToInput) {
        LinkedList<String> lines = TxtUnicode.readLinesAsList(new File(pathToInput));

        if (TextCompressor.getNumber(lines.getLast()) == 1) return 0;// if there is only one house, return 0

        LinkedHashMap<Integer, Vec2> houses = new LinkedHashMap<>();// houses as integer and its position
        lines.removeFirst();

        int i = 1;
        for (String line : lines) {// setup "houses" map
            LinkedList<String> rawMuns = new LinkedList<>(Arrays.asList(line.split(" ")));
            houses.put(i, new Vec2(TextCompressor.getNumber(rawMuns.getFirst()), TextCompressor.getNumber(rawMuns.getLast())));
            i++;
        }

        List<Map.Entry<Integer, Float>> mostFar = get3MostFar(houses);// get 3 points, that are most distant from each other

        Vec2 a = houses.get(mostFar.get(0).getKey());
        Vec2 b = houses.get(mostFar.get(1).getKey());
        if (mostFar.size() == 2) return (float) a.distance(b)/2;// if there are only two, just compute the dist between them
        Vec2 c = houses.get(mostFar.get(2).getKey());

        if (liesInCircle(a, b, c)) return (float) a.distance(b)/2;// if the third point is not useful (it is not part of the triangle)

        Vec2 mid = a.midpoint(b);

        return (float) (mid.distance(c) * 2 / 3);
    }

    private boolean liesInCircle(Vec2 a, Vec2 b, Vec2 c) { // a^2 + b^2 = c^2
        // every point, that is on a circle between a and b makes with them rectangular triangle
        // if the ac + bc is lower than ab^2, it's inside the circle
        double ab = a.distance(b);
        double ac = a.distance(c);
        double cb = c.distance(b);

        return ac*ac + cb*cb <= ab*ab;
    }

    private List<Map.Entry<Integer, Float>> get3MostFar(LinkedHashMap<Integer, Vec2> input) {
        LinkedHashMap<Integer, Float> maxDists = new LinkedHashMap<>();
        for (Map.Entry<Integer, Vec2> house : input.entrySet()) {// compute the further dist to every point
            float farest = 0;
            for (Map.Entry<Integer, Vec2> houseNumeroDuo : input.entrySet()) {
                if (house == houseNumeroDuo) continue;
                float d = (float) house.getValue().distance(houseNumeroDuo.getValue());
                if (d > farest) {
                    farest = d;
                }
            }

            maxDists.put(house.getKey(), farest);
        }

        // sort it by dist (higher = better)
        List<Map.Entry<Integer, Float>> list = new LinkedList<>(maxDists.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // pick 3 most far
        // there is no need for more, because it can create only triangle (triangle has 3 points)
        List<Map.Entry<Integer, Float>> result = new LinkedList<>();
        result.add(list.get(0));
        result.add(list.get(1));
        result.add(list.get(2));

        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
