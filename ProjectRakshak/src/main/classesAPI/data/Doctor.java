package data;

import java.io.Serializable;

public class Doctor extends User implements Serializable {
    String doctorID;
    boolean available = true;

    public Doctor(String username, String password, String firstname, String lastname, String email, String userUID) {
        super(username, password, firstname, lastname, email, userUID);
    }

    public Doctor(String username, String password) {
        super(username, password);
    }
}
