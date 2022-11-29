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

public class AuthorizationController {

    @FXML
    private Button GoToRegistrationButton;

    @FXML
    private TextField PasswordField;

    @FXML
    private Button SignInButton;

    @FXML
    private TextField LogInField;

    @FXML
    private Label ErrorText;

    @FXML
    private Button SkipButton;

    private static String login;

    public static String getLogin() {
        return login;
    }

    @FXML
    void initialize(){
        GoToRegistrationButton.setOnAction(event ->{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("registration.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            primaryStage.setScene(new Scene(root));
        });

        SignInButton.setOnAction(event ->{
            String resultOfCheck = DataManager.CheckAuthorization(LogInField.getText(), PasswordField.getText());

            if (resultOfCheck.equals("success")) {
                login = LogInField.getText();

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
                ErrorText.setText(resultOfCheck);
            }
        });

        SkipButton.setOnAction(event -> {
            login = "admin";
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
