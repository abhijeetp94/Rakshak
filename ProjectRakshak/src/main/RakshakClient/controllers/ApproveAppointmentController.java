package controllers;

import data.Doctor;
import data.Schedule;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.awt.*;
import java.util.List;

public class ApproveAppointmentController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Button approveAllButton, approveSelectedButton;
    @FXML
    private TableView<Schedule> scheduleTableView;
    @FXML
    private TableView<Doctor> doctorTableView;
    @FXML
    private TableColumn<Schedule, String> doctorColumn, userColumn;
    @FXML
    private TableColumn<Schedule, Integer> slotColumn;
    @FXML
    private TableColumn<Doctor, Integer> shiftColumn, patientColumn;
    @FXML
    private TableColumn<Doctor, String> doctorDetailsColumn;
    @FXML
    private ContextMenu contextMenu;
    ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();

    public void initialize(){
        scheduleTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        scheduleTableView.getColumns().setAll(doctorColumn, slotColumn, userColumn);

        doctorTableView.getColumns().setAll(doctorDetailsColumn, shiftColumn, patientColumn);

    }

    public void setData(List<Schedule> schedules){
        doctorColumn.setCellValueFactory(cellData -> {
            String data = "Dr. " + cellData.getValue().getDoctor().getFullName() + " (" + cellData.getValue().getDoctor().getDoctorID() + ")";
            return new SimpleStringProperty(data);
        });
        slotColumn.setCellValueFactory(cellData->{
            Integer data = cellData.getValue().getShift();
            IntegerProperty property = new SimpleIntegerProperty(data);
            return property.asObject(); //*** important to make integer property work
        });
        userColumn.setCellValueFactory(cellData->{
            String data = cellData.getValue().getUser().getFullName();
            return new SimpleStringProperty(data);
        });
        for(Schedule a: schedules){
            if(!a.getApproved()){
                scheduleList.add(a);
            }
        }
        scheduleTableView.setItems(scheduleList);


    }

    public List<Schedule> approveAppointments(){
        ObservableList<Schedule> sc = scheduleTableView.getSelectionModel().getSelectedItems();
        return sc;
    }
    public List<Schedule> getAllAppointments(){
        return scheduleList;
    }

}
