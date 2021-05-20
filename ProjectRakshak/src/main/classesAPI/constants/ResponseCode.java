package constants;

import java.io.Serializable;

public enum ResponseCode implements Serializable {
    SUCCESS,
    FAILURE,
    OK,
    ERROR;

    @Override
    public String toString() {
        return this.name();
    }
}
