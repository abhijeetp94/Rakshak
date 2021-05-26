package data;

import utils.PayManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Staff extends User implements Serializable {
    String staffID;
    String QRCode;
    PayManager payManager;
    List<Attendance> attendances;
    boolean isAdmin = false;
    boolean isDoctor = false;

    public Staff(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
        this.staffID = username;
//        this.QRCode = ""
        this.payManager = new PayManager(0, 0, 0, new ArrayList<>());
        this.attendances = new ArrayList<>();
    }

    public Staff(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String QRCode, PayManager payManager, List<Attendance> attendances, boolean isAdmin) {
        super(username, password, firstname, lastname, email, userUID);
        this.staffID = staffID;
        this.QRCode = QRCode;
        this.payManager = payManager;
        this.attendances = attendances;
        this.isAdmin = true;
    }

    public Staff(String username, String password) {
        super(username, password);
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public static Staff findStaff(List<Staff> staffList, String staffID){    // to find a staff member in a list of staffs using only staffID as for every staff member the staffID would be unique
        for (Staff d:
                staffList) {
            if(d.getStaffID().equals(staffID)){
                return d;
            }
        }
        return null;
    }
}
