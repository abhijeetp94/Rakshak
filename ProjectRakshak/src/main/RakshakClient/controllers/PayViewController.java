package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import utils.PayManager;

import java.time.LocalDate;

public class PayViewController {
    @FXML
    AnchorPane primaryPane;
    @FXML
    TableView<PayManager> payTable;
    @FXML
    TableColumn<PayManager, String> monthColumn;
    @FXML
    TableColumn<PayManager, String> baseColumn, gradePayColumn, paidLeaveColumn, totalBonusColumn, taxColumn, finalSalaryColumn;

    public void initialize(){
        payTable.getColumns().setAll(monthColumn, baseColumn, gradePayColumn, paidLeaveColumn,
                totalBonusColumn, taxColumn, finalSalaryColumn);

        monthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonth())));
        baseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBaseSalary())));
        gradePayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGradePay())));
        paidLeaveColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPaidLeave())));

        totalBonusColumn.setCellValueFactory(cellData -> {
            double sum = 0;
            for(var a: cellData.getValue().getBonus()){
                sum += a.getAmount();
            }
            return new SimpleStringProperty(String.valueOf(sum));
        });

        finalSalaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(PayManager.calculateSalary(cellData.getValue()))));

    }

    public void setData(ObservableList<PayManager> list){
        payTable.setItems(list);
    }

}
