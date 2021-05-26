package request;

import java.io.Serializable;

public class AdminLoginRequest extends Request implements Serializable {
    private String staffID;
    private String password;

    public AdminLoginRequest(String staffID, String password) {
        this.staffID = staffID;
        this.password = password;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
