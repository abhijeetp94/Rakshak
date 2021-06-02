package controllers;

import MainApp.Main;
import data.TimeTable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import request.Request;
import request.Response;
import request.TimeTableRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDashboardController {
    @FXML
    private Button accountButton, timeTableButton, historyButton, logoutButton, contactButton, bedButton, appointmentButton, exitButton, bloodBankButton, plasmaBankButton, vaccineButton, medicalStoreButton, seeAppointmentButton;
    @FXML
    private AnchorPane primaryPane;

    public void onToolBarButtonClicked(ActionEvent ae){
        if(ae.getSource().equals(logoutButton)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Do you want to logout?");
            alert.showAndWait().ifPresent(response -> {
                if(response.equals(ButtonType.OK)){
                    Main.user = null;
                    try {
                        Main.socket.close();
                    } catch (IOException ie){
                        System.out.println("Error Logging out: " + ie.getMessage());
                    }
                    Main.loadControl(primaryPane, "/login.fxml");
                }
            });

        }
        if(ae.getSource().equals(exitButton)){
            Main.user = null;
            Platform.exit();
        }
        if(ae.getSource().equals(timeTableButton)){
            TimeTableRequest request = new TimeTableRequest();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Main.oosTracker.writeObject(request);
                        Response response = (Response) Main.oisTracker.readObject();
                        ArrayList<TimeTable> timeTableList = (ArrayList<>) response.getResponseObject();
                    } catch (IOException | ClassNotFoundException ie){
                        ie.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
