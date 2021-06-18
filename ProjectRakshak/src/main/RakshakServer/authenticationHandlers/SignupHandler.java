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
        if (DataHandler.findUser(request.getUser())){
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
        boolean found=false;



        if (request.getType().equals(StaffType.STAFF)){
            found = (Staff.findStaff(Main.staff, request.getStaff().getStaffID())!=null);
            if(found)
                return false;
            Main.staff.add(request.getStaff());

        }



        else if (request.getType().equals(StaffType.ADMIN)){
            found = (Admin.findAdmin(Main.admins, request.getStaff().getStaffID())!=null);
            if(found)
                return false;
            Main.admins.add((Admin) request.getStaff());

        }



        else if(request.getType().equals(StaffType.DOCTOR)){
            found = (Staff.findStaff(Main.staff, request.getStaff().getStaffID())!=null);
            if(found)
                return false;
            Main.doctors.add((Doctor) request.getStaff());
        }
        return true;
    }

}
