package com.baza.digitalsecretary;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.baza.digitalsecretary.HelloApplication.primaryStage;

public class AppController {

    @FXML
    private Button ExitButton;

    @FXML
    private Label TodayText;

    @FXML
    private Button GoToAddEventButton;

    @FXML
    private ListView<String> TodayEventListBox;

    @FXML
    private ListView<String> СomingEventsListBox;

    @FXML
    private Button GoToChooseEventButton;

    @FXML
    void initialize() {

        //Обновление даты в приложении
        Date today = new Date();
        SimpleDateFormat formatToday = new SimpleDateFormat("'Сегодня: 'dd.MM.yyyy");
        TodayText.setText(formatToday.format(today));

        ObservableList<String> allEventsList = FXCollections.observableArrayList();
        allEventsList.add("17.11.2022 категория пррррррррррррррррррррррррррррррррррррррррррр");
        allEventsList.add("24.02.2022 СВО Начало");
        allEventsList.add("18.12.2020 Дора Релиз альбома");

        TodayEventListBox.setItems(allEventsList);

        //Переход на авторизацию
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

        GoToAddEventButton.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("adding_event.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            primaryStage.setScene(new Scene(root));
        });

        GoToChooseEventButton.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("choose_event.fxml"));

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
