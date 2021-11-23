package com.example.conceptcube;

import java.util.Date;

public class MemberAccount {
    private String id;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    private String email;
    private Date bday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public MemberAccount() {

    }


    public MemberAccount(String id, String password, String email, Date bday) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.bday = bday;
    }

    @Override
    public String toString() {
        return "MemberAccount{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", bday=" + bday +
                '}';
    }
}
