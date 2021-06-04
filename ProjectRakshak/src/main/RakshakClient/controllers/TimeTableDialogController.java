package controllers;

import data.TimeTable;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<TimeTable, String> availableColumn;

    public void setData(ObservableList<TimeTable> timeTables){
//        doctorColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("doctorName"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("fieldName"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, LocalTime>("shiftStartTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, LocalTime>("shiftEndTime"));
//        availableColumn.setCellValueFactory(new PropertyValueFactory<TimeTable, Boolean>("available"));

        doctorColumn.setCellValueFactory(cellData -> {
            String name = cellData.getValue().getDoctor().getFullName();
            return new SimpleStringProperty(name);
        });
        availableColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().isAvailable()){
                return new SimpleStringProperty("YES");
            } else {
                return new SimpleStringProperty("NO");
            }
        });

        doctorTableView.setItems(timeTables);

        doctorTableView.getColumns().setAll(doctorColumn, fieldColumn, startTimeColumn, endTimeColumn, availableColumn);

    }
}
