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
}
