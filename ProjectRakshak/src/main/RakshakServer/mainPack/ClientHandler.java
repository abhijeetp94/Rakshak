package mainPack;

import authenticationHandlers.LoginHandler;
import constants.RequestCode;
import constants.ResponseCode;
import data.User;
import request.LoginRequest;
import request.Request;
import request.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    public static ObjectOutputStream oosTracker;
    public static ObjectInputStream oisTracker;
    public ClientHandler(Socket socket){
        this.socket = socket;
        try {
            oisTracker = new ObjectInputStream(this.socket.getInputStream());
            oosTracker = new ObjectOutputStream(this.socket.getOutputStream());
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean quit = false;
        while (!quit) {
            Request request = null;
            Object ob;
            try {
                System.out.println("Here");
                ob = oisTracker.readObject();
                System.out.println("Object Read" + ob.toString());
                request = (Request) ob;
                System.out.println("Object Read" + request.getRequestCode());

            } catch (IOException | ClassNotFoundException ie){
                quit = true;
                ie.printStackTrace();

            }
            try {


                // If request is login request
                // Send Response
                if (request.getRequestCode().equals(RequestCode.LOGIN_REQUEST)){
                    System.out.println("Login Request.");
                    User result = LoginHandler.verifyUser((LoginRequest) request);
                    if (result!=null){                                          // send response if user exists
                        Response response = new Response("LOGIN", ResponseCode.SUCCESS, result);
                        try {
                            oosTracker.writeObject(response);
                        } catch (IOException ie){
                            System.out.println("Problem Sending Object " + ie.getMessage());
                        }
                    } else {                                                    // if User not found
                        Response response = new Response("LOGIN", ResponseCode.FAILURE, result);
                        try {
                            oosTracker.writeObject(response);
                        } catch (IOException ie){
                            System.out.println("Problem Sending Object " + ie.getMessage());
                        }
                    }
                    System.out.println("Username = " + ((LoginRequest) request).getUsername());
                }

            } catch (NullPointerException ne){
                System.out.println("Null aaya h: "+ ne.getMessage());
            }


        }

    }
}
