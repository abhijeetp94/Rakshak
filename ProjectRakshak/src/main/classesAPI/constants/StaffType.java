package constants;

import java.io.Serializable;

public enum StaffType implements Serializable {
    ADMIN,
    STAFF,
    DOCTOR;

    @Override
    public String toString() {
        return this.name();
    }
}
