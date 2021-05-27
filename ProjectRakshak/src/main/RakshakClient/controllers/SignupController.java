package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import request.Response;
import request.SignupRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SignupController {
    @FXML
    private Button loginButton, signupButton, goBackButton;
    @FXML
    private PasswordField passwordField, passwordAgainField;
    @FXML
    private TextField usernameField, firstnameField, lastnameField, emailField, userUIDField;
    @FXML
    private GridPane primaryGridPane;
    @FXML
    private Label statusLabel;

    public void initialize(){
        statusLabel.setVisible(false);
    }

    public void onBackClicked(ActionEvent ae){
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

    public void onSignupClicked(ActionEvent ae){
        String username = usernameField.getText(), password = passwordField.getText(), repassword = passwordAgainField.getText();
        String firstname = firstnameField.getText(), lastname = lastnameField.getText(), email = emailField.getText(), userUID = userUIDField.getText();

        if(username.trim().equals("") || password.trim().equals("") || repassword.trim().equals("") || userUID.trim().equals("")){
            statusLabel.setText("Fill all the fields");
            statusLabel.setVisible(true);
            return;
        }
        if(!password.equals(repassword)){
            statusLabel.setText("Unmatched Passwords");
            statusLabel.setVisible(true);
            return;
        }

        User user = new User(username,password,firstname,lastname,email,userUID);
        SignupRequest request = new SignupRequest(user);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(Main.socket == null){
                        Main.socket = new Socket(Main.ipaddress, Main.portno);
                        Main.oosTracker = new ObjectOutputStream(Main.socket.getOutputStream());
                        Main.oisTracker = new ObjectInputStream(Main.socket.getInputStream());
                    }
                    Main.oosTracker.writeObject(request);
                    System.out.println("SignUp request sent");
//                    Main.oisTracker = new ObjectInputStream(Main.socket.getInputStream());
                    Response response = (Response) Main.oisTracker.readObject();
                    System.out.println("Response read");
                    if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText("Signup successful");
                                alert.setContentText("Welcome Onboard: " + user.getFirstname());
                                alert.showAndWait();

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
                    else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                statusLabel.setText("User Already exists.");
                                statusLabel.setTextFill(Color.RED);
                                statusLabel.setVisible(true);
                            }
                        });
                    }

                } catch (IOException ie){
                    System.out.println("Error in signup: " + ie.getMessage());
                } catch (ClassNotFoundException ce){
                    System.out.println("Error receiving response: " + ce.getMessage());
                }
            }
        }).start();
    }

}
