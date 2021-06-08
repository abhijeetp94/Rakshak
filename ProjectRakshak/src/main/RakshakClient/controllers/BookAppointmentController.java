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
import javafx.util.Pair;
import request.GetDoctorsRequest;
import request.Request;
import request.Response;
import request.ScheduleRequest;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookAppointmentController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private ComboBox<Doctor> doctorBox;
    @FXML
    private ComboBox<Pair<LocalTime, LocalTime>> slotBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label statusLabel;
    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private ObservableList<Schedule> schedules = FXCollections.observableArrayList();

    public void initialize(){
        statusLabel.setVisible(false);
        doctorBox.setVisibleRowCount(5);

    }

    public void onDoctorSelected(){
        if(!doctorBox.getSelectionModel().isEmpty()){
            ObservableList<Pair<LocalTime, LocalTime>> slots = FXCollections.observableArrayList();
            slots.setAll(doctorBox.getSelectionModel().getSelectedItem().getShifts());
            slotBox.setItems(slots);
        }
    }

    public void setData(List<Doctor> doctors, List<Schedule> schedules){
        this.doctors.setAll(doctors);
        this.schedules.setAll(schedules);
        doctorBox.setItems(this.doctors);
    }



    public Schedule processData(){
        Doctor doc = doctorBox.getSelectionModel().getSelectedItem();
        Pair<LocalTime, LocalTime> slot = slotBox.getSelectionModel().getSelectedItem();
        int shift = doc.getShifts().indexOf(slot) + 1;
        LocalDate date = datePicker.getValue();
        return new Schedule(date, shift, doc, Main.user);
    }

}
