package com.asmbcs.woman.modelclass;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("success")
    private String success;

    @SerializedName("role")
    private String role;

    @SerializedName("username")
    private String username;

    @SerializedName("access")
    private String accessToken;

    @SerializedName("refresh")
    private String refreshToken;

    // Constructor
    public LoginResponse(String success, String role, String username, String accessToken, String refreshToken) {
        this.success = success;
        this.role = role;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters
    public String getSuccess() {
        return success;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    // Setters
    public void setSuccess(String success) {
        this.success = success;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
