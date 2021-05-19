package constants;

public enum ResponseCode {
    SUCCESS,
    FAILURE,
    OK,
    ERROR;

    @Override
    public String toString() {
        return this.name();
    }
}
