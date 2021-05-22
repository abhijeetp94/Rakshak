package request;

import constants.RequestCode;
import data.Doctor;
import data.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ScheduleRequest extends Request implements Serializable {
    public ScheduleRequest() {
        requestCode = RequestCode.SCHEDULE_REQUEST;
    }
}
