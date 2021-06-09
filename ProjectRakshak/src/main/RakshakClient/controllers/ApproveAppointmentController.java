package controllers;

import data.Doctor;
import data.Schedule;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.awt.*;

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

    public void initialize(){
        scheduleTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        scheduleTableView.getColumns().setAll(doctorColumn, slotColumn, userColumn);

        doctorTableView.getColumns().setAll(doctorDetailsColumn, shiftColumn, patientColumn);
    }

}
