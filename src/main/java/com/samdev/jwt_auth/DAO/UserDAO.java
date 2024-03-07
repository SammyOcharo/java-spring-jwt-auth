package com.samdev.jwt_auth.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDAO {
    private String email;
    private String mobileNumber;
    private String role;
    private String password;

    private String responseCode;
    private String responseMessage;

    public UserDAO() {
    }

    public UserDAO(String email, String mobileNumber, String role, String password, String responseCode, String responseMessage) {
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.password = password;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
