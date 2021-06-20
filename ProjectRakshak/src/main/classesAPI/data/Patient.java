package data;

import java.io.Serializable;

public class Patient extends User implements Serializable {
    private String details = null;
    private Doctor doctor = null;


    public Patient(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);

    }

    public Patient(String username, String password, String firstname, String lastname, String email, String userUID, String phone) {
        super(username, password, firstname, lastname, email, userUID, phone);
    }

    public Patient(String username, String password, String firstname, String lastname, String email, String userUID, String details, Doctor doctor) {
        super(username, password, firstname, lastname, email, userUID);
        this.details = details;
        this.doctor = doctor;
    }

    public Patient(String username, String password, String firstname, String lastname, String email, String userUID, String phone, String details, Doctor doctor) {
        super(username, password, firstname, lastname, email, userUID, phone);
        this.details = details;
        this.doctor = doctor;
    }

    public Patient(User user, String details, Doctor doctor) {
        super(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getUserUID(), user.getPhone(), user.getDateJoined());
        this.details = details;
        this.doctor = doctor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
