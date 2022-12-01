package com.baza.digitalsecretary;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.baza.digitalsecretary.DigitalSecretaryApp.primaryStage;

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
    void initialize() throws SQLException {

        //Обновление даты в приложении
        Date today = new Date();
        SimpleDateFormat formatToday = new SimpleDateFormat("'Сегодня: 'dd.MM.yyyy");
        TodayText.setText(formatToday.format(today));

        /*ObservableList<String> allEventsList = FXCollections.observableArrayList();
        var statement = DataManager.connection.prepareStatement("SELECT * FROM events WHERE login = ?");
        statement.setString(1, AuthorizationController.getLogin());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            allEventsList.add(resultSet.getString(5) + " " + resultSet.getString(4) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
        }*/

        ObservableList<String> todayEventsList = DataManager.GetTodayEvents();
        TodayEventListBox.setItems(todayEventsList);

        ObservableList<String> comingEventsList = DataManager.GetComingEvents();
        СomingEventsListBox.setItems(comingEventsList);


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
