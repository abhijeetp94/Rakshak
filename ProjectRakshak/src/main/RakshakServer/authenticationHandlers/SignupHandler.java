package authenticationHandlers;

import constants.StaffType;
import data.Admin;
import data.Doctor;
import data.Staff;
import data.User;
import mainPack.Main;
import request.SignupRequest;
import request.StaffRegisterRequest;

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
    public static boolean verifyStaffRegistration(StaffRegisterRequest request){
        boolean found=false;
        if (request.getType().equals(StaffType.STAFF)){
            found = (Staff.findStaff(Main.staff, request.getStaff().getStaffID())!=null);
            if(!found)
                return false;
            Main.staff.add(request.getStaff());

        } else if (request.getType().equals(StaffType.ADMIN)){
            found = (Admin.findAdmin(Main.admins, request.getStaff().getStaffID())!=null);
            if(!found)
                return false;
            Main.admins.add((Admin) request.getStaff());

        } else if(request.getType().equals(StaffType.DOCTOR)){
            found = (Staff.findStaff(Main.staff, request.getStaff().getStaffID())!=null);
            if(!found)
                return false;
            Main.doctors.add((Doctor) request.getStaff());
        }
        return true;
    }

}
