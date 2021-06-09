package controllers;

import MainApp.Main;
import data.Doctor;
import data.Schedule;
import data.TimeTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import request.GetDoctorsRequest;
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
    private ObservableList<Doctor> doctors;

    public void initialize(){
        ScheduleRequest request1 = new ScheduleRequest();
        TimeTableRequest request2 = new TimeTableRequest();
        GetDoctorsRequest request3 = new GetDoctorsRequest();
        schedules = FXCollections.observableArrayList();
        timeTables = FXCollections.observableArrayList();
        doctors = FXCollections.observableArrayList();
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

                    Main.oosTracker.writeObject(request3);                     // fill the doctors
                    Response response2 = (Response) Main.oisTracker.readObject();
                    ArrayList<Doctor> doctors2 = (ArrayList<Doctor>) response2.getResponseObject();
                    doctors.setAll(doctors2);

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

    public void approveAppointmentsClicked(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(primaryPane.getScene().getWindow());
        dialog.setTitle("Approve Appointments");
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("ApproveAll", ButtonBar.ButtonData.OK_DONE));
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Approve", ButtonBar.ButtonData.OK_DONE));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ApproveAppointmentDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException ie){
            ie.printStackTrace();
        }
        
    }

    public void onToolBarButtonClicked(ActionEvent ae){

    }
}
