package controllers;

import data.Admin;
import data.Doctor;
import data.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import request.StaffRegisterRequest;
import utils.PayManager;

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
    public void onRegisterButtonClicked(ActionEvent ae){
        String uid, staffID, firstname, lastname, email, title, password, rePassword;
        Integer cabinNumber;
        Double baseSalary, gradePay;
        String[] experience, education;
        StaffRegisterRequest staffRegisterRequest = null;
        if(theTabPane.getSelectionModel().getSelectedItem().equals(staffTab) && ae.getSource().equals(registerStaffButton)){
            uid = uidField.getText().trim();
            staffID = staffIDField.getText().trim();
            firstname = firstNameField.getText().trim();
            lastname = lastNameField.getText().trim();
            email = emailField.getText().trim();
            title = titleField.getText().trim();
            baseSalary = Double.parseDouble(baseSalaryField.getText().trim());
            gradePay = Double.parseDouble(gradePayField.getText().trim());
            password = passwordField.getText().trim();
            rePassword = rePasswordField.getText().trim();
            PayManager payManager = new PayManager(gradePay, baseSalary, 0, new ArrayList<>());
            Staff staff = new Staff(staffID, password, firstname, lastname, email, uid, staffID, title, payManager, new ArrayList<>(), false);


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
            PayManager payManager = new PayManager(gradePay, baseSalary, 0, new ArrayList<>());
            Doctor doctor = new Doctor(staffID, password, firstname, lastname, email, uid, staffID, title, payManager, new ArrayList<>());



        }
    }

}
