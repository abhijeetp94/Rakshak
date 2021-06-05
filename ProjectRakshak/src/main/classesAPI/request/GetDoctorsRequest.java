package request;

import constants.RequestCode;

import java.io.Serializable;

public class GetDoctorsRequest extends Request implements Serializable {
    public GetDoctorsRequest() {
        requestCode = RequestCode.GET_DOCTORS_REQUEST;
    }

}
