package generalHandlers;

import authenticationHandlers.LoginHandler;
import constants.BedType;
import data.*;
import javafx.util.Pair;
import mainPack.Main;
import request.BookBedRequest;
import request.LoginRequest;
import utils.Bonus;
import utils.PayManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DataHandler {
    public static List<Doctor> getDoctors(){
        List<Doctor> doctors = new ArrayList<>();
        String doctorsQuery = "SELECT doctorID from doctors";
        try {
            PreparedStatement doctorsStatement = Main.connection.prepareStatement(doctorsQuery);
            ResultSet result = doctorsStatement.executeQuery();
            while (result.next()){
                Doctor doctor = retrieveDoctor(result.getString("doctorID"));
                doctors.add(doctor);
            }

        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }

        return doctors;
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
        String doctorsQuery = "SELECT * from doctors where doctorID = ?";
        try {
            PreparedStatement doctorsStatement = Main.connection.prepareStatement(doctorsQuery);
            doctorsStatement.setString(1, doctorID);
            ResultSet result = doctorsStatement.executeQuery();
            if (result.next()){
                return retrieveDoctor(result.getString("doctorID"));
            }

        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        return null;
    }


    public static Staff getStaffMember(String staffID){
        String staffQuery = "SELECT * from staff where staffID = ?";
        try {
            PreparedStatement staffStatement = Main.connection.prepareStatement(staffQuery);
            staffStatement.setString(1, staffID);
            ResultSet result = staffStatement.executeQuery();
            if (result.next()){
                return retrieveStaff(result.getString("doctorID"));
            }

        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        return null;
    }



    public static User getUser(String userID){
        String userQuery = "SELECT * from users where userid = ?";
        try {
            PreparedStatement staffStatement = Main.connection.prepareStatement(userQuery);
            staffStatement.setString(1, userID);
            ResultSet result = staffStatement.executeQuery();
            if (result.next()){
                System.out.println("In Data Handler" + result.getString("userid"));
                return retrieveUser(result.getString("userid"), result.getString("username"));
            }

        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean updateStaff(Staff staff){
        String getStaffQuery = "SELECT * FROM staff where staffID = ?";
        String getUserQuery = "SELECT * FROM users where _id = ?";
        String updateQuery = "UPDATE users SET firstname = ?, lastname = ?, email = ?, phone = ? WHERE _id = ?";
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getStaffQuery);
            getStatement.setString(1, staff.getStaffID());
            ResultSet staffResult = getStatement.executeQuery();
            if(staffResult.next()){
                PreparedStatement getUserStatement = Main.connection.prepareStatement(getUserQuery);
                getUserStatement.setInt(1, staffResult.getInt("user"));
                ResultSet userResult = getUserStatement.executeQuery();
                if(userResult.next()){
                    PreparedStatement updateStatement = Main.connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, staff.getFirstname());
                    updateStatement.setString(2, staff.getLastname());
                    updateStatement.setString(3, staff.getEmail());
                    updateStatement.setString(4, staff.getPhone());
                    updateStatement.setInt(5, userResult.getInt("_id"));
                    updateStatement.execute();
                    return true;
                }else {
                    return false;
                }

            } else{
                return false;
            }

        } catch (SQLException se){
            se.printStackTrace();
            return false;
        }
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


            // attendance query
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
                        LocalDate.parse(result2.getString("joining_date"), Main.formatter),
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
            docStatement.close();
            staffStatement.close();
            timeTableStatement.close();

        } catch (SQLException se){
            se.printStackTrace();
        }

        return null;
    }

    public static User retrieveUser(String userID, String username){

        String query = "SELECT * FROM users where userid = '" + userID + "' OR users.username = '" + username + "'";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return new User(result.getString("username"),
                        result.getString("password"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("email"),
                        result.getString("userid"),
                        result.getString("phone"),
                        LocalDate.parse(result.getString("joining_date"), Main.formatter));
            }
            statement.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    public static Patient retrievePatient(String userID){
        User user = retrieveUser(userID, userID);
        if(user == null){
            return null;
        }
        String getUser = "SELECT _id FROM users where userid = ?";
        String patientQuery = "SELECT * FROM patients where user = ?";
        try {
            PreparedStatement getUserStatement = Main.connection.prepareStatement(getUser);
            getUserStatement.setString(1, userID);
            ResultSet userResult = getUserStatement.executeQuery();
            if(!userResult.next()){
                return null;
            }
            int user_id = userResult.getInt("_id");
            PreparedStatement patientStatement = Main.connection.prepareStatement(patientQuery);
            patientStatement.setInt(1, user_id);
            ResultSet patientResult = patientStatement.executeQuery();
            if(!patientResult.next()){
                return null;
            }
            int doctor_id = patientResult.getInt("doctor");
            String doctorQuery = "SELECT doctorID from doctors where _id = ?";
            PreparedStatement doctorStatement = Main.connection.prepareStatement(doctorQuery);
            doctorStatement.setInt(1, doctor_id);
            ResultSet rs = doctorStatement.executeQuery();
            Doctor doctor = null;
            if(rs.next()){
                doctor = retrieveDoctor(rs.getString("doctorID"));
            }

            Patient patient = new Patient(user, patientResult.getString("details"), doctor);

            getUserStatement.close();
            doctorStatement.close();
            patientStatement.close();

            return patient;
        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }
    }

    public static boolean bookBed(BookBedRequest request){
        String bookBedQuery = "SELECT * from beds where bed_number = ? and cabin_number = ?";
        String updateBedQuery = "UPDATE beds" +
                " SET patient_id = ?, doctor_id = ?, occupied = ? where _id = ?";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(bookBedQuery);
            statement.setInt(1, request.getBed().getBedNumber());
            statement.setInt(2, request.getBed().getCabinNumber());
            ResultSet result = statement.executeQuery();
            int bed_id = result.getInt("_id");
            PreparedStatement updateStatement = Main.connection.prepareStatement(updateBedQuery);
            updateStatement.setString(1, request.getBed().getPatient().getUserUID());
            if(request.getBed().getPrescribingDoctor() != null)
                updateStatement.setString(2, request.getBed().getPrescribingDoctor().getDoctorID());
            else
                updateStatement.setString(2, "null");
            updateStatement.setInt(3, 1);
            updateStatement.setInt(4, bed_id);
            updateStatement.execute();

            statement.close();
            updateStatement.close();
            return true;
        } catch (SQLException se){
            se.printStackTrace();
            return false;
        }
    }

    public static List<Bed> getBeds(){
        String bedQuery = "SELECT * FROM beds";
        List<Bed> beds = new ArrayList<>();
        try {
            PreparedStatement bedStatement = Main.connection.prepareStatement(bedQuery);
            ResultSet result = bedStatement.executeQuery();
            while (result.next()){
                int bedNumber = result.getInt("bed_number");
                int cabinNumber = result.getInt("cabin_number");
                boolean occupied = (result.getInt("occupied") != 0);
                String patient_id = result.getString("patient_id");
                String doctor_id = result.getString("doctor_id");
                String type = result.getString("type");
                String getUid = "SELECT * from users where userid = " + patient_id;
                String getDid = "SELECT * from doctors where doctorID = " + doctor_id;
                PreparedStatement getUIDStatement = Main.connection.prepareStatement(getUid);
                PreparedStatement getDIDStatement = Main.connection.prepareStatement(getDid);
                ResultSet rsUid = getUIDStatement.executeQuery();
                ResultSet rsDid = getDIDStatement.executeQuery();
                User user = null;
                Doctor doctor = null;
                if(rsUid.next()){
                    user = retrieveUser(rsUid.getString("userid"), rsUid.getString("userid"));
                }

                if(rsDid.next()){
                    doctor = retrieveDoctor(rsDid.getString("doctorID"));
                }

                Bed bed = new Bed(bedNumber,cabinNumber, BedType.valueOf(type),user,doctor,occupied);
//                System.out.println(bed.getType());
                beds.add(bed);

                getDIDStatement.close();
                getUIDStatement.close();
            }
            bedStatement.close();
        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        return beds;
    }

    public static List<Staff> getStaff(){
        String staffQuery = "SELECT * from staff";
        List<Staff> staff = new ArrayList<>();
        try {
            PreparedStatement staffStatement = Main.connection.prepareStatement(staffQuery);
            ResultSet result = staffStatement.executeQuery();
            while (result.next()){
                staff.add(retrieveStaff(result.getString("staffID")));
            }

        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        return staff;
    }

    public static Staff getStaff(String staffID){
        String staffQuery = "SELECT * from staff where staffID = ?";
        try {
            PreparedStatement staffStatement = Main.connection.prepareStatement(staffQuery);
            staffStatement.setString(1, staffID);
            ResultSet result = staffStatement.executeQuery();
            if (result.next()){
                return retrieveStaff(result.getString("staffID"));
            }

        } catch (SQLException se){
            se.printStackTrace();
            return null;
        }
        return null;
    }

}
