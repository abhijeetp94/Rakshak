package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button loginButton, signupButton;
    @FXML
    private GridPane primaryGridPane;

    public void onLoginClicked(ActionEvent e){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) primaryGridPane.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/login.fxml"));

                } catch (IOException ie){
                    ie.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 800, 600));
            }
        });
    }

    public void onSignupClicked(ActionEvent e){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) primaryGridPane.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/signup.fxml"));

                } catch (IOException ie){
                    ie.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 800, 600));
            }
        });
    }
}
