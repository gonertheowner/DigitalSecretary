package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.baza.digitalsecretary.HelloApplication.primaryStage;

public class AppController {

    @FXML
    private Button ExitButton;

    @FXML
    private Label TodayText;

    @FXML
    void initialize() {

        Date today = new Date();
        SimpleDateFormat formatToday = new SimpleDateFormat("'Сегодня: 'dd.MM.yyyy");
        TodayText.setText(formatToday.format(today));

        ExitButton.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("authorization.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            primaryStage.setScene(new Scene(root));
        });





    }
}
