package com.baza.digitalsecretary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.baza.digitalsecretary.HelloApplication.primaryStage;

public class ChooseEventController {

    @FXML
    private Button BackToAppButton;

    @FXML
    private Label ErrorText;

    @FXML
    private Button GoToChangeButton;

    @FXML
    private TextField IdField;

    @FXML
    private ListView<String> EventsListBox;

    @FXML
    void initialize() throws SQLException {
        ObservableList<String> allEventsList = FXCollections.observableArrayList();
        var statement = DataManager.connection.prepareStatement("SELECT * FROM events");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            allEventsList.add(resultSet.getString(1) + " " + resultSet.getString(5) + " " + resultSet.getString(2) + " " + resultSet.getString(4) + " " + resultSet.getString(3));
        }
        EventsListBox.setItems(allEventsList);

        GoToChangeButton.setOnAction(event -> {
            //В message записывается результат проверки IdField.getText()
            //на существование такого id события и корректности int
            String message = "success";
            if (message == "success") {
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
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText(message);
            }

        });

        BackToAppButton.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("app.fxml"));

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
