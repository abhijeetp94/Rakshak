package data;

import utils.PayManager;

import java.io.Serializable;
import java.util.List;


public class Admin extends Staff implements Serializable {


    public Admin(String username, String password, String firstname, String lastname, String email, String userUID, String staffID, String title, PayManager payManager, List<Attendance> attendances) {
        super(username, password, firstname, lastname, email, userUID, staffID, title, payManager, attendances, true);
    }

    public Admin(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
        this.title = "Admin";
        isAdmin = true;
    }
}
