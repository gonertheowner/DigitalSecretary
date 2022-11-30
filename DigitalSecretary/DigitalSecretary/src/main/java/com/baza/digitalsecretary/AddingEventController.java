package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.baza.digitalsecretary.DigitalSecretaryApp.primaryStage;

public class AddingEventController {

    @FXML
    private Button AddEventButton;

    @FXML
    private Button BackToAppButton;

    @FXML
    private TextField CategoryField;

    @FXML
    private DatePicker DateField;

    @FXML
    private TextArea DescriptionField;

    @FXML
    private TextField TitleField;

    @FXML
    private Label ErrorText;

    @FXML
    void initialize() {
        DateField.setValue(LocalDate.now());

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

        AddEventButton.setOnAction(event -> {
            String resultOfCheck = DataManager.AddEvent(DateField.getValue(), CategoryField.getText(), TitleField.getText(), DescriptionField.getText());
            if (resultOfCheck.equals("success")){
                ErrorText.setTextFill(Color.color(0, 0.70, 0));
                ErrorText.setText("Добавление прошло успешно");
            } else {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText(resultOfCheck);
            }
        });


    }
}