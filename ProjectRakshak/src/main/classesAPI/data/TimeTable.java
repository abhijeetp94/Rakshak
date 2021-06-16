package data;

import javafx.util.Pair;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeTable implements Serializable {
    private Doctor doctor;
    private LocalDate date = LocalDate.now();
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
    private String doctorName;
    private boolean available;
    private String fieldName;

    public TimeTable(Doctor doctor, LocalDate date, LocalTime shiftStartTime, LocalTime shiftEndTime) {
        this.doctor = doctor;
        this.date = date;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
        doctorName = doctor.getFullName();
        available = doctor.isAvailable();
        fieldName = doctor.getTitle();
    }

    public TimeTable(Doctor doctor, LocalTime shiftStartTime, LocalTime shiftEndTime) {
        this.doctor = doctor;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
        doctor.addShift(shiftStartTime, shiftEndTime);
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
