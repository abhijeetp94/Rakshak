package authenticationHandlers;

import data.User;
import mainPack.Main;
import request.SignupRequest;

public class SignupHandler {
    SignupRequest signupRequest;

    public SignupHandler(SignupRequest signupRequest) {
        this.signupRequest = signupRequest;
    }

    public static boolean verifySignup(SignupRequest request){
        if (findUser(request.getUser())){
            return false;
        }
        Main.users.add(request.getUser());
        return true;
    }

    public static boolean findUser(User user){
        for(User users: Main.users){
            if(users.getUsername().equals(user.getUsername()) || users.getUserUID().equals(user.getUserUID())){
                return true;
            }
        }
        return false;
    }
    public static boolean verifyStaffRegistration(){

    }

}
