package data;

import utils.PayManager;

import java.io.Serializable;
import java.util.List;


public class Admin extends Staff implements Serializable {


    public Admin(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String title, PayManager payManager, List<Attendance> attendances) {
        super(username, password, firstname, lastname, email, userUID, staffID, title, payManager, attendances, true, false);
    }

    public Admin(String username, String password, String firstname, String lastname, String email, String userUID, String phone, String staffID, String QRCode, String title, PayManager payManager, List<Attendance> attendances) {
        super(username, password, firstname, lastname, email, userUID, phone, staffID, QRCode, title, payManager, attendances, false);
    }

    public Admin(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
        this.title = "Admin";
        isAdmin = true;
    }
    public Admin(Staff staff){
        super(staff.getUsername(), staff.getPassword(), staff.getFirstname(), staff.getLastname(), staff.getEmail(), staff.getUserUID(), staff.getPhone(), staff.getDateJoined(), staff.getStaffID(), staff.getQRCode(), staff.getTitle(), staff.getPayManager(), staff.getAttendances(), staff.isAdmin(), staff.isDoctor(), staff.isReceptionist());
    }

    public static Admin findAdmin(List<Admin> adminList, String staffID){    // to find a staff member in a list of staffs using only staffID as for every staff member the staffID would be unique
        for (Admin d:
                adminList) {
            if(d.getStaffID().equals(staffID)){
                return d;
            }
        }
        return null;
    }
}
