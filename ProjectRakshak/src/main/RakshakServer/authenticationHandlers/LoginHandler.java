package authenticationHandlers;

import data.Admin;
import data.Doctor;
import data.Staff;
import data.User;
import mainPack.Main;
import request.AdminLoginRequest;
import request.DoctorLoginRequest;
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

    public static Staff verifyDoctor(DoctorLoginRequest request){
        for (Doctor doctor: Main.doctors){
            if(doctor.getStaffID().equals(request.getDoctorID())){
                if (doctor.getPassword().equals(request.getPassword())){
                    return doctor;
                }
            }
        }
        return null;
    }

    public static Admin verifyAdmin(AdminLoginRequest request){
        for (Staff staff: Main.staff){
            if(staff.getStaffID().equals(request.getStaffID())){
                if (staff.getPassword().equals(request.getPassword()) && staff.isAdmin()){
                    return (Admin) staff;
                }
            }
        }
        return null;
    }

}
