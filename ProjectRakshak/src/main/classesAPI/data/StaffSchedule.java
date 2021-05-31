package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class StaffSchedule implements Serializable {
    String title;
    String description;
    LocalDate date;
    LocalTime time;

}
