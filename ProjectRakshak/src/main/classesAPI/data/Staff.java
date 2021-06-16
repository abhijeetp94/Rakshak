package data;

import utils.PayManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Staff extends User implements Serializable {
    protected String staffID;
    protected String QRCode;
    protected String title;
    protected List<PayManager> payManager = new ArrayList<>();;
    protected List<Attendance> attendances;
    boolean isAdmin = false;
    boolean isDoctor = false;
    boolean isReceptionist = false;

    public Staff(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
        this.staffID = username;
//        this.QRCode = ""
//        this.payManager =
        this.payManager.add(new PayManager(0.0, 0.0, 0, new ArrayList<>()));
        this.attendances = new ArrayList<>();
        this.isAdmin = false;
        this.title = "Staff";
    }

    public Staff(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String title, PayManager payManager, List<Attendance> attendances, boolean isAdmin, boolean isReceptionist) {
        super(username, password, firstname, lastname, email, userUID);
        this.staffID = staffID;
        this.payManager.add(payManager);
        this.attendances = attendances;
        this.isAdmin = isAdmin;
        this.title = title;

    }

    public Staff(String username, String password, String firstname, String lastname, String email, String userUID, String phone, String staffID, String QRCode, String title, PayManager payManager, List<Attendance> attendances, boolean isReceptionist) {
        super(username, password, firstname, lastname, email, userUID, phone);
        this.staffID = staffID;
        this.QRCode = QRCode;
        this.title = title;
        this.payManager.add(payManager);
        this.attendances = attendances;
        this.isReceptionist = isReceptionist;
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

    public List<PayManager> getPayManager() {
        return payManager;
    }

    public void setPayManager(PayManager payManager) {
        this.payManager.add(payManager);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isReceptionist() {
        return isReceptionist;
    }

    public void setReceptionist(boolean receptionist) {
        isReceptionist = receptionist;
    }
}
