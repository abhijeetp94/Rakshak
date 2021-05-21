package constants;

import java.io.Serializable;

public enum ResponseCode implements Serializable {
    SUCCESS,
    FAILURE,
    AVAILABLE,
    UNAVAILABLE,
    OK,
    ERROR;

    @Override
    public String toString() {
        return this.name();
    }
}
