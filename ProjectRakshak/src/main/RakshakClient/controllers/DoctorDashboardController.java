package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.Doctor;
import data.Schedule;
import data.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import request.Response;
import request.ScheduleRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDashboardController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Button exitButton, logoutButton, goBackButton, accountButton, scheduleButton, payManagerButton, contactButton, attendanceButton;
    @FXML
    private Label dateLabel;
    @FXML
    private TableView<Schedule> scheduleTable;
    @FXML
    private TableColumn<Schedule, String> patientColumn, slotColumn;
    private ObservableList<Schedule> scheduleList;

    public void initialize(){
        patientColumn.setCellValueFactory(cellData -> {
            String name = cellData.getValue().getUser().getFullName();
            return new SimpleStringProperty(name);
        });
        slotColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShift().toString()));
        scheduleTable.getColumns().setAll(patientColumn, slotColumn);

        System.out.println(((Doctor) Main.user).getDoctorID());
        ScheduleRequest request = new ScheduleRequest(((Doctor) Main.user).getDoctorID());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Request Sent");
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        ArrayList<Schedule> schedules = (ArrayList<Schedule>) response.getResponseObject();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                scheduleList = FXCollections.observableArrayList();
                                scheduleList.setAll(schedules);

                                scheduleTable.setItems(scheduleList);
                            }
                        });

                    }
                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

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

    }
}
