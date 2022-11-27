package com.baza.digitalsecretary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        primaryStage.setTitle("Digital Secretary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /*public static void ChangeScene(String sceneName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(sceneName));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        primaryStage.setScene(new Scene(root));
    }*/
}