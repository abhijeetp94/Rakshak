package mainPack;

import data.Admin;
import data.Doctor;
import data.Staff;
import data.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ServerSocket serverSocket;

    // ========================================
    public static List<User> users;
    public static List<Staff> staff;
    public static List<Doctor> doctors;
    public static Admin admin;
    // ========================================

    public static void main(String[] args) {

        //for testing
        // ===================================================================================================================================
        users = new ArrayList<>();
        users.add(new User("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211"));
        users.add(new User("AbhijeetP", "12345678", "Abhijeet", "P", "abijetp94@gmail.com", "45111"));
        users.add(new User("Abhij", "12345678", "Abhi", "Pandey", "abieetp94@gmail.com", "44211"));

        staff = new ArrayList<>();
        staff.add(new Staff("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211"));
        staff.add(new Staff("AbhijeetP", "12345678", "Abhijeet", "P", "abijetp94@gmail.com", "45111"));
        staff.add(new Staff("Abhij", "12345678", "Abhi", "Pandey", "abieetp94@gmail.com", "44211"));
//        admin = new Admin("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211");
        staff.get(0).setAdmin(true);

        doctors = new ArrayList<>();
        doctors.add(new Doctor("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211"));
        doctors.add(new Doctor("AbhijeetP", "12345678", "Abhijeet", "P", "abijetp94@gmail.com", "45111"));
        doctors.add(new Doctor("Abhij", "12345678", "Abhi", "Pandey", "abieetp94@gmail.com", "44211"));
        // =====================================================================================================================================


        try{
            serverSocket = new ServerSocket(8800);

        } catch (IOException ie){
            ie.printStackTrace();
        }
        while (true) {
            try {

                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected");
                Thread t = new Thread(new ClientHandler(socket));
                t.start();

        } catch(IOException ie){
            ie.printStackTrace();
        }
    }
    }
}
