package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.Attendance;
import data.Staff;
import data.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import request.*;
import utils.PayManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffDashboardController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Button doneButton, changePasswordButton, editDetailsButton, exitButton, logoutButton, goBackButton, accountButton, scheduleButton, payManagerButton, contactButton, attendanceButton;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField nameField, staffIDField, uidField, emailField, qrCodeField, titleField, phoneNumberField, dateJoinedField;

    public void initialize(){
        if(Main.user != null){
            Staff current = (Staff) Main.user;
            nameField.setText(current.getFullName());
            nameField.setEditable(false);
            staffIDField.setText(current.getStaffID());
            staffIDField.setEditable(false);
            uidField.setText(current.getUserUID());
            uidField.setEditable(false);
            emailField.setText(current.getEmail());
            emailField.setEditable(false);
            titleField.setText(current.getTitle());
            titleField.setEditable(false);
            phoneNumberField.setText(current.getPhone());
            phoneNumberField.setEditable(false);
            dateJoinedField.setText(current.getDateJoined().toString());
            dateJoinedField.setEditable(false);
            qrCodeField.setText(current.getQRCode());
            qrCodeField.setEditable(false);
            doneButton.setVisible(false);
        }
    }

    public void onPayManagerClicked(){
        GetStaffRequest staffRequest = new GetStaffRequest(((Staff) Main.user).getStaffID());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(staffRequest);
                    Response response = (Response) Main.oisTracker.readObject();
                    Staff staff;
                    if (response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        Main.user = (User) response.getResponseObject();
                        staff = (Staff) response.getResponseObject();
                        List<PayManager> payManagerList = new ArrayList<>();
                        for(var a:staff.getPayManager().entrySet()){
                            a.getValue().setMonth(a.getKey());
                            payManagerList.add(a.getValue());
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.initOwner(primaryPane.getScene().getWindow());
                                dialog.setTitle("PayManager");
                                ObservableList<PayManager> payList = FXCollections.observableArrayList();
                                payList.setAll(payManagerList);
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/PayViewDialog.fxml"));
                                try {
                                    dialog.getDialogPane().setContent(loader.load());
                                } catch (IOException ie){
                                    ie.printStackTrace();
                                }
                                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                                PayViewController controller = loader.getController();
                                controller.setData(payList);
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

    public void markAttendanceClicked(){
        Attendance attendance = new Attendance(LocalDate.now(), (Staff) Main.user, true);
        MarkAttendanceRequest request = new MarkAttendanceRequest(attendance);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    if (response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Attendance");
                                alert.setHeaderText("Your attendance for today has been marked");
                                alert.showAndWait();
                            }
                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Attendance");
                                alert.setHeaderText("Your attendance for today has already been marked");
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

    public void onDoneClicked(){
        Staff current = (Staff) Main.user;
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String uid = uidField.getText().trim();
        String phone = phoneNumberField.getText().trim();
        current.setFullName(name);
        current.setUserUID(uid);
        current.setPhone(phone);
        current.setEmail(email);
        EditStaffDetailsRequest request = new EditStaffDetailsRequest(current);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    if (response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Details");
                                alert.setHeaderText("Details successfully updated");
                                alert.showAndWait();
                                editDetailsButton.setVisible(true);
                                doneButton.setVisible(false);
                            }
                        });
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Details");
                        alert.setHeaderText("Ran into some error");
                        alert.showAndWait();
                    }
                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

    public void onEditClicked(){
        emailField.setEditable(true);
        phoneNumberField.setEditable(true);
        nameField.setEditable(true);
        uidField.setEditable(true);
        doneButton.setVisible(true);
        editDetailsButton.setVisible(false);
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
