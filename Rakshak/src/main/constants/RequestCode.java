package constants;

import java.io.Serializable;

public enum RequestCode implements Serializable {
    LOGIN_REQUEST,
    SIGNUP_REQUEST,
    PATIENT_REQUEST,
    LOGOUT_REQUEST,
    SCHEDULE_REQUEST;

    @Override
    public String toString() {
        return this.name();
    }
}
