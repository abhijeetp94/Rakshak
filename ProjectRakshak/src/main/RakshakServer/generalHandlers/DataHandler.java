package generalHandlers;

import data.Doctor;
import data.Staff;
import mainPack.Main;

import java.util.List;

public class DataHandler {
    public static List<Doctor> getDoctors(){
        return Main.doctors;
    }

    public static Doctor getDoctor(String doctorID){
        Doctor doc = Doctor.findDoctor(Main.doctors, doctorID);
        return doc;
    }

    public static Staff getStaffMember(String staffID){
        Staff staff = Staff.findStaff(Main.staff, staffID);
        return staff;
    }

    public static List<Staff> getStaff(){
        return Main.staff;
    }


}
