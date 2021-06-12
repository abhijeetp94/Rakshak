package data;

import constants.BedType;

import java.io.Serializable;

public class Bed implements Serializable {
    private Integer bedNumber;
    private Integer cabinNumber;
    private BedType type;
    private User patient;
    private Doctor prescribingDoctor; // doctor who prescribed the bed
    private boolean occupied;

    public Bed(int bedNumber, int cabinNumber, BedType type) {
        this.bedNumber = bedNumber;
        this.cabinNumber = cabinNumber;
        this.type = type;
        this.occupied = false;
        patient = null;
        prescribingDoctor = null;
    }

    public Bed(int bedNumber, int cabinNumber, BedType type, User patient, Doctor prescribingDoctor) {
        this.bedNumber = bedNumber;
        this.cabinNumber = cabinNumber;
        this.type = type;
        this.patient = patient;
        this.prescribingDoctor = prescribingDoctor;
        this.occupied = false;
    }

    public Bed(int bedNumber, int cabinNumber, BedType type, User patient, Doctor prescribingDoctor, boolean occupied) {
        this.bedNumber = bedNumber;
        this.cabinNumber = cabinNumber;
        this.type = type;
        this.patient = patient;
        this.prescribingDoctor = prescribingDoctor;
        this.occupied = occupied;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Doctor getPrescribingDoctor() {
        return prescribingDoctor;
    }

    public void setPrescribingDoctor(Doctor prescribingDoctor) {
        this.prescribingDoctor = prescribingDoctor;
    }

    public Integer getCabinNumber() {
        return cabinNumber;
    }

    public void setCabinNumber(int cabinNumber) {
        this.cabinNumber = cabinNumber;
    }

    public BedType getType() {
        return type;
    }

    public void setType(BedType type) {
        this.type = type;
    }

    public Integer getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
