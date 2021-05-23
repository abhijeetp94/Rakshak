package data;

import utils.PayManager;

import java.util.List;

public class Staff extends User {
    String StaffID;
    String QRCode;
    PayManager payManager;
    List<Attendance> attendances;

    public Staff(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
    }

    public Staff(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String QRCode, PayManager payManager, List<Attendance> attendances) {
        super(username, password, firstname, lastname, email, userUID);
        this.StaffID = staffID;
        this.QRCode = QRCode;
        this.payManager = payManager;
        this.attendances = attendances;
    }

    public Staff(String username, String password) {
        super(username, password);
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public PayManager getPayManager() {
        return payManager;
    }

    public void setPayManager(PayManager payManager) {
        this.payManager = payManager;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Staff findStaff(List<Staff> staffList, String staffID){    // to find a doctor in a list of doctors using only doctorID as for every doctor the doctorID would be unique
        for (Staff d:
                staffList) {
            if(d.getStaffID().equals(staffID)){
                return d;
            }
        }
        return null;
    }
}
