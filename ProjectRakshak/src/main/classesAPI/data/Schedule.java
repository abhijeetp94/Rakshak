package data;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private LocalDate theDate;
    private LocalTime theTime;
    private User appointedUser;

    public Schedule(LocalDate theDate, LocalTime theTime, User appointedUser) {
        this.theDate = theDate;
        this.theTime = theTime;
        this.appointedUser = appointedUser;
    }

    public LocalDate getTheDate() {
        return theDate;
    }

    public void setTheDate(LocalDate theDate) {
        this.theDate = theDate;
    }

    public LocalTime getTheTime() {
        return theTime;
    }

    public void setTheTime(LocalTime theTime) {
        this.theTime = theTime;
    }

    public User getAppointedUser() {
        return appointedUser;
    }

    public void setAppointedUser(User appointedUser) {
        this.appointedUser = appointedUser;
    }
}
