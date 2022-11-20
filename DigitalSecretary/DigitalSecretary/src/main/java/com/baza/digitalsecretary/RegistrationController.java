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

import static com.baza.digitalsecretary.HelloApplication.primaryStage;

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
            String resultOfCheck = "success";
            User user=new User(LoginField.getText(),PasswordField.getText());
            if (!FieldsChecker.isValidPassword(PasswordField.getText()).equals("Success")){
                resultOfCheck=FieldsChecker.isValidPassword(PasswordField.getText());
            }
            if (DataManager.searchByLogin(user)){
                resultOfCheck="User with this username exists";
            }
            if (LoginField.getText().length()==0){
                resultOfCheck="No login";
            }
            if (PasswordField.getText().length()==0){
                resultOfCheck="No Password";
            }
            if (!DataManager.searchByLogin(user) && PasswordField.getText().length()!=0 && LoginField.getText().length()!=0
                    && FieldsChecker.isValidPassword(PasswordField.getText()).equals("Success")) {
                resultOfCheck="success";
                DataManager.addUser(user);
            }
            ErrorText.setText(resultOfCheck);
            if (resultOfCheck == "success") {
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
    }
}
