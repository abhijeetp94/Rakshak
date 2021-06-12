package request;

import constants.RequestCode;

import java.io.Serializable;

public class BedAvailabilityRequest extends Request implements Serializable {
    public BedAvailabilityRequest() {
        requestCode = RequestCode.BED_AVAILABILITY_REQUEST;
    }
}
