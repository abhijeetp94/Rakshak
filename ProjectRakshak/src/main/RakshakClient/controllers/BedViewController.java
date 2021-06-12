package controllers;

import data.Bed;
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

    public void setData(List<Bed> bedList){

    }
}
