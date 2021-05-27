package controllers;

import MainApp.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminDashboardController {
    @FXML
    private Button registerStaffButton, logoutButton, exitButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private AnchorPane primaryPane;

    public void initialize(){
        welcomeLabel.setText("Welcome " + Main.user.getFirstname());
    }

    public void onRegisterClicked(ActionEvent ae){
        Main.loadControl(primaryPane, "/AdminRegister.fxml");
    }

    public void onToolBarButtonClicked(ActionEvent ae){
        if(ae.getSource().equals(logoutButton)){
            Main.user = null;
            try {
                Main.socket.close();
            } catch (IOException ie){
                System.out.println("Error Logging out: " + ie.getMessage());
            }
            Main.loadControl(primaryPane, "/login.fxml");
        }
        if(ae.getSource().equals(exitButton)){
            Platform.exit();
        }

    }

}
