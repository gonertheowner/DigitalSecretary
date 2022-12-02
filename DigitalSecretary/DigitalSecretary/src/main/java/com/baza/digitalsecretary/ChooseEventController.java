package com.baza.digitalsecretary;

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
import java.sql.SQLException;

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
        ObservableList<String> allEventsList = DataManager.GetAllEventsList();
        EventsListBox.setItems(allEventsList);

        /*EventsListBox.setOnAction(event -> {

        });*/


        GoToChangeButton.setOnAction(event -> {
            String resultOfCheck = DataManager.GetCheckEventIdInChoose(IdField.getText());
            if (resultOfCheck.equals("success")) {
                ChangeEventController.setSelectedEventId(IdField.getText());

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
                ErrorText.setText(resultOfCheck);
            }
        });

        deleteEventButton.setOnAction(actionEvent -> {
            String resultOfCheck = DataManager.GetCheckEventIdInChoose(IdField.getText());
            if (resultOfCheck.equals("success")) {
                DataManager.DeleteEvent(IdField.getText());
                ObservableList<String> allEventsList2 = DataManager.GetAllEventsList();//может исправить ?
                EventsListBox.setItems(allEventsList2);
                ErrorText.setTextFill(Color.color(0, 0.7, 0));
                ErrorText.setText("Удаление прошло успешно");
            } else {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText(resultOfCheck);
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
