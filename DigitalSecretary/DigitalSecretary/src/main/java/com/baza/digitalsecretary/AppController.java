package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.baza.digitalsecretary.DigitalSecretaryApp.primaryStage;

public class AppController {

    @FXML
    private Button ExitButton;

    @FXML
    private Label TodayText;

    @FXML
    private Label UserText;

    @FXML
    private Label ErrorText;

    @FXML
    private Button DeleteEventButton;

    @FXML
    private Label CountTodayEventsText;

    @FXML
    private Button GoToAddEventButton;

    @FXML
    private ListView<String> TodayEventListBox;

    @FXML
    private ListView<String> ComingEventsListBox;

    @FXML
    private Button GoToChangeEventButton;

    @FXML
    private Button AllEventsButton;

    @FXML
    void initialize() {

        TodayEventListBox.setItems(DataManager.GetTodayEvents());
        ComingEventsListBox.setItems(DataManager.GetComingEvents());

        Date today = new Date();
        SimpleDateFormat formatToday = new SimpleDateFormat("'Сегодня: 'dd.MM.yyyy");
        TodayText.setText(formatToday.format(today));

        UserText.setText("Пользователь: " + AuthorizationController.getLogin());
        CountTodayEventsText.setText("Событий на сегодня: " + TodayEventListBox.getItems().size());


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

        GoToChangeEventButton.setOnAction(event -> {

            int todayNumber = TodayEventListBox.getSelectionModel().getSelectedIndex();
            int comingNumber = ComingEventsListBox.getSelectionModel().getSelectedIndex();
            if (todayNumber == -1 && comingNumber == -1) {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText("Ничего не выбрано");
            } else if (comingNumber == -1) {
                int id = DataManager.GetIdByNumberFromTodayEvents(todayNumber);
                DataManager.SetSelectedEventId(id);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("change_event.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                primaryStage.setScene(new Scene(root));
            } else {
                int id = DataManager.GetIdByNumberFromComingEvents(comingNumber);
                DataManager.SetSelectedEventId(id);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("change_event.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                primaryStage.setScene(new Scene(root));
            }
        });

        AllEventsButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("all_event.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            primaryStage.setScene(new Scene(root));
        });

        DeleteEventButton.setOnAction(event -> {
            int todayNumber = TodayEventListBox.getSelectionModel().getSelectedIndex();
            int comingNumber = ComingEventsListBox.getSelectionModel().getSelectedIndex();
            if (todayNumber == -1 && comingNumber == -1) {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText("Ничего не выбрано");
            } else if (comingNumber == -1) {
                int id = DataManager.GetIdByNumberFromTodayEvents(todayNumber);
                DataManager.DeleteEvent(id);
                TodayEventListBox.setItems(DataManager.GetTodayEvents());
                ErrorText.setTextFill(Color.color(0, 0.7, 0));
                ErrorText.setText("Удаление прошло успешно");
            } else {
                int id = DataManager.GetIdByNumberFromComingEvents(comingNumber);
                DataManager.DeleteEvent(id);
                ComingEventsListBox.setItems(DataManager.GetComingEvents());
                ErrorText.setTextFill(Color.color(0, 0.7, 0));
                ErrorText.setText("Удаление прошло успешно");
            }
        });

        TodayEventListBox.setOnMouseClicked(event -> {
            ComingEventsListBox.getSelectionModel().clearSelection();
        });

        ComingEventsListBox.setOnMouseClicked(event -> {
            TodayEventListBox.getSelectionModel().clearSelection();
        });

    }
}