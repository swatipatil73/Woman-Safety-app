package com.asmbcs.woman.modelclass;

public class RegisterRequest {
    private String first_name;
    private String last_name;
    private String mobile;
    private String birthdate;
    private String married_status;
    private String role;
    private String address;
    private String email;
    private String username;
    private String password;

    public RegisterRequest(String first_name, String last_name, String mobile, String birthdate,
                           String married_status, String role, String address, String email,
                           String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile = mobile;
        this.birthdate = birthdate;
        this.married_status = married_status;
        this.role = role;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

