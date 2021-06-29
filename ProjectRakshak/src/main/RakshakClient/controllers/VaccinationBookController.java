package controllers;

import data.Vaccination;
import data.Vaccine;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;


public class VaccinationBookController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Label dateLabel;
    @FXML
    private ComboBox<Vaccine> vaccineBox;
    @FXML
    private ComboBox<Integer> doseBox;
    @FXML
    private TextField userUIDField;

    public void initialize(){
        doseBox.getItems().add(0);
        doseBox.getItems().add(1);
        dateLabel.setText(LocalDate.now().toString());
        doseBox.setEditable(false);
        vaccineBox.setEditable(false);
    }

    public void setData(ObservableList<Vaccine> vaccines){
        vaccineBox.setItems(vaccines);
    }

    public Vaccination retrieveData(){
        Integer dose = doseBox.getSelectionModel().getSelectedItem();
        Vaccine vaccine = vaccineBox.getSelectionModel().getSelectedItem();
        LocalDate date = LocalDate.now();
        String userID = userUIDField.getText();

        return new Vaccination(userID, vaccine, dose, date);
    }

}
