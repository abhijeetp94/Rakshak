package authenticationHandlers;

import data.Staff;
import data.User;
import mainPack.Main;
import request.LoginRequest;
import request.StaffLoginRequest;

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

    public static Staff verifyStaff(StaffLoginRequest request){
        for (Staff staff: Main.staff){
            if(staff.getStaffID().equals(request.getStaffID())){
                if (staff.getPassword().equals(request.getPassword())){
                    return staff;
                }
            }
        }
        return null;
    }

}
