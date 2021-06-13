package request;

import constants.RequestCode;

import java.io.Serializable;

public class GetUserRequest extends Request implements Serializable {
    String userID;

    public GetUserRequest(String userID) {
        this.userID = userID;
        requestCode = RequestCode.GET_USER_REQUEST;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
