package request;

import constants.RequestCode;

import java.io.Serializable;

public class VaccineAvailabilityRequest extends Request implements Serializable {
    public VaccineAvailabilityRequest() {
        this.requestCode = RequestCode.VACCINE_AVAILABILITY_REQUEST;
    }
}
