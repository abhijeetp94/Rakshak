package mainPack;

import authenticationHandlers.LoginHandler;
import authenticationHandlers.SignupHandler;
import constants.RequestCode;
import constants.ResponseCode;
import data.*;
import generalHandlers.DataHandler;
import generalHandlers.ScheduleHandler;
import request.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    public static ObjectOutputStream oosTracker;
    public static ObjectInputStream oisTracker;
    public ClientHandler(Socket socket){
        this.socket = socket;
        try {
            oisTracker = new ObjectInputStream(this.socket.getInputStream());
            oosTracker = new ObjectOutputStream(this.socket.getOutputStream());
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean quit = false;
        while (!quit) {
            Request request = null;
            Object ob;
            try {
//                System.out.println("Here");
                ob = oisTracker.readObject();
//                System.out.println("Object Read" + ob.toString());
                request = (Request) ob;
//                System.out.println("Object Read" + request.getRequestCode());

            } catch (IOException | ClassNotFoundException ie){
                quit = true;
                System.out.println("End of inputs: " + ie.getMessage());

            }
            try {

                // If request is login request
                // Send Response
                if (request.getRequestCode().equals(RequestCode.LOGIN_REQUEST)){
                    System.out.println("Login Request.");
                    User result = LoginHandler.verifyUser((LoginRequest) request);
                    if (result!=null){                                          // send response if user exists
                        Response response = new Response("LOGIN", ResponseCode.SUCCESS, result);
                        try {
                            oosTracker.writeObject(response);
                        } catch (IOException ie){
                            System.out.println("Problem Sending Object " + ie.getMessage());
                        }
                    } else {                                                    // if User not found
                        Response response = new Response("LOGIN", ResponseCode.FAILURE, result);
                        try {
                            oosTracker.writeObject(response);
                        } catch (IOException ie){
                            System.out.println("Problem Sending Object " + ie.getMessage());
                        }
                    }
                    System.out.println("Username = " + ((LoginRequest) request).getUsername());
                }

                else if(request.getRequestCode().equals(RequestCode.SIGNUP_REQUEST)){       //signup request handler
                    System.out.println("Signup Request.");
                    boolean result = SignupHandler.verifySignup((SignupRequest) request);
                    Response response = null;

                    if(result){
                        response = new Response("SIGNUP", ResponseCode.SUCCESS, null);
                    } else {
                        response = new Response("SIGNUP", ResponseCode.FAILURE, null);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        System.out.println("Error in signup: " + ie.getMessage());
                    }

                } else if(request.getRequestCode().equals(RequestCode.STAFF_LOGIN_REQUEST)){
                    System.out.println("Staff Login Request.");
                    Staff result = LoginHandler.verifyStaff((StaffLoginRequest) request);
                    Response response;
                    if (result!=null){
                        response = new Response("STAFF_LOGIN", ResponseCode.SUCCESS, result);
                    } else {
                        response = new Response("STAFF_LOGIN", ResponseCode.FAILURE, result);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        System.out.println("Error in staff login: " + ie.getMessage());
                    }

                } else if(request.getRequestCode().equals(RequestCode.DOCTOR_LOGIN_REQUEST)){
                    System.out.println("Doctor Login Request.");
                    Staff result = LoginHandler.verifyDoctor((DoctorLoginRequest) request);
                    Response response;
                    if (result!=null){
                        response = new Response("DOCTOR_LOGIN", ResponseCode.SUCCESS, result);
                    } else {
                        response = new Response("DOCTOR_LOGIN", ResponseCode.FAILURE, result);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        System.out.println("Error in staff login: " + ie.getMessage());
                    }

                } else if(request.getRequestCode().equals(RequestCode.ADMIN_LOGIN_REQUEST)){
                    System.out.println("Admin Login Request.");
                    Admin result = LoginHandler.verifyAdmin((AdminLoginRequest) request);
                    Response response;
                    if (result!=null){
                        System.out.println(result.getUsername());
                        response = new Response("ADMIN_LOGIN", ResponseCode.SUCCESS, result);
                    } else {
                        response = new Response("ADMIN_LOGIN", ResponseCode.FAILURE, result);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        System.out.println("Error in staff login: " + ie.getMessage());
                    }

                } else if(request.getRequestCode().equals(RequestCode.STAFF_REGISTER_REQUEST)) {
                    System.out.println("Staff Register Request.");
                    boolean result = SignupHandler.verifyStaffRegistration((StaffRegisterRequest) request);
                    Response response;
                    if (result){
                        response = new Response("STAFF_REGISTRATION", ResponseCode.SUCCESS, null);
                    } else {
                        response = new Response("STAFF_REGISTRATION", ResponseCode.FAILURE, null);
                    }

                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        System.out.println("Error in staff registration: " + ie.getMessage());
                    }

                } else if(request.getRequestCode().equals(RequestCode.TIMETABLE_REQUEST)){
                    System.out.println("TimeTable Get Request.");
                    List<TimeTable> timeTables = ScheduleHandler.getTimeTable();
                    Response response;
                    response = new Response("GET_TIMETABLE", ResponseCode.SUCCESS, timeTables);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.SCHEDULE_REQUEST)){        // to get schedules and schedule related to doctor
                    System.out.println("Schedule Get Request.");
                    List<Schedule> schedules;
                    if(((ScheduleRequest) request).getDoctorID().equalsIgnoreCase("ALL")){ // if request demands all doctors
                        schedules = ScheduleHandler.getSchedule();
                    }else{
                        schedules = ScheduleHandler.getSchedule(((ScheduleRequest) request).getDoctorID()); // if doctor id is provided for specific doctor
                    }
                    Response response;
                    response = new Response("GET_SCHEDULE", ResponseCode.SUCCESS, schedules);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.GET_DOCTORS_REQUEST)){
                    GetDoctorsRequest doctorsRequest = (GetDoctorsRequest) request;
                    if(doctorsRequest.getDoctorID().equalsIgnoreCase("ALL")){
                        List<Doctor> docList = DataHandler.getDoctors();
                        Response response = new Response("ALL_DOCTORS_RESPONSE", ResponseCode.SUCCESS, docList);
                        try {
                            oosTracker.writeObject(response);
                        } catch (IOException ie){
                            ie.printStackTrace();
                        }
                    } else {
                        Doctor doc = DataHandler.getDoctor(doctorsRequest.getDoctorID());
                        Response response = new Response("SINGLE_DOCTOR_RESPONSE", ResponseCode.SUCCESS, doc);
                        try {
                            oosTracker.writeObject(response);
                            System.out.println("Doctors response sent");
                        } catch (IOException ie){
                            ie.printStackTrace();
                        }
                    }
                } else if(request.getRequestCode().equals(RequestCode.APPOINTMENT_REQUEST)){
                    AppointmentRequest appointmentRequest = (AppointmentRequest) request;
                    Boolean res = ScheduleHandler.addSchedule(appointmentRequest.getSchedule());
                    Response response = null;
                    if(res){
                        response = new Response("APPOINTMENT_RESPONSE", ResponseCode.SUCCESS, null);
                    }
                    else {
                        response = new Response("APPOINTMENT_RESPONSE", ResponseCode.FAILURE, null);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.APPROVE_APPOINTMENT_REQUEST)){
                    AppointmentApproveRequest approveRequest = (AppointmentApproveRequest) request;
                    boolean res = ScheduleHandler.approveSchedule(approveRequest.getSchedules());
                    Response response = new Response("APPROVED_APPOINTMENTS", ResponseCode.SUCCESS, res);

                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if (request.getRequestCode().equals(RequestCode.EDIT_STAFF_DETAILS_REQUEST)){
                    EditStaffDetailsRequest detailsRequest = (EditStaffDetailsRequest) request;
                    boolean res = DataHandler.updateStaff(detailsRequest.getStaff());
                    Response response;
                    if(res)
                        response = new Response("EDIT_STAFF_DETAILS", ResponseCode.SUCCESS, null);
                    else
                        response = new Response("EDIT_STAFF_DETAILS", ResponseCode.FAILURE, null);

                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.BED_AVAILABILITY_REQUEST)){
                    BedAvailabilityRequest bedRequest = (BedAvailabilityRequest) request;
                    List<Bed> beds = DataHandler.getBeds();
                    Response response;
                    response = new Response("BES_AVAILABILITY", ResponseCode.SUCCESS, beds);

                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.GET_USER_REQUEST)){
                    GetUserRequest userRequest = (GetUserRequest) request;
                    User user = DataHandler.getUser(userRequest.getUserID());
                    System.out.println("In Client handler" + (user == null));
                    Response response = new Response("SINGLE_USER_RESPONSE", ResponseCode.SUCCESS, user);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                }else if(request.getRequestCode().equals(RequestCode.GET_STAFF_REQUEST)){
                    GetStaffRequest staffRequest = (GetStaffRequest) request;
                    Response response;
                    if (staffRequest.getStaffID().equalsIgnoreCase("ALL")){
                        List<Staff> staff = DataHandler.getStaff();
                        response = new Response("SINGLE_USER_RESPONSE", ResponseCode.SUCCESS, staff);
                    }
                    else{
                        Staff staff = DataHandler.getStaff(staffRequest.getStaffID());
                        response = new Response("SINGLE_USER_RESPONSE", ResponseCode.SUCCESS, staff);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.BOOK_BED_REQUEST)){
                    BookBedRequest bedRequest = (BookBedRequest) request;
                    boolean res = DataHandler.bookBed(bedRequest);
                    Response response;
                    if(res){
                        response = new Response("BOOK_BED_RESPONSE", ResponseCode.SUCCESS, null);
                    }
                    else {
                        response = new Response("BOOK_BED_RESPONSE", ResponseCode.FAILURE, null);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.MARK_ATTENDANCE_REQUEST)){
                    MarkAttendanceRequest attendanceRequest = (MarkAttendanceRequest) request;
                    boolean res = ScheduleHandler.markAttendance(attendanceRequest.getAttendance());
                    Response response;
                    if(res){
                        response = new Response("MARK_ATTENDANCE_RESPONSE", ResponseCode.SUCCESS, null);
                    }
                    else {
                        response = new Response("MARK_ATTENDANCE_RESPONSE", ResponseCode.FAILURE, null);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                }




            } catch (NullPointerException ne){
                ne.printStackTrace();
                System.out.println("Null aaya h: "+ ne.getMessage());
            }


        }

    }
}
