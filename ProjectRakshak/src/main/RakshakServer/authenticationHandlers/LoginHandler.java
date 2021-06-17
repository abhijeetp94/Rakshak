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

    // get staff from database
    public static Staff retrieveStaff(String StaffID){

        String staffQuery = "SELECT * FROM staff where staffID = '" + StaffID + "'";

        try {
            PreparedStatement statement = Main.connection.prepareStatement(staffQuery);
            ResultSet result = statement.executeQuery();
            if(!result.next()){
                return null;
            }

            String userQuery = "SELECT * FROM users where _id = " + result.getInt("user");
            String payQuery = "SELECT * FROM pay_managers where staff = " + result.getInt("_id");
            PreparedStatement statement2 = Main.connection.prepareStatement(userQuery);  // for user data
            ResultSet result2 = statement2.executeQuery();
            PreparedStatement statement3 = Main.connection.prepareStatement(payQuery); // for pay manager data
            ResultSet result3 = statement3.executeQuery();



            Map<Integer, PayManager> payMap = new TreeMap<>();
            // fill pay Manager data;
            while (result3.next()){
                PayManager payManager = new PayManager(result3.getDouble("grade_pay"),
                        result3.getDouble("base_salary"), result3.getInt("paid_leaves"));
                ArrayList<Bonus> bonuses = new ArrayList<>();
                String bonusQuery = "SELECT * FROM bonuses where pay_manager = " + result3.getInt("_id");
                PreparedStatement statement4 = Main.connection.prepareStatement(bonusQuery);
                ResultSet result4 = statement4.executeQuery();

                while (result4.next()){
                    Bonus bonus = new Bonus(result4.getString("title"), result4.getDouble("amount"));
                    bonuses.add(bonus);
                }
                payManager.setBonus(bonuses);
                payMap.put(result3.getInt("month"), payManager);
            }


            // attendance queries
            String attendQuery = "SELECT * FROM attendance where staff = " + result.getInt("_id");
            PreparedStatement attendStatement = Main.connection.prepareStatement(attendQuery);
            ResultSet attendResult = attendStatement.executeQuery();
            ArrayList<Attendance> attendances = new ArrayList<>();
            while (attendResult.next()){
                Attendance attendance = new Attendance(LocalDate.parse(attendResult.getString("date"), Main.formatter), (attendResult.getInt("isPresent")!=0));
                attendances.add(attendance);
            }

            if(result2.next()){

                return new Staff(result2.getString("username"),
                        result2.getString("password"),
                        result2.getString("firstname"),
                        result2.getString("lastname"),
                        result2.getString("email"),
                        result2.getString("userid"),
                        result2.getString("phone"),
                        LocalDate.parse(result.getString("joining_date"), Main.formatter),
                        result.getString("staffID"),
                        result.getString("qr_code"),
                        result.getString("title"),
                        payMap,
                        attendances,
                        result.getInt("isAdmin")!=0,
                        result.getInt("isDoctor")!=0,
                        result.getInt("isReceptionist")!=0);

            }

            attendStatement.close();
            statement3.close();
            statement2.close();
            statement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    public static Doctor retrieveDoctor(String doctorID){
        String docQuery = "SELECT * FROM doctors WHERE doctorID = '" + doctorID + "'";

        try {
            PreparedStatement docStatement = Main.connection.prepareStatement(docQuery);
            ResultSet docResult = docStatement.executeQuery();
            if(!docResult.next())
                return null;

            int staff_id = docResult.getInt("staff");
            String staffQuery = "SELECT * FROM staff WHERE _id = " + staff_id;
            PreparedStatement staffStatement = Main.connection.prepareStatement(staffQuery);
            ResultSet staffResult = staffStatement.executeQuery();
            if(!staffResult.next()){
                return null;
            }
            String staffID = staffResult.getString("staffID");
            Staff staff = retrieveStaff(staffID);
            String timeTableQuery = "SELECT * FROM time_table WHERE doctor = " + docResult.getInt("_id") + " ORDER BY shift ";
            PreparedStatement timeTableStatement = Main.connection.prepareStatement(timeTableQuery);
            ResultSet timeTableResult = timeTableStatement.executeQuery();
            List<Pair<LocalTime, LocalTime>> shifts = new ArrayList<>();
            while (timeTableResult.next()){
                Pair<LocalTime, LocalTime> current = new Pair<>(LocalTime.parse(timeTableResult.getString("start_time"), Main.timeFormatter),
                        LocalTime.parse(timeTableResult.getString("end_time"), Main.timeFormatter));
                shifts.add(current);
            }
            if(staff != null){

                return new Doctor(
                        staff,
                        docResult.getString("doctorID"),
                        docResult.getString("speciality"),
                        docResult.getString("degrees").split(","),
                        docResult.getInt("experience"),
                        docResult.getInt("cabin_number"),
                        shifts,
                        (docResult.getInt("available")!=0)
                );
            }


        } catch (SQLException se){
            se.printStackTrace();
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
