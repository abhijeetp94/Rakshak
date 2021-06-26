package controllers;

import MainApp.Main;
import constants.BedType;
import data.Bed;
import data.Doctor;
import data.User;
import javafx.application.Platform;
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
    @FXML
    private Bed bed;

    public void initialize(){
        bedTypeBox.getItems().setAll(BedType.COVID, BedType.GENERAL, BedType.ICU, BedType.VENTILATOR);
        bedNumberField.setEditable(false);
        cabinNumberField.setEditable(false);
        bedTypeBox.setEditable(false);
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
        GetUserRequest userRequest = new GetUserRequest(patientID);
        GetDoctorsRequest doctorsRequest = new GetDoctorsRequest(doctorID);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(userRequest);
                    Response response1 = (Response) Main.oisTracker.readObject();
                    Main.oosTracker.writeObject(doctorsRequest);
                    Response response2 = (Response) Main.oisTracker.readObject();
                    User user1 = (User) response1.getResponseObject();
                    Doctor doctor = (Doctor) response2.getResponseObject();
                    bed = new Bed(bedNumber, cabinNumber, type, user1, doctor, true);

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();

                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
        System.out.println("In Book Bed " + (bed == null));
        return bed;
    }
}
