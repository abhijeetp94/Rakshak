package controllers;

import MainApp.Main;
import data.Schedule;
import data.TimeTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import request.Response;
import request.ScheduleRequest;
import request.TimeTableRequest;

import java.io.IOException;
import java.util.ArrayList;

public class ReceptionDashboardController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Button approveAppointmentButton, allotBedButton, bloodBankApproveButton, plasmaBankApproveButton, checkAvailabilityButton, medicalStoreButton, registerUserButton, scheduleButton, staffDashboardButton, logoutButton, exitButton;

    private ObservableList<Schedule> schedules;
    private ObservableList<TimeTable> timeTables;

    public void initialize(){
        ScheduleRequest request1 = new ScheduleRequest();
        TimeTableRequest request2 = new TimeTableRequest();
        schedules = FXCollections.observableArrayList();
        timeTables = FXCollections.observableArrayList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(request1);                     // fill the schedule
                    Response response = (Response) Main.oisTracker.readObject();
                    ArrayList<Schedule> schedules1 = (ArrayList<Schedule>) response.getResponseObject();
                    schedules.setAll(schedules1);

                    Main.oosTracker.writeObject(request2);                     // fill the timetable
                    Response response1 = (Response) Main.oisTracker.readObject();
                    ArrayList<TimeTable> timeTables1 = (ArrayList<TimeTable>) response1.getResponseObject();
                    timeTables.setAll(timeTables1);

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

    public void approveAppointmentsClicked(){

    }

    public void onToolBarButtonClicked(ActionEvent ae){

    }
}
