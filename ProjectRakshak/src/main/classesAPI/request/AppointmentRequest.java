package request;

import constants.RequestCode;
import data.Schedule;

import java.io.Serializable;

public class AppointmentRequest extends Request implements Serializable {
    private Schedule schedule;

    public AppointmentRequest(Schedule schedule) {
        this.schedule = schedule;
        requestCode = RequestCode.APPOINTMENT_REQUEST;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
