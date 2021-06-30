package data;

import java.io.Serializable;
import java.time.LocalDate;

public class Vaccination implements Serializable {
    String userID;
    Vaccine vaccine;
    int dose;
    boolean vaccinated;
    LocalDate date;

    public Vaccination(String userID, Vaccine vaccine, int dose, LocalDate date) {
        this.userID = userID;
        this.vaccine = vaccine;
        this.dose = dose;
        this.date = date;
        vaccinated = false;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}
