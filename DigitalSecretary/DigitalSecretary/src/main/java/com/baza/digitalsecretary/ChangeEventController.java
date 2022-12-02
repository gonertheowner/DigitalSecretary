package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static com.baza.digitalsecretary.DigitalSecretaryApp.primaryStage;

public class ChangeEventController {

    @FXML
    private Button BackToChooseEventButton;

    @FXML
    private TextField CategoryField;

    @FXML
    private Button ChangeEventButton;

    @FXML
    private DatePicker DateField;

    @FXML
    private TextArea DescriptionField;

    @FXML
    private Label ErrorText;

    @FXML
    private TextField TitleField;

    private static String selectedEventId;

    private static ArrayList<Integer> ids;

    public static void setSelectedEventId(String selectedEventId) {
        ChangeEventController.selectedEventId = selectedEventId;
    }

    public static void setIds(ArrayList<Integer> ids) {
        ChangeEventController.ids = ids;
    }

    public static ArrayList<Integer> getIds() {
        return ids;
    }

    @FXML
    void initialize() throws SQLException {
        selectedEventId = ids.get(Integer.parseInt(selectedEventId) - 1).toString();
        var statement = DataManager.connection.prepareStatement("SELECT * FROM events WHERE id = ?");
        statement.setInt(1, Integer.parseInt(selectedEventId));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            DateField.setValue(LocalDate.parse(resultSet.getString(5)));
            TitleField.setText(resultSet.getString(2));
            CategoryField.setText(resultSet.getString(4));
            DescriptionField.setText(resultSet.getString(3));
        }

        ChangeEventButton.setOnAction(event -> {
            String message = DataManager.changeEvent(selectedEventId, DateField.getValue(), TitleField.getText(), CategoryField.getText(), DescriptionField.getText());
            if (message.equals("success")) {
                ErrorText.setTextFill(Color.color(0, 0.70, 0));
                ErrorText.setText("Изменение прошло успешно");
            } else {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText(message);
            }
        });

        BackToChooseEventButton.setOnAction(event -> {
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
