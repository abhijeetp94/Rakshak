package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.Bed;
import data.Doctor;
import data.Schedule;
import data.TimeTable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import request.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceptionDashboardController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Button approveAppointmentButton, allotBedButton, bedAvailabilityButton, bloodBankApproveButton, plasmaBankApproveButton, checkAvailabilityButton, medicalStoreButton, registerUserButton, scheduleButton, staffDashboardButton, logoutButton, exitButton;

    private ObservableList<Schedule> schedules;
    private ObservableList<TimeTable> timeTables;
    private ObservableList<Doctor> doctors;
    private ObservableList<Bed> beds = FXCollections.observableArrayList();

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
        ApproveAppointmentController controller = loader.getController();
        controller.setData(schedules);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && (result.get().getText().equals("Approve") || result.get().getText().equals("ApproveAll"))){
            List<Schedule> toApprove;
            if(result.get().getText().equals("Approve")){
                toApprove = controller.approveAppointments();
            }
            else{
                toApprove = controller.getAllAppointments();
            }
            AppointmentApproveRequest request = new AppointmentApproveRequest(toApprove);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Main.oosTracker.writeObject(request);
                        Response response = (Response) Main.oisTracker.readObject();
                        boolean res = (Boolean) response.getResponseObject();
                        if(res){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Appointment");
                                    alert.setHeaderText("Appointments Approved.");
                                    alert.showAndWait();
                                }
                            });
                        }
                        else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Appointment");
                                    alert.setHeaderText("Some error occurred.");
                                    alert.showAndWait();
                                }
                            });
                        }
                    } catch (IOException | ClassNotFoundException ie){
                        ie.printStackTrace();
                    }
                }
            }).start();
        }

    }
    public void onBedAvailabilityClicked(){
        BedAvailabilityRequest request = new BedAvailabilityRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        ArrayList<Bed> bedList = (ArrayList<Bed>) response.getResponseObject();
                        beds.setAll(bedList);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setTitle("Bed Availability");
                                dialog.initOwner(primaryPane.getScene().getWindow());
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/BedViewDialog.fxml"));
                                try {
                                    dialog.getDialogPane().setContent(loader.load());
                                } catch (IOException ie){
                                    ie.printStackTrace();
                                }
                                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                                BedViewController controller = loader.getController();
                                controller.setData(beds);
                                dialog.showAndWait();
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

    }
}
