package data;

import javafx.util.Pair;
import utils.PayManager;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Doctor extends Staff implements Serializable {
    protected String doctorID;
    protected String speciality;
    protected String[] degrees;
    protected Integer experience;
    protected Integer cabinNumber;
    private List<Pair<LocalTime, LocalTime>> shifts;    // in a separate database mark at the doctor with foreign key
    boolean available = true;              // to check if the doctor is available for today or not (Maintain by admins)

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
        this.doctorID = userUID;
        isDoctor = true;
        title = "Doctor";
        speciality = "Doctor";
        shifts = new ArrayList<>();

    }

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String title, PayManager payManager, List<Attendance> attendances) {
        super(username, password, firstname, lastname, email, userUID, staffID, title, payManager, attendances, false, false);
        isDoctor = true;
        shifts = new ArrayList<>();
    }

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, PayManager payManager, List<Attendance> attendances, String speciality, String[] degrees, Integer experience, Integer cabinNumber, boolean available) {
        super(username, password, firstname, lastname, email, userUID, staffID, "Doctor", payManager, attendances, false, false);
        this.doctorID = staffID;
        this.speciality = speciality;
        this.degrees = degrees;
        this.experience = experience;
        this.cabinNumber = cabinNumber;
        this.available = available;
        isDoctor = true;
        shifts = new ArrayList<>();
    }

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID, String phone, String staffID, String QRCode, String title, PayManager payManager, List<Attendance> attendances, boolean isReceptionist, String doctorID, String speciality, String[] degrees, Integer experience, Integer cabinNumber) {
        super(username, password, firstname, lastname, email, userUID, phone, staffID, QRCode, title, payManager, attendances, isReceptionist);
        this.doctorID = doctorID;
        this.speciality = speciality;
        this.degrees = degrees;
        this.experience = experience;
        this.cabinNumber = cabinNumber;
        isDoctor = true;
        shifts = new ArrayList<>();

    }

    public Doctor(Staff staff, String doctorID, String speciality, String[] degrees, Integer experience, Integer cabinNumber, List<Pair<LocalTime, LocalTime>> shifts, boolean available) {
        super(staff.getUsername(), staff.getPassword(), staff.getFirstname(), staff.getLastname(), staff.getEmail(), staff.getUserUID(), staff.getPhone(), staff.getDateJoined(), staff.getStaffID(), staff.getQRCode(), staff.getTitle(), staff.getPayManager(), staff.getAttendances(), staff.isAdmin(), staff.isDoctor(), staff.isReceptionist());
        this.doctorID = doctorID;
        this.speciality = speciality;
        this.degrees = degrees;
        this.experience = experience;
        this.cabinNumber = cabinNumber;
        this.shifts = shifts;
        this.available = available;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String specialities) {
        this.speciality = specialities;
    }

    public String[] getDegrees() {
        return degrees;
    }

    public void setDegrees(String[] degrees) {
        this.degrees = degrees;
    }

    public Integer getCabinNumber() {
        return cabinNumber;
    }

    public void setCabinNumber(Integer cabinNumber) {
        this.cabinNumber = cabinNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }


    public static Doctor findDoctor(List<Doctor> doctors, String doctorID){    // to find a doctor in a list of doctors using only doctorID as for every doctor the doctorID would be unique
        for (Doctor d:
                doctors) {
            if(d.getDoctorID().equals(doctorID)){
                return d;
            }
        }
        return null;
    }

    public List<Pair<LocalTime, LocalTime>> getShifts() {
        return shifts;
    }

    public void setShifts(List<Pair<LocalTime, LocalTime>> shifts) {
        this.shifts = shifts;
    }

    public void addShift(LocalTime start, LocalTime end){
        shifts.add(new Pair<>(start, end));
        shifts.sort(new PairCompare());
    }
    public void removeShift(LocalTime start, LocalTime end){
        shifts.remove(new Pair<>(start, end));
    }

    public static class PairCompare implements Comparator<Pair<LocalTime, LocalTime>>{
        @Override
        public int compare(Pair<LocalTime, LocalTime> o1, Pair<LocalTime, LocalTime> o2) {
            return (o1.getKey().compareTo(o2.getKey())==0)?(o1.getValue().compareTo(o2.getValue())):(o1.getKey().compareTo(o2.getKey()));
        }
    }

    @Override
    public String toString() {
        return "Dr. " + firstname + " " + lastname + " (" + speciality + ") ";
    }
}
