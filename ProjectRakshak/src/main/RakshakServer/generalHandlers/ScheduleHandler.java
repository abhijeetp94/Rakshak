package generalHandlers;

import data.Schedule;
import data.TimeTable;
import mainPack.Main;

import java.util.List;

public class ScheduleHandler {
    public static List<Schedule> schedules;

    public static List<TimeTable> getTimeTable(){
        return Main.timeTableList;
    }
}
