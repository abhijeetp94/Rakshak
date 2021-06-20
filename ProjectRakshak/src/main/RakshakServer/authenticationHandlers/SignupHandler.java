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
import utils.PayManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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
        String getQuery = "SELECT _id from users where userid = ?";
        String addPatient = "INSERT INTO patients (user, details, doctor) values (?, '-', 0)";
        try {
            PreparedStatement insertStatement = Main.connection.prepareStatement(addQuery);
            insertStatement.setString(1, user.getUserUID());
            insertStatement.setString(2, user.getUsername());
            insertStatement.setString(3, user.getPassword());
            insertStatement.setString(4, user.getFirstname());
            PreparedStatement getUserStatement = Main.connection.prepareStatement(getQuery);
            insertStatement.setString(5, user.getLastname());
            getUserStatement.setString(1, user.getUserUID());
            insertStatement.setString(6, user.getEmail());
            insertStatement.setString(7, user.getPhone());
            insertStatement.setString(8, user.getDateJoined().format(Main.formatter));
            insertStatement.execute();
            PreparedStatement patientInsertStatement = Main.connection.prepareStatement(addPatient);
            ResultSet userResult = getUserStatement.executeQuery();
            int user_id = 0;
            if(userResult.next()){
                user_id = userResult.getInt("_id");
            }
            patientInsertStatement.setInt(1, user_id);
            patientInsertStatement.execute();

            patientInsertStatement.close();
            getUserStatement.close();
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
        String getStaffIDQuery = "SELECT _id FROM staff where staffID = ?";
        String insertPayManagerQuery = "INSERT INTO pay_managers (grade_pay, base_salary, staff, month, paid_leaves) " +
                "values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement getUserStatement = Main.connection.prepareStatement(getUserIDQuery);
            PreparedStatement getStaffStatement = Main.connection.prepareStatement(getStaffIDQuery);

            // insert in the user database first
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

            // get ID of the inserted user
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

            System.out.println(staff.isReceptionist());
            System.out.println((staff.isReceptionist()?1:0));

            staffInsertStatement.setInt(6, (staff.isReceptionist()?1:0));
            staffInsertStatement.setString(7, staff.getQRCode());
            staffInsertStatement.execute();

            // get _id of the inserted staff
            getStaffStatement.setString(1, staff.getStaffID());
            ResultSet getStaffResult = getStaffStatement.executeQuery();
            int staffID = 0;
            if(getStaffResult.next()){
                staffID = getStaffResult.getInt("_id");
            }

            // insert PayManager of the inserted staff;
            for(Map.Entry<Integer, PayManager> entry: staff.getPayManager().entrySet()){
                PreparedStatement payManagerStatement = Main.connection.prepareStatement(insertPayManagerQuery);
                payManagerStatement.setDouble(1, entry.getValue().getBaseSalary());
                payManagerStatement.setDouble(2, entry.getValue().getBaseSalary());
                payManagerStatement.setInt(3, staffID);
                payManagerStatement.setInt(4, entry.getKey());
                payManagerStatement.setInt(5, entry.getValue().getPaidLeave());
                payManagerStatement.execute();

                payManagerStatement.close();
            }

            // doctor
            if(request.getType().equals(StaffType.DOCTOR)){
                Doctor doctor = (Doctor) staff;
                found = (DataHandler.findDoctor(doctor.getDoctorID()));
                if(found)
                    return false;
                String doctorInsertQuery = "INSERT INTO doctors (staff, doctorID, speciality, degrees, experience, cabin_number, available) " +
                        "values (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement doctorInsertStatement = Main.connection.prepareStatement(doctorInsertQuery);
                doctorInsertStatement.setInt(1, staffID);
                doctorInsertStatement.setString(2, doctor.getDoctorID());
                doctorInsertStatement.setString(3, doctor.getSpeciality());
                doctorInsertStatement.setString(4, String.join(",", doctor.getDegrees()));
                doctorInsertStatement.setInt(5, doctor.getExperience());
                doctorInsertStatement.setInt(6, doctor.getCabinNumber());
                doctorInsertStatement.setInt(7, doctor.isAvailable()?1:0);
                doctorInsertStatement.execute();

                doctorInsertStatement.close();
                getStaffStatement.close();
                getUserStatement.close();
                staffInsertStatement.close();
            }
        } catch (SQLException se){
            se.printStackTrace();
            return false;
        }


        return true;
    }

}
