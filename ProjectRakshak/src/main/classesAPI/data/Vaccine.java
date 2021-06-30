package data;

import java.io.Serializable;

public class Vaccine implements Serializable {
    private String name;
    private String type;
    private boolean available;
    private String vaccineID;
    private int dose;
    private int quantity;


    public Vaccine(String name, String type, int quantity, boolean available, String vaccineID, int dose) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.available = available;
        this.vaccineID = vaccineID;
        this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

}
