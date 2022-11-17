package com.baza.digitalsecretary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.baza.digitalsecretary.HelloApplication.primaryStage;

public class AppController {

    @FXML
    private Button ExitButton;

    @FXML
    void initialize() {
        ExitButton.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("authorization.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            primaryStage.setScene(new Scene(root));
            /*Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            BackToAuthorizationButton.getScene().getWindow().hide();
            GoToRegistrationButton.getScene().getWindow().hide();
            stage.showAndWait();*/
        });
    }
}
