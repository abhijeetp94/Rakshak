package request;

import constants.RequestCode;

import java.io.Serializable;

public class TimeTableRequest extends Request implements Serializable {
    public TimeTableRequest() {
        this.requestCode = RequestCode.TIMETABLE_REQUEST;
    }
}
