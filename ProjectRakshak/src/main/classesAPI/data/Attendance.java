package data;

import java.time.LocalDate;

public class Attendance {
    private LocalDate date;
    private Staff staff;
    private boolean present;

    public Attendance(LocalDate date, Staff staff, boolean present) {
        this.date = date;
        this.staff = staff;
        this.present = present;
    }

    public Attendance(LocalDate date, boolean present) {
        this.date = date;
        this.present = present;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
