package controllers;

import data.TimeTable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalTime;

public class TimeTableDialogController {

    @FXML
    private AnchorPane primaryPane;
    @FXML
    TableView<TimeTable> doctorTableView;
    @FXML
    private TableColumn<TimeTable, String> doctorColumn, fieldColumn;
    @FXML
    private TableColumn<TimeTable, LocalTime> startTimeColumn, endTimeColumn;
    @FXML
    private TableColumn<TimeTable, Boolean> availableColumn;

    public void setData(ObservableList<TimeTable> timeTables){
        doctorColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("doctor.fullName"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("doctor.title"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, LocalTime>("shiftStartTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, LocalTime>("shiftEndTime"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, Boolean>("doctor.isAvailable"));
        doctorTableView.setItems(timeTables);
    }
}
