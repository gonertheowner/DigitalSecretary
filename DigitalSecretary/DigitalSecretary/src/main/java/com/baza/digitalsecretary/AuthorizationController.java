package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
            String resultOfCheck="success";
            User user=new User(LogInField.getText(), PasswordField.getText());
            if (!DataManager.searchByLogin(user)){
                resultOfCheck="Not found user with this login. Go registration";
            }
            if (DataManager.searchByLogin(user) && !DataManager.searchUser(user)){
                resultOfCheck="Uncorrected password";
            }
            if (PasswordField.getText().length()==0){
                resultOfCheck="No password";
            }
            if (LogInField.getText().length()==0){
                resultOfCheck="No login";
            }
            if (DataManager.searchUser(user)) {
                resultOfCheck="success";
                login = LogInField.getText();
            }

            if (resultOfCheck.equals("success")) {
                ErrorText.setText(resultOfCheck);
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
                ErrorText.setText(resultOfCheck);
            }
        });

        SkipButton.setOnAction(event ->{

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
