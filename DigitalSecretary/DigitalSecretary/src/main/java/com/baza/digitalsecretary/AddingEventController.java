package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.baza.digitalsecretary.HelloApplication.primaryStage;

public class AddingEventController {

    @FXML
    private Button AddEventButton;

    @FXML
    private Button BackToAppButton;

    @FXML
    private TextField Category;

    @FXML
    private TextField DateField;

    @FXML
    private TextArea DescriptionField;

    @FXML
    private TextField TitleField;

    @FXML
    void initialize() {

        //Переход обратно в главное окно
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