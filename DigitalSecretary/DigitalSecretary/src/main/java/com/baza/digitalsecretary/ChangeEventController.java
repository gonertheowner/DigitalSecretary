package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    @FXML
    void initialize() throws SQLException {
        var statement = DataManager.connection.prepareStatement("SELECT * FROM events WHERE id = ?");
        statement.setInt(1, DataManager.GetSelectedEventId());
        ResultSet resultSet = statement.executeQuery();
        LocalDate date = LocalDate.now();
        String title = "", category = "", description = "";
        while (resultSet.next()) {
            date = LocalDate.parse(resultSet.getString(5));
            DateField.setValue(date);
            title = resultSet.getString(2);
            TitleField.setText(title);
            category = resultSet.getString(4);
            CategoryField.setText(category);
            description = resultSet.getString(3);
            DescriptionField.setText(description);
            DataManager.SetEventInfo(date, title, category, description);
            /*DateField.setValue(LocalDate.parse(resultSet.getString(5)));
            TitleField.setText(resultSet.getString(2));
            CategoryField.setText(resultSet.getString(4));
            DescriptionField.setText(resultSet.getString(3));*/
        }

        ChangeEventButton.setOnAction(event -> {
            String message = DataManager.changeEvent(DataManager.GetSelectedEventId(), DateField.getValue(), TitleField.getText(), CategoryField.getText(), DescriptionField.getText());
            if (message.equals("success")) {
                ErrorText.setTextFill(Color.color(0, 0.70, 0));
                ErrorText.setText("Изменение прошло успешно");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("app.fxml"));


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

        BackToChooseEventButton.setOnAction(event -> {
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
