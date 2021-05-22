package data;

import java.io.Serializable;
import java.util.List;

public class Doctor extends User implements Serializable {
    String doctorID;
    String[] specialities;
    String[] degrees;
    Integer cabinNumber;

    boolean available = true;

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
    }


    public Doctor(String username, String password) {
        super(username, password);
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

    public static Doctor findDoctor(List<Doctor> doctors, String doctorID){
        for (Doctor d:
                doctors) {
            if(d.getDoctorID().equals(doctorID)){
                return d;
            }
        }
        return null;
    }
}
