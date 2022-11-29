package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

import static com.baza.digitalsecretary.DigitalSecretaryApp.primaryStage;

public class RegistrationController {

    @FXML
    private TextField LoginField;

    @FXML
    private TextField PasswordField;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button BackToAuthorizationButton;

    @FXML
    private Label ErrorText;

    @FXML
    void initialize() {
        BackToAuthorizationButton.setOnAction(event -> {
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

        RegisterButton.setOnAction(event ->{
            String resultOfCheck = DataManager.AddUser(LoginField.getText(), PasswordField.getText());

            if (resultOfCheck == "success") {
                ErrorText.setTextFill(Color.color(0, 0.7, 0));
                ErrorText.setText("Регистрация прошла успешно");
            } else {
                ErrorText.setTextFill(Color.color(1, 0, 0));
                ErrorText.setText(resultOfCheck);
            }
        });
    }
}
