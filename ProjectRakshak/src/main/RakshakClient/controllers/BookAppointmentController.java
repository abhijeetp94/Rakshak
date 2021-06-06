package controllers;

import MainApp.Main;
import data.Doctor;
import data.Schedule;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import request.GetDoctorsRequest;
import request.Request;
import request.Response;
import request.ScheduleRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookAppointmentController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private ComboBox<Doctor> doctorBox;
    @FXML
    private ComboBox<Integer> slotBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label statusLabel;
    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();

    public void initialize(){
        statusLabel.setVisible(false);
    }

    public void setData(List<Doctor> doctors){
        this.doctors.setAll(doctors);
        doctorBox.setItems(this.doctors);

    }

    public Schedule processData(){

        return null;
    }

}
