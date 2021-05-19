package request;

import constants.RequestCode;

public abstract class Request {
    protected RequestCode requestCode;

    public RequestCode getRequestCode() {
        return requestCode;
    }
}
