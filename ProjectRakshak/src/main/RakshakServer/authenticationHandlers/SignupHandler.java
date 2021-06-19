package authenticationHandlers;

import constants.StaffType;
import data.Admin;
import data.Doctor;
import data.Staff;
import data.User;
import generalHandlers.DataHandler;
import mainPack.Main;
import request.SignupRequest;
import request.StaffRegisterRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupHandler {
    SignupRequest signupRequest;

    public SignupHandler(SignupRequest signupRequest) {
        this.signupRequest = signupRequest;
    }

    public static boolean verifySignup(SignupRequest request){
        if (DataHandler.findUser(request.getUser().getUserUID(), request.getUser().getUsername())){
            return false;
        }
        User user = request.getUser();
        String addQuery = "INSERT INTO users(userid, username, password, firstname, lastname, " +
                "email, phone, joining_date) values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement insertStatement = Main.connection.prepareStatement(addQuery);
            insertStatement.setString(1, user.getUserUID());
            insertStatement.setString(2, user.getUsername());
            insertStatement.setString(3, user.getPassword());
            insertStatement.setString(4, user.getFirstname());
            insertStatement.setString(5, user.getLastname());
            insertStatement.setString(6, user.getEmail());
            insertStatement.setString(7, user.getPhone());
            insertStatement.setString(8, user.getDateJoined().format(Main.formatter));
            insertStatement.execute();
//            System.out.println(insertStatement.);
            insertStatement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return true;
    }


    public static boolean verifyStaffRegistration(StaffRegisterRequest request){
        boolean found;

        // staff
        Staff staff = request.getStaff();
        found = (DataHandler.findStaff(request.getStaff().getStaffID()));
        boolean foundUser = (DataHandler.findUser(request.getStaff().getUserUID(), request.getStaff().getUsername()));
        if(found)
            return false;
        String userInsertQuery = "INSERT INTO users (userid, username, password, firstname, lastname, email, phone, joining_date) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        String getUserIDQuery = "SELECT _id FROM users where userid = ?";
        String staffInsertQuery = "INSERT INTO staff (user, staffID, title, isAdmin, isDoctor, isReceptionist, qr_code) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        String getStaff = "SELECT _id FROM users where staffID = ?";
        String insertPaymanagerQuery

        try {
            PreparedStatement getUserStatement = Main.connection.prepareStatement(getUserIDQuery);
            if(!foundUser){
                PreparedStatement userInsertStatement = Main.connection.prepareStatement(userInsertQuery);
                userInsertStatement.setString(1, staff.getUserUID());
                userInsertStatement.setString(2, staff.getUsername());
                userInsertStatement.setString(3, staff.getPassword());
                userInsertStatement.setString(4, staff.getFirstname());
                userInsertStatement.setString(5, staff.getLastname());
                userInsertStatement.setString(6, staff.getEmail());
                userInsertStatement.setString(7, staff.getPhone());
                userInsertStatement.setString(8, staff.getDateJoined().format(Main.formatter));
                userInsertStatement.execute();
                userInsertStatement.close();
            }

            getUserStatement.setString(1, staff.getUserUID());
            ResultSet getUserResult = getUserStatement.executeQuery();
            int userID = 0;
            if(getUserResult.next()){
                userID = getUserResult.getInt("_id");
            }
            PreparedStatement staffInsertStatement = Main.connection.prepareStatement(staffInsertQuery);
            staffInsertStatement.setInt(1, userID);
            staffInsertStatement.setString(2, staff.getStaffID());
            staffInsertStatement.setString(3, staff.getTitle());
            staffInsertStatement.setInt(4, (staff.isAdmin()?1:0));
            staffInsertStatement.setInt(5, (staff.isDoctor()?1:0));
            staffInsertStatement.setInt(6, (staff.isReceptionist()?1:0));
            staffInsertStatement.setString(7, staff.getQRCode());
            staffInsertStatement.execute();

        } catch (SQLException se){
            se.printStackTrace();
        }


        // doctor
        if(request.getType().equals(StaffType.DOCTOR)){
            Doctor doctor = (Doctor) staff;
            found = (DataHandler.findDoctor(doctor.getDoctorID()));
            if(found)
                return false;
            Main.doctors.add((Doctor) request.getStaff());
        }
        return true;
    }

}
