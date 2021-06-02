package controllers;

import data.TimeTable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TimeTableDialogController {

    @FXML
    private AnchorPane primaryPane;
    @FXML
    TableView<TimeTable> doctorTableView;
    @FXML
    private TableColumn<TimeTable, String> doctorColumn, fieldColumn, timeColumn, availableColumn;
}
