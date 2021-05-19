package constants;

public enum RequestCode {
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
