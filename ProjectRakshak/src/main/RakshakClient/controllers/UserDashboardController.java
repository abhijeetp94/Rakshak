package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.TimeTable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
                        if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                            ArrayList timeTableList = (ArrayList) response.getResponseObject();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Dialog<ButtonType> dialog = new Dialog<>();
                                    dialog.initOwner(primaryPane.getScene().getWindow());
                                    dialog.setTitle("TimeTable");
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(getClass().getResource("/"));

                                }
                            });
                        }


                    } catch (IOException | ClassNotFoundException | ClassCastException ie){
                        ie.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
