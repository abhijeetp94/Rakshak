package request;

import data.Staff;

import java.io.Serializable;

public class EditStaffDetailsRequest extends Request implements Serializable {
    private Staff staff;

    public EditStaffDetailsRequest(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
