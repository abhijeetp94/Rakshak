package request;

import constants.RequestCode;
import constants.StaffType;
import data.Staff;

import java.io.Serializable;

public class StaffRegisterRequest extends Request implements Serializable {
    private Staff staff;
    private StaffType type;

    public StaffRegisterRequest(Staff staff, StaffType type) {
        this.staff = staff;
        this.type = type;
        this.requestCode = RequestCode.STAFF_REGISTER_REQUEST;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public StaffType getType() {
        return type;
    }

    public void setType(StaffType type) {
        this.type = type;
    }
}
