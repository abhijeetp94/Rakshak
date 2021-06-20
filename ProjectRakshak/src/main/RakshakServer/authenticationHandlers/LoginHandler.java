package authenticationHandlers;

import data.*;
import javafx.util.Pair;
import mainPack.Main;
import request.AdminLoginRequest;
import request.DoctorLoginRequest;
import request.LoginRequest;
import request.StaffLoginRequest;
import utils.Bonus;
import utils.PayManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static generalHandlers.DataHandler.retrieveDoctor;
import static generalHandlers.DataHandler.retrieveStaff;

public class LoginHandler {
    LoginRequest request;

    public LoginHandler(LoginRequest request) {
        this.request = request;
    }
    public static User verifyUser(LoginRequest request){

        String query = "SELECT * FROM users where userid = '" + request.getUsername() + "' OR users.username = '" + request.getUsername() + "'";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                if(result.getString("password").equals(request.getPassword())){

                    return new User(result.getString("username"),
                            result.getString("password"),
                            result.getString("firstname"),
                            result.getString("lastname"),
                            result.getString("email"),
                            result.getString("userid"),
                            result.getString("phone"),
                            LocalDate.parse(result.getString("joining_date"), Main.formatter));
                }
            }

            statement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    public static Staff verifyStaff(StaffLoginRequest request){
        Staff staff = retrieveStaff(request.getStaffID());
        if(staff == null)
            return null;

        if(staff.getPassword().equals(request.getPassword())){
            return staff;
        }

        return null;
    }



    public static Staff verifyDoctor(DoctorLoginRequest request){
        Doctor doctor = retrieveDoctor(request.getDoctorID());
        if(doctor != null){
            if(doctor.getPassword().equals(request.getPassword()))
                return doctor;
        }
        return null;
    }

    public static Admin verifyAdmin(AdminLoginRequest request){
        Staff staff = verifyStaff(new StaffLoginRequest(request.getStaffID(), request.getPassword()));
        if(staff != null){
            if(staff.isAdmin())
                return new Admin(staff);
        }
        return null;
    }

}
