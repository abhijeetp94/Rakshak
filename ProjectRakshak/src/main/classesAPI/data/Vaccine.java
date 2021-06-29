package data;

import java.io.Serializable;

public class Vaccine implements Serializable {
    String name;
    String type;
    String available;
    String vaccineID;
    String dose;

    public Vaccine(String name, String type, String available, String vaccineID, String dose) {
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
