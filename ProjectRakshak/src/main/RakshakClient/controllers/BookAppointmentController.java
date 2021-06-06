package controllers;

import MainApp.Main;
import data.Doctor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import request.GetDoctorsRequest;
import request.Request;
import request.Response;

import java.io.IOException;

public class BookAppointmentController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private ComboBox<Doctor> doctorBox;
    @FXML
    private ComboBox<Integer> slotBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label statusLabel;
    private ObservableList<Doctor> doctors;

    public void initialize(){
        statusLabel.setVisible(false);
    }

    public void setData(){
        GetDoctorsRequest request = new GetDoctorsRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();



                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

}
