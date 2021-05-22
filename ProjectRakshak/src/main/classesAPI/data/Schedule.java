package data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Schedule {
    private LocalDate theDate;
    private LocalTime theTime;
    private Doctor doctor;
    private List<User> userList;

    public Schedule(LocalDate theDate, LocalTime theTime, Doctor doctor, List<User> userList) {
        this.theDate = theDate;
        this.theTime = theTime;
        this.doctor = doctor;
        this.userList = userList;
    }

    public LocalDate getTheDate() {
        return theDate;
    }

    public void setTheDate(LocalDate theDate) {
        this.theDate = theDate;
    }

    public LocalTime getTheTime() {
        return theTime;
    }

    public void setTheTime(LocalTime theTime) {
        this.theTime = theTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user){
        this.userList.add(user);
    }
    public int findUser(User user){
        int index=0;
        boolean found = this.userList.contains(user);
        return (found?this.userList.indexOf(user):-1);
    }
    public boolean deleteUser(User user){
        boolean found = this.userList.contains(user);
        if(found){
            this.userList.remove(user);
        }
        return found;
    }
}
