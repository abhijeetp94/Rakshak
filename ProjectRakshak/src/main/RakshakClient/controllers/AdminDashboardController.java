package controllers;

import MainApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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

    }

}
