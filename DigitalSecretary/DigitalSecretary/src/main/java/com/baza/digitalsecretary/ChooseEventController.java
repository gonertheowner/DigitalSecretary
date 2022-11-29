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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.baza.digitalsecretary.DigitalSecretaryApp.primaryStage;

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
    private Button deleteEventButton;

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
            List<String> ids = new ArrayList<>();
            try {
                var selectionSet = DataManager.connection.prepareStatement("SELECT * FROM events").executeQuery();
                while (selectionSet.next()) {
                    ids.add(selectionSet.getString(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String message;
            if (IdField.getText().equals("")) {
                message = "Please, input event id first";
            } else if (DataManager.parseStringToInteger(IdField.getText()) == -1) {
                message = "Please, input a number";
            } else if (!(ids.contains(IdField.getText()))) {
                message = "Please, input an existing id";
            } else {
                message = "Success";
                ChangeEventController.setSelectedEventId(IdField.getText());
            }

            if (message.equals("Success")) {
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

        deleteEventButton.setOnAction(actionEvent -> {
            List<String> ids = new ArrayList<>();
            try {
                var selectionSet = DataManager.connection.prepareStatement("SELECT * FROM events").executeQuery();
                while (selectionSet.next()) {
                    ids.add(selectionSet.getString(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String message;
            if (IdField.getText().equals("")) {
                message = "Please, input event id first";
            } else if (DataManager.parseStringToInteger(IdField.getText()) == -1) {
                message = "Please, input a number";
            } else if (!(ids.contains(IdField.getText()))) {
                message = "Please, input an existing id";
            } else {
                message = "Success";
            }

            if (message.equals("Success")) {
                PreparedStatement st;
                try {
                    st = DataManager.connection.prepareStatement("DELETE FROM events WHERE id = ?");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    st.setInt(1, Integer.parseInt(IdField.getText()));
                    st.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
