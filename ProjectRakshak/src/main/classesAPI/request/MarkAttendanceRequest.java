package request;

import constants.RequestCode;
import data.Attendance;

import java.io.Serializable;

public class MarkAttendanceRequest extends Request implements Serializable {
    private Attendance attendance;

    public MarkAttendanceRequest(Attendance attendance) {
        this.attendance = attendance;
        this.requestCode = RequestCode.MARK_ATTENDANCE_REQUEST;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
