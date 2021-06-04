package request;

import constants.RequestCode;
import data.Doctor;
import data.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ScheduleRequest extends Request implements Serializable {
    private String doctorID;
    public ScheduleRequest(String doctorID) {
        this.doctorID = doctorID;
        requestCode = RequestCode.SCHEDULE_REQUEST;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }
}
