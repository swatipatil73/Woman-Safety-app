package com.asmbcs.woman.modelclass;

public class EmergencyContact {
    private String name;
    private int imageResId;
    private String phoneNumber;

    public EmergencyContact(String name, int imageResId, String phoneNumber) {
        this.name = name;
        this.imageResId = imageResId;
        this.phoneNumber = phoneNumber;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getPhoneNumber() { return phoneNumber; }
}

