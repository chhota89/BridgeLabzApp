package com.bridgelabz.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bridgeit on 9/6/16.
 */

public class UserInfo {

    @SerializedName("name")
    private String firstName;
    @SerializedName("lastName")
    private  String lastName;

    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
