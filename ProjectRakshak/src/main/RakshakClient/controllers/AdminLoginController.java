package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.Admin;
import data.Doctor;
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
import request.AdminLoginRequest;
import request.DoctorLoginRequest;
import request.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AdminLoginController {
    @FXML
    private GridPane primaryGridPane;
    @FXML
    private Button loginButton, goBackButton, userLogin, doctorLogin, staffLogin;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label headLabel, statusLabel;

    public void initialize(){
        statusLabel.setVisible(false);
    }


    public void onLoginClicked(ActionEvent ae){
        String staffID = usernameField.getText();
        String password = passwordField.getText();

        if(staffID.trim().equals("")){
            statusLabel.setVisible(true);
            statusLabel.setText("Enter valid username");
            statusLabel.setTextFill(Color.RED);
            return;
        }
        if (password.trim().equals("")){
            statusLabel.setVisible(true);
            statusLabel.setText("Invalid Password");
            statusLabel.setTextFill(Color.RED);
            return;
        }
        AdminLoginRequest loginRequest = new AdminLoginRequest(staffID, password);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.socket = new Socket(Main.ipaddress,Main.portno);
                    Main.oosTracker = new ObjectOutputStream(Main.socket.getOutputStream());
                    System.out.println("Object sent");
                    Main.oosTracker.writeObject(loginRequest);
                    Main.oisTracker = new ObjectInputStream(Main.socket.getInputStream());
                    Response response = (Response) Main.oisTracker.readObject();
                    if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        User user = (Admin) response.getResponseObject();
                        System.out.println("Username = " + user.getUsername());
                        Main.user = user;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                headLabel.setText("Welcome " + Main.user.getFirstname() + " " + Main.user.getLastname());
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Success");
                                alert.setHeaderText("Login Successful.");
                                alert.showAndWait();
                                Stage primaryStage = (Stage) primaryGridPane.getScene().getWindow();
                                Parent root = null;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("/AdminDashboard.fxml"));
                                } catch (IOException ie){
                                    ie.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 800, 600));
                            }
                        });
                    }
                    else if(response.getResponseCode().equals(ResponseCode.FAILURE)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                headLabel.setText("Incorrect Username or password");
                            }
                        });
                    }
                } catch (IOException ie){
                    System.out.println("Failed while connecting to socket " + ie.getMessage());
                } catch (ClassNotFoundException ce){
                    System.out.println("Problem reading object: " + ce.getMessage());
                }

            }
        }).start();

    }


    public void otherLoginClicked(ActionEvent ae){
        if(ae.getSource().equals(userLogin)){
            loadControl("/login.fxml");
        } else if(ae.getSource().equals(doctorLogin)){
            loadControl("/DoctorLogin.fxml");
        }  else if(ae.getSource().equals(staffLogin)){
            loadControl("/StaffLogin.fxml");
        }else if(ae.getSource().equals(goBackButton)){
            loadControl("/login.fxml");
        }
    }

    public void loadControl(String filename){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) primaryGridPane.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource(filename));

                } catch (IOException ie){
                    ie.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 800, 600));
            }
        });
    }
}
