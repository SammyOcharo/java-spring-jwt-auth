package com.samdev.jwt_auth.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "USER_ACCOUNT_VERIFY")
public class UserAccountVerify {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private Integer otp;
    private boolean isUsed=false;

    public UserAccountVerify() {
    }

    public UserAccountVerify(Long id,
                             String email,
                             Integer otp,
                             boolean isUsed) {
        this.id = id;
        this.email = email;
        this.otp = otp;
        this.isUsed = isUsed;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
