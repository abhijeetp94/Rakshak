package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Schedule implements Serializable {
    private LocalDate theDate;
    private Integer shift;
    private Doctor doctor;
    private User user;
    private Boolean approved = false;

    public Schedule(LocalDate theDate, Integer shift, Doctor doctor, User user) {
        this.theDate = theDate;
        this.shift = shift;
        this.doctor = doctor;
        this.user = user;
    }

    public Schedule(LocalDate theDate, Integer shift, Doctor doctor, User user, Boolean approved) {
        this.theDate = theDate;
        this.shift = shift;
        this.doctor = doctor;
        this.user = user;
        this.approved = approved;
    }

    public LocalDate getTheDate() {
        return theDate;
    }

    public void setTheDate(LocalDate theDate) {
        this.theDate = theDate;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
