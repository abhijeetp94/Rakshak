package controllers;

import MainApp.Main;
import constants.ResponseCode;
import data.Bed;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import request.BookBedRequest;
import request.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        bedTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void bookBedClicked(){
        if(bedTableView.getSelectionModel().getSelectedItem().isOccupied() || bedTableView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Book Bed");
        dialog.initOwner(primaryPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(""));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException ie){
            ie.printStackTrace();
        }
        ButtonType bookButton = new ButtonType("Book Bed", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(bookButton);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        BookBedController controller = loader.getController();
        Bed selectedBed = bedTableView.getSelectionModel().getSelectedItem();
        controller.setData(selectedBed);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get().equals(bookButton)){
            Bed bed = controller.retrieveData();
            BookBedRequest request = new BookBedRequest(bed);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Main.oosTracker.writeObject(request);
                        Response response = (Response) Main.oisTracker.readObject();
                        if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Bed Booked");
                            alert.setHeaderText("The bed has successfully booked for " + bed.getPatient());
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Bed Not Booked");
                            alert.setHeaderText("The bed has not booked please try again.");
                        }

                    } catch (IOException | ClassNotFoundException ie){
                        ie.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void setData(List<Bed> bedList){
        beds.setAll(bedList);
        bedTableView.setItems(beds);
    }
}
