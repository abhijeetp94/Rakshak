package request;

import constants.RequestCode;

import java.io.Serializable;

public class DoctorLoginRequest extends Request implements Serializable {
    String doctorID;
    String password;

    public DoctorLoginRequest(String doctorID, String password) {
        this.doctorID = doctorID;
        this.password = password;
        requestCode = RequestCode.DOCTOR_LOGIN_REQUEST;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
