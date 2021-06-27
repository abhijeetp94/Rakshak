package generalHandlers;

import data.Doctor;
import data.Schedule;
import data.TimeTable;
import data.User;
import mainPack.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleHandler {
//    public static List<Schedule> schedules;

    public static List<TimeTable> getTimeTable(){
        List<TimeTable> timeTables = new ArrayList<>();
        String getQuery = "SELECT * FROM time_table";
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getQuery);
            ResultSet result = getStatement.executeQuery();
            while (result.next()){
                int doctor_id = result.getInt("doctor");
                String doctorQuery = "SELECT * FROM doctors WHERE _id = ?";
                PreparedStatement doctorStatement = Main.connection.prepareStatement(doctorQuery);
                doctorStatement.setInt(1, doctor_id);
                ResultSet doctorResult = doctorStatement.executeQuery();
                Doctor doctor = null;
                if(doctorResult.next()){
                    doctor = DataHandler.retrieveDoctor(doctorResult.getString("doctorID"));
                }
                LocalTime startTime = LocalTime.parse(result.getString("start_time"), Main.timeFormatter);
                LocalTime endTime = LocalTime.parse(result.getString("end_time"), Main.timeFormatter);
                LocalDate date = LocalDate.parse(result.getString("date"), Main.formatter);
                if(doctor!=null)
                    timeTables.add(new TimeTable(doctor, date, startTime, endTime));
            }
        } catch (SQLException se){
            se.printStackTrace();
        }

        return timeTables;
    }



    public static List<Schedule> getSchedule() {
        String getScheduleQuery = "SELECT * FROM schedules WHERE schedules.date = ?";
        List<Schedule> schedules = new ArrayList<>();
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getScheduleQuery);
            getStatement.setString(1, LocalDate.now().format(Main.formatter));
            ResultSet result = getStatement.executeQuery();
            while(result.next()){
                LocalDate date = LocalDate.parse(result.getString("date"), Main.formatter);
                boolean approved = (result.getInt("approved") != 0);
                int shift = result.getInt("shift");
                int doctor_id = result.getInt("doctor");
                String doctorQuery = "SELECT * FROM doctors WHERE _id = ?";
                PreparedStatement doctorStatement = Main.connection.prepareStatement(doctorQuery);
                doctorStatement.setInt(1, doctor_id);
                String userID = result.getString("user_id");
                User user = DataHandler.retrieveUser(userID, userID);
                ResultSet doctorResult = doctorStatement.executeQuery();
                Doctor doctor = null;
                if(doctorResult.next()){
                    doctor = DataHandler.retrieveDoctor(doctorResult.getString("doctorID"));
                }
                schedules.add(new Schedule(date, shift, doctor, user, approved));
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return schedules;
    }

    public static List<Schedule> getSchedule(String doctorID) {
        String getScheduleQuery = "SELECT * FROM schedules WHERE schedules.date = ? AND doctorID = ?";
        List<Schedule> schedules = new ArrayList<>();
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getScheduleQuery);
            getStatement.setString(1, LocalDate.now().format(Main.formatter));
            getStatement.setString(2, doctorID);
            ResultSet result = getStatement.executeQuery();
            while(result.next()){
                boolean approved = (result.getInt("approved") != 0);
                LocalDate date = LocalDate.parse(result.getString("date"), Main.formatter);
                int shift = result.getInt("shift");
                int doctor_id = result.getInt("doctor");
                String userID = result.getString("user_id");
                String doctorQuery = "SELECT * FROM doctors WHERE _id = ?";
                PreparedStatement doctorStatement = Main.connection.prepareStatement(doctorQuery);
                doctorStatement.setInt(1, doctor_id);
                ResultSet doctorResult = doctorStatement.executeQuery();
                User user = DataHandler.retrieveUser(userID, userID);
                Doctor doctor = null;
                if(doctorResult.next()){
                    doctor = DataHandler.retrieveDoctor(doctorResult.getString("doctorID"));
                }
                schedules.add(new Schedule(date, shift, doctor, user, approved));
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return schedules;
    }

    public static boolean addSchedule(Schedule schedule){
        String insertQuery = "INSERT INTO schedules (date, shift, approved, user_id, doctor) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement insertStatement = Main.connection.prepareStatement(insertQuery);
            insertStatement.setString(1, schedule.getTheDate().format(Main.formatter));
            insertStatement.setInt(2, schedule.getShift());
            insertStatement.setInt(3, (schedule.getApproved()?1:0));
            insertStatement.setString(4, schedule.getUser().getUserUID());
            String doctorQuery = "SELECT * FROM doctors WHERE doctorID = ?";
            PreparedStatement doctorStatement = Main.connection.prepareStatement(doctorQuery);
            doctorStatement.setString(1, schedule.getDoctor().getDoctorID());
            ResultSet doctorResult = doctorStatement.executeQuery();
            int doctor_id = 0;
            if(doctorResult.next()){
                doctor_id = doctorResult.getInt("_id");
            }
            insertStatement.setInt(5, doctor_id);

            insertStatement.execute();

        } catch (SQLException se){
            se.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean approveSchedule(List<Schedule> schedules){
        String getQuery = "";
    }


}
