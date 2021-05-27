package data;

import utils.PayManager;

import java.io.Serializable;
import java.util.List;

public class Doctor extends Staff implements Serializable {
    protected String doctorID;
    protected String[] specialities;
    protected String[] degrees;
    protected Integer experience;
    protected Integer cabinNumber;

    boolean available = true;              // to check if the doctor is available for today or not (Maintain by admins)

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);

    }

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String QRCode, PayManager payManager, List<Attendance> attendances) {
        super(username, password, firstname, lastname, email, userUID, staffID, "Doctor", QRCode, payManager, attendances, false);
        isDoctor = true;
    }

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String QRCode, PayManager payManager, List<Attendance> attendances, String doctorID, String[] specialities, String[] degrees, Integer experience, Integer cabinNumber, boolean available) {
        super(username, password, firstname, lastname, email, userUID, staffID, "Doctor", QRCode, payManager, attendances, false);
        this.doctorID = doctorID;
        this.specialities = specialities;
        this.degrees = degrees;
        this.experience = experience;
        this.cabinNumber = cabinNumber;
        this.available = available;
        isDoctor = true;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String[] getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String[] specialities) {
        this.specialities = specialities;
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
}
