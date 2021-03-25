package com.hamza.app.Model;

public class User {


    private String email ;
    private String passward ;
    private String fullname ;

    public User() {
    }

    public User(String email, String passward) {
        this.email = email;
        this.passward = passward;
    }

    public User(String email, String passward, String fullname) {
        this.email = email;
        this.passward = passward;
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }



    public String getPassward() {
        return passward;
    }



    public String getFullname() {
        return fullname;
    }


}
