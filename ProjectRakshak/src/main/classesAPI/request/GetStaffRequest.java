package request;

import constants.RequestCode;
import data.Staff;

import java.io.Serializable;

public class GetStaffRequest extends Request implements Serializable {
    private String StaffID;

    public GetStaffRequest() {
        this.StaffID = "ALL";
        requestCode = RequestCode.GET_STAFF_REQUEST;
    }

    public GetStaffRequest(String staffID) {
        StaffID = staffID;
        requestCode = RequestCode.GET_STAFF_REQUEST;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }
}
