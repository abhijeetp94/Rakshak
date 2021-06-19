package controllers;

import MainApp.Main;
import constants.ResponseCode;
import constants.StaffType;
import data.Admin;
import data.Doctor;
import data.Staff;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import request.Response;
import request.StaffRegisterRequest;
import utils.PayManager;

import java.io.IOException;
import java.util.ArrayList;

public class AdminRegisterController {
    @FXML
    private TabPane theTabPane;
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Tab staffTab, adminTab, doctorTab;
    @FXML
    private Button logoutButton, exitButton, goBackButton, registerStaffButton, registerDoctorButton, registerAdminButton;
    @FXML
    private TextField uidField, staffIDField, firstNameField, lastNameField, emailField, titleField, baseSalaryField, gradePayField;
    @FXML
    private PasswordField passwordField, rePasswordField, passwordFieldDoctor, rePasswordFieldDoctor, passwordFieldAdmin, rePasswordFieldAdmin;
    @FXML
    private TextField uidFieldDoctor, staffIDFieldDoctor, firstNameFieldDoctor, lastNameFieldDoctor, emailFieldDoctor, titleFieldDoctor, baseSalaryFieldDoctor, gradePayFieldDoctor, experienceFieldDoctor, educationFieldDoctor, cabinNumberField;
    @FXML
    private TextField uidFieldAdmin, staffIDFieldAdmin, firstNameFieldAdmin, lastNameFieldAdmin, emailFieldAdmin, titleFieldAdmin, baseSalaryFieldAdmin, gradePayFieldAdmin;
    @FXML
    private CheckBox receptionistBox;

    @FXML
    public void onRegisterButtonClicked(ActionEvent ae){
        String uid, staffID, firstname, lastname, email, title, password, rePassword;
        Integer cabinNumber;
        Double baseSalary, gradePay;
        boolean isReceptionist;
        String[] experience, education;
        System.out.println(theTabPane.getSelectionModel().getSelectedItem().toString());
        StaffRegisterRequest request = null;
        if(theTabPane.getSelectionModel().getSelectedItem().equals(staffTab) && ae.getSource().equals(registerStaffButton)){
            uid = uidField.getText().trim();
            staffID = staffIDField.getText().trim();
            firstname = firstNameField.getText().trim();
            lastname = lastNameField.getText().trim();
            email = emailField.getText().trim();
            title = titleField.getText().trim();
            isReceptionist = receptionistBox.isSelected();
            System.out.println(isReceptionist);
            gradePay = Double.parseDouble(gradePayField.getText().trim());
            baseSalary = Double.parseDouble(baseSalaryField.getText().trim());
            rePassword = rePasswordField.getText().trim();
            password = passwordField.getText().trim();
            PayManager payManager = new PayManager(gradePay, baseSalary, 0, new ArrayList<>());
            Staff staff = new Staff(staffID, password, firstname, lastname, email, uid, staffID, title, payManager, new ArrayList<>(), false, isReceptionist);
            request = new StaffRegisterRequest(staff, StaffType.STAFF);


        } else if(theTabPane.getSelectionModel().getSelectedItem().equals(adminTab) && ae.getSource().equals(registerAdminButton)){
            uid = uidFieldAdmin.getText().trim();
            staffID = staffIDFieldAdmin.getText().trim();
            firstname = firstNameFieldAdmin.getText().trim();
            lastname = lastNameFieldAdmin.getText().trim();
            email = emailFieldAdmin.getText().trim();
            title = titleFieldAdmin.getText().trim();
            gradePay = Double.parseDouble(gradePayFieldAdmin.getText().trim());
            baseSalary = Double.parseDouble(baseSalaryFieldAdmin.getText().trim());
            password = passwordFieldAdmin.getText().trim();
            rePassword = rePasswordFieldAdmin.getText().trim();
            PayManager payManager = new PayManager(gradePay, baseSalary, 0, new ArrayList<>());
            Admin admin = new Admin(staffID, password, firstname, lastname, email, uid, staffID, title, payManager, new ArrayList<>());
            request = new StaffRegisterRequest(admin, StaffType.ADMIN);



        } else if(theTabPane.getSelectionModel().getSelectedItem().equals(doctorTab) && ae.getSource().equals(registerDoctorButton)){
            uid = uidFieldDoctor.getText().trim();
            staffID = staffIDFieldDoctor.getText().trim();
            firstname = firstNameFieldDoctor.getText().trim();
            lastname = lastNameFieldDoctor.getText().trim();
            email = emailFieldDoctor.getText().trim();
            title = titleFieldDoctor.getText().trim();
            experience = experienceFieldDoctor.getText().trim().split(", ");
            education = educationFieldDoctor.getText().trim().split(", ");
            baseSalary = Double.parseDouble(baseSalaryFieldDoctor.getText().trim());
            gradePay = Double.parseDouble(gradePayFieldDoctor.getText().trim());
            cabinNumber = Integer.parseInt(cabinNumberField.getText().trim());
            password = passwordFieldDoctor.getText().trim();
            rePassword = rePasswordFieldDoctor.getText().trim();
            PayManager payManager = new PayManager(gradePay, baseSalary, 0);
            Doctor doctor = new Doctor(staffID, password, firstname, lastname, email, uid, staffID, payManager, new ArrayList<>(), title,  education, Integer.parseInt(experience[0]), cabinNumber, true);
            request = new StaffRegisterRequest(doctor, StaffType.DOCTOR);

        }
        handleRegisterRequest(request);
    }

    public void handleRegisterRequest(StaffRegisterRequest request){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(request);
                    System.out.println("Sending register request");
                    Response response = (Response) Main.oisTracker.readObject();
                    System.out.println("Register Response received");
                    if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Success");
                                alert.setHeaderText("Staff member successfully registered");
                                alert.showAndWait();
                                Stage primaryStage = (Stage) primaryPane.getScene().getWindow();
                                Parent root = null;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("/AdminDashboard.fxml"));

                                } catch (IOException ie){
                                    ie.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 800, 600));
                            }
                        });
                    } else if (response.getResponseCode().equals(ResponseCode.FAILURE)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Registration Failed");
                                alert.setHeaderText("Staff member Already exists.");
                                alert.showAndWait();
                            }
                        });
                    }


                } catch (IOException | ClassNotFoundException ie){
                    System.out.println("Error in sending register request: "+ie.getMessage());
                }
            }
        }).start();

    }
}
