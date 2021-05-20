package authenticationHandlers;

import data.User;
import mainPack.Main;
import request.LoginRequest;

public class LoginHandler {
    LoginRequest request;

    public LoginHandler(LoginRequest request) {
        this.request = request;
    }
    public static User verifyUser(LoginRequest request){
        for (User user: Main.users) {
            if(user.getUsername().equals(request.getUsername()) || user.getUserUID().equals(request.getUsername())){
                if (user.getPassword().equals(request.getPassword())){
                    return user;
                }
            }
        }
        return null;
    }
}
