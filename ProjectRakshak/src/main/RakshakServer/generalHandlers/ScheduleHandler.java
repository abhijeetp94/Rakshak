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
import java.util.ArrayList;
import java.util.List;

public class ScheduleHandler {
//    public static List<Schedule> schedules;

    public static List<TimeTable> getTimeTable(){
        return Main.timeTableList;
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
        if(Main.schedules.contains(schedule)){
            return false;
        }
        Main.schedules.add(schedule);
        return true;
    }
    public static boolean approveSchedule(List<Schedule> schedules){
        int id=-1;
        for(Schedule schedule: schedules){
            schedule.setApproved(true);
            if(Main.schedules.contains(schedule)){
                id = Main.schedules.indexOf(schedule);
            }
            if(id!=-1){
                Main.schedules.set(id, schedule);
            }else {
                Main.schedules.add(schedule);
            }

        }

        return true;
    }


}
