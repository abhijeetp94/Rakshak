package controllers;

import MainApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import request.LoginRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class LoginController {
    @FXML
    private GridPane primaryGridPane;
    @FXML
    private Button loginButton, signupButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label headLabel;

    public void onLoginClicked(ActionEvent ae){
        String username = usernameField.getText();
        String password = passwordField.getText();

        LoginRequest loginRequest = new LoginRequest(username, password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.socket = new Socket(Main.ipaddress,Main.portno);
//                    Main.oisTracker = new ObjectInputStream(Main.socket.getInputStream());
                    Main.oosTracker = new ObjectOutputStream(Main.socket.getOutputStream());
                    System.out.println("Object sent");
                    Main.oosTracker.writeObject(loginRequest);
                    System.out.println("Object should be printed");
                } catch (IOException ie){
                    System.out.println("Failed while connecting to socket " + ie.getMessage());
                }
            }
        }).start();


    }

}
