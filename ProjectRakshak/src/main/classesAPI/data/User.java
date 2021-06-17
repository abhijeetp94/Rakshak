package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class User implements Serializable {
    protected String username;
    protected String password;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String userUID;
    protected String fullName;
    protected String phone;
    protected LocalDate dateJoined;

    public User(String username, String password, String firstname, String lastname, String email, String userUID) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userUID = userUID;
        this.dateJoined = LocalDate.now();
        fullName = firstname + " " + lastname;
    }
    public User(String username, String password, String firstname, String lastname, String email, String userUID, String phone) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userUID = userUID;
        this.dateJoined = LocalDate.now();
        this.phone = phone;
        fullName = firstname + " " + lastname;
    }

    public User(String username, String password, String firstname, String lastname, String email, String userUID, String phone, LocalDate dateJoined) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userUID = userUID;
        this.phone = phone;
        this.dateJoined = dateJoined;
        fullName = firstname + " " + lastname;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public static User findUser(List<User> users, String username){
        for (User u:
             users) {
            if(u.getUserUID().equals(username) || u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
