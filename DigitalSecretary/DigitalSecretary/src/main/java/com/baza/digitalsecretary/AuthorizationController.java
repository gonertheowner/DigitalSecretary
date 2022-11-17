package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationController {

    @FXML
    private Button GoToRegistrationButton;

    @FXML
    private TextField PasswordField;

    @FXML
    private Button SignInButton;

    @FXML
    private TextField SignInField;

    @FXML
    private Label ErrorText;

    @FXML
    void initialize(){
        GoToRegistrationButton.setOnAction(event ->{
            //GoToRegistrationButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("registration.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            GoToRegistrationButton.getScene().getWindow().hide();
            stage.showAndWait();
        });

        SignInButton.setOnAction(event -> {
            String resultOfCheck = "Asuccess";
            //Максим, добавь сюда функцию которая принимает login и password и возвращает "success" или ошибку
            //возможные ошибки: какое-то поле пустое, нет такого логина, неверный пароль
            if (resultOfCheck == "success") {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("app.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                SignInButton.getScene().getWindow().hide();
                stage.showAndWait();
            } else {
                ErrorText.setText(resultOfCheck);
            }
        });
    }

}
