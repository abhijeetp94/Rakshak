package request;

import constants.RequestCode;

import java.io.Serializable;

public class GetDoctorsRequest extends Request implements Serializable {
    private String doctorID;

    public GetDoctorsRequest() {
        doctorID = "ALL";
        requestCode = RequestCode.GET_DOCTORS_REQUEST;
    }

    public GetDoctorsRequest(String doctorID) {
        this.doctorID = doctorID;
        requestCode = RequestCode.GET_DOCTORS_REQUEST;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }
}
