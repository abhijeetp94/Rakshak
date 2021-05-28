package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class AdminRegisterController {
    @FXML
    private Tab staffTab, adminTab, doctorTab;
    @FXML
    private Button logoutButton, exitButton, goBackButton, registerStaffButton, registerDoctorButton, registerAdminButton;
    @FXML
    private TextField uidField, staffIDField, firstNameField, lastNameField, emailField, titleField, baseSalaryField, gradePayField;
    @FXML
    private PasswordField passwordField, rePasswordField, passwordFieldDoctor, rePasswordFieldDoctor, passwordFieldAdmin, rePasswordFieldAdmin;
    @FXML
    private TextField uidFieldDoctor, staffIDFieldDoctor, firstNameFieldDoctor, lastNameFieldDoctor, emailFieldDoctor, titleFieldDoctor, baseSalaryFieldDoctor, gradePayFieldDoctor, experienceFieldDoctor, educationFieldDoctor;
    @FXML
    private TextField uidFieldAdmin, staffIDFieldAdmin, firstNameFieldAdmin, lastNameFieldAdmin, emailFieldAdmin, titleFieldAdmin, baseSalaryFieldAdmin, gradePayFieldAdmin;




}
