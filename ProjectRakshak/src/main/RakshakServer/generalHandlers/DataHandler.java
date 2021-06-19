package generalHandlers;

import data.Bed;
import data.Doctor;
import data.Staff;
import data.User;
import mainPack.Main;
import request.BookBedRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataHandler {
    public static List<Doctor> getDoctors(){
        return Main.doctors;
    }

    public static boolean findUser(String userID, String username){
        String searchQuery = "Select * from users where userid = ? or username = ?";
        try {
            PreparedStatement searchStatement = Main.connection.prepareStatement(searchQuery);
            searchStatement.setString(1, userID);
            searchStatement.setString(2, username);
            ResultSet result = searchStatement.executeQuery();
            if (result.next()){
                return true;
            }

            searchStatement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }
    public static boolean findStaff(String staffID){
        String searchQuery = "Select * from staff where staffID = ?";
        try {
            PreparedStatement searchStatement = Main.connection.prepareStatement(searchQuery);
            searchStatement.setString(1, staffID);
            ResultSet result = searchStatement.executeQuery();
            if (result.next()){
                return true;
            }

            searchStatement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }
    public static boolean findDoctor(String doctorID){
        String searchQuery = "Select * from doctors where doctorID = ?";
        try {
            PreparedStatement searchStatement = Main.connection.prepareStatement(searchQuery);
            searchStatement.setString(1, doctorID);
            ResultSet result = searchStatement.executeQuery();
            if (result.next()){
                return true;
            }

            searchStatement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }
    public static boolean findAdmin(String staffID){
        String searchQuery = "Select * from staffs where staffID = ?";
        try {
            PreparedStatement searchStatement = Main.connection.prepareStatement(searchQuery);
            searchStatement.setString(1, staffID);
            ResultSet result = searchStatement.executeQuery();
            if (result.next()){
                if(result.getInt("isAdmin")!=0)
                    return true;
            }

            searchStatement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }

    public static Doctor getDoctor(String doctorID){
        Doctor doc = Doctor.findDoctor(Main.doctors, doctorID);
        return doc;
    }

    public static Staff getStaffMember(String staffID){
        Staff staff = Staff.findStaff(Main.staff, staffID);
        return staff;
    }

    public static User getUser(String userID){
        User user = User.findUser(Main.users, userID);
        return user;
    }

    public static boolean updateStaff(Staff staff){
        int id = -1;
        for (Staff s:
              Main.staff) {
            if(s.getStaffID().equals(staff.getStaffID())){
                id = Main.staff.indexOf(s);
            }
        }
        if(id == -1){
            return false;
        }
        Main.staff.set(id, staff);
        return true;
    }

    public static boolean bookBed(BookBedRequest request){
        return true;
    }

    public static List<Bed> getBeds(){
        return Main.beds;
    }

    public static List<Staff> getStaff(){
        return Main.staff;
    }


}
