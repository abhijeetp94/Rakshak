package generalHandlers;

import data.Doctor;
import data.Schedule;
import data.TimeTable;
import mainPack.Main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleHandler {
//    public static List<Schedule> schedules;

    public static List<TimeTable> getTimeTable(){
        return Main.timeTableList;
    }

    public static List<Schedule> getSchedule(String doctorID) {
        List<Schedule> result = new ArrayList<>();
        for(Schedule schedule: Main.schedules){
            if(schedule.getDoctor().getDoctorID().equals(doctorID)){
                if(schedule.getTheDate().equals(LocalDate.now())){
                    result.add(schedule);
                }
            }
        }
        return result;
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
