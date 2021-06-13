package controllers;

import constants.BedType;
import data.Bed;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class BookBedController {
    @FXML
    private TextField bedNumberField, cabinNumberField, patientIDField, doctorIDField;
    @FXML
    private ComboBox<BedType> bedTypeBox;

    public void initialize(){
        bedTypeBox.getItems().setAll(BedType.COVID, BedType.GENERAL, BedType.ICU, BedType.VENTILATOR);
    }

    public void setData(Bed bed){
        bedNumberField.setText(String.valueOf(bed.getBedNumber()));
        cabinNumberField.setText(String.valueOf(bed.getCabinNumber()));
        bedTypeBox.getSelectionModel().select(bed.getType());
    }

    public Bed retrieveData(){
        String patientID = patientIDField.getText().trim();
        String doctorID = doctorIDField.getText().trim();
        Integer bedNumber = Integer.parseInt(bedNumberField.getText().trim());
        Integer cabinNumber = Integer.parseInt(cabinNumberField.getText().trim());

        return null;
    }
}
