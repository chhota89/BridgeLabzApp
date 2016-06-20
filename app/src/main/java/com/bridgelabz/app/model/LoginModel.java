package com.bridgelabz.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bridgeit on 20/6/16.
 */

public class LoginModel {

    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("Password")
    @Expose
    private String password;

    public LoginModel(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    /**
     *
     * @return
     * The emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     *
     * @param emailId
     * The EmailId
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
