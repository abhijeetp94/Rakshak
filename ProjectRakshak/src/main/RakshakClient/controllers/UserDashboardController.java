package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.Doctor;
import data.Schedule;
import data.TimeTable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import request.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDashboardController {
    @FXML
    private Button accountButton, timeTableButton, historyButton, logoutButton, contactButton, bedButton, appointmentButton, exitButton, bloodBankButton, plasmaBankButton, vaccineButton, medicalStoreButton, seeAppointmentButton;
    @FXML
    private AnchorPane primaryPane;

    public void onAppointmentButtonClicked(){                                       // if the book appointment button is clicked
        GetDoctorsRequest request1 = new GetDoctorsRequest();
        ScheduleRequest request2 = new ScheduleRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(request1);
                    Response response = (Response) Main.oisTracker.readObject();
                    ArrayList<Doctor> doctors = (ArrayList<Doctor>) response.getResponseObject();

                    Main.oosTracker.writeObject(request2);
                    Response response1 = (Response) Main.oisTracker.readObject();
                    ArrayList<Schedule> schedule = (ArrayList<Schedule>) response1.getResponseObject();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            Dialog<ButtonType> dialog = new Dialog<>();
                            dialog.initOwner(primaryPane.getScene().getWindow());
                            dialog.setTitle("Book Appointment");
                            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/BookAppointmentDialog.fxml"));
                            try {
                                dialog.getDialogPane().setContent(loader.load());
                            } catch (IOException ie){
                                ie.printStackTrace();
                            }
                            BookAppointmentController controller = loader.getController();
                            controller.setData(doctors, schedule);
                            Optional<ButtonType> result = dialog.showAndWait();
                            if(result.isPresent() && result.get().equals(ButtonType.OK)){
                                Schedule schedule = controller.processData();
                                if((!schedule.getDoctor().isAvailable()) && schedule.getTheDate().equals(LocalDate.now())){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Appointment");
                                    alert.setHeaderText("Selected Doctor Not Available Today.");
                                    alert.showAndWait();
                                    return;
                                }
                                AppointmentRequest appointmentRequest = new AppointmentRequest(schedule);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Main.oosTracker.writeObject(appointmentRequest);
                                            Response response1 = (Response) Main.oisTracker.readObject();
                                            if(response1.getResponseCode().equals(ResponseCode.SUCCESS)){
                                                Platform.runLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setTitle("Appointment");
                                                        alert.setHeaderText("Appointment requested, we will get back to you soon.");
                                                        alert.showAndWait();
                                                    }
                                                });
                                            } else {
                                                Platform.runLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setTitle("Appointment");
                                                        alert.setHeaderText("Appointment is already booked, we will get back to you soon.");
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
                    });

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

    // when toolbar buttons are pressed
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
                        System.out.println("Reached here");
                        Main.oosTracker.writeObject(request);
                        Response response = (Response) Main.oisTracker.readObject();
                        if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                            ArrayList<TimeTable> timeTableList = (ArrayList<TimeTable>) response.getResponseObject();
                            System.out.println("Response object get");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Dialog<ButtonType> dialog = new Dialog<>();
                                    dialog.initOwner(primaryPane.getScene().getWindow());
                                    dialog.setTitle("TimeTable");
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(getClass().getResource("/TimeTableDialog.fxml"));
                                    System.out.println("Dialog Created");
                                    try {
                                        dialog.getDialogPane().setContent(loader.load());
                                    } catch (IOException ie){
                                        ie.printStackTrace();
                                    }
                                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                                    TimeTableDialogController controller = loader.getController();
                                    ObservableList<TimeTable> timeTables = FXCollections.observableArrayList();
                                    timeTables.setAll(timeTableList);
                                    controller.setData(timeTables);
                                    System.out.println("Dialog Shown");
                                    dialog.showAndWait();
                                }
                            });
                        }
                        else {
                            System.out.println("Some error");
                        }

                    } catch (IOException | ClassNotFoundException | ClassCastException ie){
                        ie.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
