package MainApp;

import data.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {
    public static final int portno = 8800;
    public static ObjectInputStream oisTracker = null;
    public static ObjectOutputStream oosTracker = null;
    public static Socket socket=null;
    public static final String ipaddress = "localhost";
    public static User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/mainpage.fxml"));
        primaryStage.setTitle("Rakshak");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        if(socket!=null) {
            try {
                socket.close();
            } catch (IOException ie){
                System.out.println("Error while closing socket: " + ie.getMessage());
            }

        }
    }

    public static void loadControl(Pane primaryPane, String filename){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) primaryPane.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource(filename));

                } catch (IOException ie){
                    ie.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 800, 600));
            }
        });
    }
}
