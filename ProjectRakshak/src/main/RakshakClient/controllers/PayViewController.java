package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import utils.PayManager;

public class PayViewController {
    @FXML
    AnchorPane primaryPane;
    @FXML
    TableView<PayManager> payTable;
    @FXML
    TableColumn<PayManager, String> monthColumn;
    @FXML
    TableColumn<PayManager, Double> baseColumn, gradePayColumn, paidLeaveColumn, totalBonusColumn, taxColumn, finalSalaryColumn;


    
}
