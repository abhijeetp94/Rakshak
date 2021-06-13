package controllers;

import data.Bed;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class BedViewController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private TableView<Bed> bedTableView;
    @FXML
    private TableColumn<Bed, String> bedTypeColumn, patientColumn, availableColumn, cabinNumberColumn, bedNumberColumn;

    private ObservableList<Bed> beds = FXCollections.observableArrayList();

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
        patientColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getPatient() == null){
                return new SimpleStringProperty("None");
            }
            return new SimpleStringProperty(cellData.getValue().getPatient().getFullName());
        });
    }

    public void bookBedClicked(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Book Bed");
        dialog.initOwner(primaryPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(""));
    }

    public void setData(List<Bed> bedList){
        beds.setAll(bedList);
        bedTableView.setItems(beds);
    }
}
