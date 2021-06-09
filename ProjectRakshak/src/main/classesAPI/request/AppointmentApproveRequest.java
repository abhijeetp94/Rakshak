package request;

import constants.RequestCode;
import data.Schedule;

import java.io.Serializable;
import java.util.List;

public class AppointmentApproveRequest extends Request implements Serializable {
    List<Schedule> schedules;

    public AppointmentApproveRequest(List<Schedule> schedules) {
        this.schedules = schedules;
        requestCode = RequestCode.APPROVE_APPOINTMENT_REQUEST;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
