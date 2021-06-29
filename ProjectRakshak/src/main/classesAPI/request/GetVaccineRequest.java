package request;

import constants.RequestCode;

import java.io.Serializable;

public class GetVaccineRequest extends Request implements Serializable {
    public GetVaccineRequest() {
        this.requestCode = RequestCode.GET_VACCINE_REQUEST;
    }
}
