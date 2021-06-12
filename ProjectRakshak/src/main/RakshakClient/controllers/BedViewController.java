package controllers;

import data.Bed;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class BedViewController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private TableView<Bed> bedTableView;
    @FXML
    private TableColumn<Bed, String> bedTypeColumn, patientColumn, availableColumn, cabinNumberColumn, bedNumberColumn;

    private ObservableList<Bed> beds;

    public void initialize(){
        bedTableView.getColumns().setAll(bedNumberColumn, bedTypeColumn, cabinNumberColumn, availableColumn, patientColumn);
        bedNumberColumn.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().getBedNumber())));
        cabinNumberColumn.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().getCabinNumber())));
        bedTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        availableColumn.setCellValueFactory(cellData -> {
            boolean av = cellData.getValue().isOccupied();
            if(av){
                return new SimpleStringProperty("Occupied");
            }
            return new SimpleStringProperty("Available");
        });
        patientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getFullName()));
    }

    public void setData(List<Bed> bedList){
        beds.setAll(bedList);
        bedTableView.setItems(beds);
    }
}
