package request;

import constants.RequestCode;

import java.io.Serializable;

public class LoginRequest extends Request implements Serializable {
    private String username;
    private String password;
    private static final long serialVersionUID = 13344665L ;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
        this.requestCode = RequestCode.LOGIN_REQUEST;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
