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
}
