package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeTable implements Serializable {
    private Doctor doctor;
    private LocalDate date;
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;

    public TimeTable(Doctor doctor, LocalDate date, LocalTime shiftStartTime, LocalTime shiftEndTime) {
        this.doctor = doctor;
        this.date = date;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(LocalTime shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    public LocalTime getShiftEndTime() {
        return shiftEndTime;
    }

    public void setShiftEndTime(LocalTime shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }
}
