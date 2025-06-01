package com.asmbcs.woman.modelclass;



import com.google.gson.annotations.SerializedName;

public class User {
    private int id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private String mobile;
    private String birthdate;

    @SerializedName("married_status")
    private String marriedStatus;

    private String role;
    private String address;
    private String email;
    private String username;
    private String password;

    // Constructors
    public User(String firstName, String lastName, String mobile, String birthdate,
                String marriedStatus, String role, String address, String email,
                String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.birthdate = birthdate;
        this.marriedStatus = marriedStatus;
        this.role = role;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMobile() { return mobile; }
    public String getBirthdate() { return birthdate; }
    public String getMarriedStatus() { return marriedStatus; }
    public String getRole() { return role; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
