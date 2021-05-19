package mainPack;

import constants.RequestCode;
import request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
        try {
            Main.oisTracker = new ObjectInputStream(this.socket.getInputStream());
            Main.oosTracker = new ObjectOutputStream(this.socket.getOutputStream());
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            Request request;
            try {
                request = (Request) Main.oisTracker.readObject();
            } catch (IOException | ClassNotFoundException ie){
                ie.printStackTrace();
                return;
            }

            if (request.getRequestCode().equals(RequestCode.LOGIN_REQUEST)){
                System.out.println("Login Request.");
            }


        }

    }
}
