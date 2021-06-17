package mainPack;

import constants.BedType;
import data.*;
import utils.DBConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ServerSocket serverSocket;
    public static Connection connection;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
    // ========================================
    public static List<User> users;
    public static List<Staff> staff;
    public static List<Doctor> doctors;
    public static List<Admin> admins;
    public static List<Schedule> schedules;
    public static List<TimeTable> timeTableList;
    public static List<Bed> beds;
    // ========================================

    public static void main(String[] args) {
        //for testing
        // ===================================================================================================================================
        users = new ArrayList<>();
        users.add(new User("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211"));
        users.add(new User("AbhijeetP", "12345678", "Abhijeet", "P", "abijetp94@gmail.com", "45111"));
        users.add(new User("Abhij", "12345678", "Abhi", "Pandey", "abieetp94@gmail.com", "44211"));

        staff = new ArrayList<>();
        admins = new ArrayList<>();
        staff.add(new Staff("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211"));
        staff.add(new Staff("AbhijeetP", "12345678", "Abhijeet", "P", "abijetp94@gmail.com", "45111"));
        staff.add(new Staff("Abhij", "12345678", "Abhi", "Pandey", "abieetp94@gmail.com", "44211"));
        admins.add(new Admin("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211"));
        staff.get(0).setReceptionist(true);

        doctors = new ArrayList<>();
        doctors.add(new Doctor("AbhijeetP94", "12345678", "Abhijeet", "Pandey", "abijeetp94@gmail.com", "45211"));
        doctors.add(new Doctor("AbhijeetP", "12345678", "Abhijeet", "P", "abijetp94@gmail.com", "45111"));
        doctors.add(new Doctor("Abhij", "12345678", "Abhi", "Pandey", "abieetp94@gmail.com", "44211"));

        doctors.get(0).addShift(LocalTime.of(9, 30), LocalTime.of(13, 30));
        doctors.get(0).addShift(LocalTime.of(14, 30), LocalTime.of(18, 30));

        schedules = new ArrayList<>();
        schedules.add(new Schedule(LocalDate.now(), 1, doctors.get(0), users.get(0), true));
        schedules.add(new Schedule(LocalDate.now(), 1, doctors.get(0), users.get(1), true));
        schedules.add(new Schedule(LocalDate.now(), 1, doctors.get(0), users.get(2), true));
        schedules.add(new Schedule(LocalDate.now(), 1, doctors.get(0), users.get(0)));
        schedules.add(new Schedule(LocalDate.now(), 1, doctors.get(0), users.get(1)));
        schedules.add(new Schedule(LocalDate.now(), 1, doctors.get(0), users.get(2)));
        schedules.add(new Schedule(LocalDate.now(), 1, doctors.get(0), users.get(1)));
        schedules.add(new Schedule(LocalDate.now().plusDays(1), 2, doctors.get(0), users.get(1)));

        timeTableList = new ArrayList<>();
        timeTableList.add(new TimeTable(doctors.get(0), LocalDate.now(), LocalTime.of(14, 30), LocalTime.of(18, 30)));
        timeTableList.add(new TimeTable(doctors.get(0), LocalDate.now(), LocalTime.of(9, 30), LocalTime.of(12, 30)));

        beds = new ArrayList<>();
        beds.add(new Bed(1, 31, BedType.GENERAL, users.get(2), doctors.get(0)));
        // =====================================================================================================================================

        DBConnection dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        System.out.println("Database connected: " + connection.toString());


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
