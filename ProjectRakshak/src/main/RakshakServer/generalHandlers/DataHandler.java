package generalHandlers;

import data.Bed;
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

    public static boolean updateStaff(Staff staff){
        int id = -1;
        for (Staff s:
              Main.staff) {
            if(s.getStaffID().equals(staff.getStaffID())){
                id = Main.staff.indexOf(s);
            }
        }
        if(id == -1){
            return false;
        }
        Main.staff.set(id, staff);
        return true;
    }

    public static List<Bed> getBeds(){
        return Main.beds;
    }

    public static List<Staff> getStaff(){
        return Main.staff;
    }


}
