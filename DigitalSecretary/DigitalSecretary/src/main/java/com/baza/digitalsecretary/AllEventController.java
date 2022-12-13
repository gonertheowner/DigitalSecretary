package com.baza.digitalsecretary;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;

import static com.baza.digitalsecretary.DigitalSecretaryApp.primaryStage;

public class AllEventController {

    @FXML
    private Button BackToAppButton;

    @FXML
    private Label ErrorText;

    @FXML
    private Button GoToChangeButton;

    @FXML
    private ListView<String> EventsListBox;

    @FXML
    private Button deleteEventButton;



    @FXML
    void initialize() throws SQLException {
        ObservableList<String> allEventsList = DataManager.GetAllEventsList();
        EventsListBox.setItems(allEventsList);


        GoToChangeButton.setOnAction(event -> {
            int number = EventsListBox.getSelectionModel().getSelectedIndex();
            if (number == -1) {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText("Ничего не выбрано");
            }else{
                int id = DataManager.GetIdByNumberFromAllEvents(number);
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

        deleteEventButton.setOnAction(event -> {
            int number = EventsListBox.getSelectionModel().getSelectedIndex();
            if (number == -1) {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText("Ничего не выбрано");
            }else{
                int id = DataManager.GetIdByNumberFromAllEvents(number);
                DataManager.DeleteEvent(id);
                EventsListBox.setItems(DataManager.GetAllEventsList());
                ErrorText.setTextFill(Color.color(0.38, 0.43, 0.37));
                ErrorText.setText("Удаление прошло успешно");
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
