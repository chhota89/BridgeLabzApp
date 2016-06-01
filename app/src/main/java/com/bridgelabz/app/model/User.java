package com.bridgelabz.app.model;

/**
 * Created by bridgelabz5 on 26/5/16.
 */
public class User {

    private String phoneNumber;
    private  String name;
    private  int age;

    public User(String name,String phoneNumber, int age) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.age = age;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
