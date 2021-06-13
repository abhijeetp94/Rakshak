package controllers;

import MainApp.Main;
import constants.BedType;
import data.Bed;
import data.Doctor;
import data.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import request.GetDoctorsRequest;
import request.GetUserRequest;
import request.Response;

import java.io.IOException;


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
        BedType type = bedTypeBox.getSelectionModel().getSelectedItem();
        final User[] user = new User[1];
        final Doctor[] doctor = {null};
        GetUserRequest userRequest = new GetUserRequest(patientID);
        GetDoctorsRequest doctorsRequest = new GetDoctorsRequest(doctorID);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(userRequest);
                    Response response1 = (Response) Main.oisTracker.readObject();
                    Main.oosTracker.writeObject(doctorsRequest);
                    Response response2 = (Response) Main.oisTracker.readObject();
                    User user1 = (User) response1.getResponseObject();
                    user[0] = user1;
                    doctor[0] = (Doctor) response2.getResponseObject();

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();

        return new Bed(bedNumber, cabinNumber, type, user[0], doctor[0], true);
    }
}
