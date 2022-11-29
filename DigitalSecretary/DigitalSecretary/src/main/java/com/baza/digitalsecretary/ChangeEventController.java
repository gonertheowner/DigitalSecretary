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
import java.time.LocalDate;

import static com.baza.digitalsecretary.HelloApplication.primaryStage;

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

    public static void setSelectedEventId(String selectedEventId) {
        ChangeEventController.selectedEventId = selectedEventId;
    }

    @FXML
    void initialize() {

        //нужно получить поля события, id которого был выбран в предыдущем окне
        //и установить значения как дальше
        DateField.setValue(LocalDate.of(2000, 1, 1));
        TitleField.setText("название");
        CategoryField.setText("категория");
        DescriptionField.setText("описание");


        ChangeEventButton.setOnAction(event -> {
            //взять все поля и перезаписать событие
            String message = "success";
            if (message == "success") {
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
