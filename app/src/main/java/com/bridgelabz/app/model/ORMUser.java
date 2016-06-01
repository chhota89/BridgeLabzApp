package com.bridgelabz.app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by bridgelabz5 on 30/5/16.
 */

@DatabaseTable(tableName = "ORMUser")
public class ORMUser  {

    public ORMUser(){

    }
    @DatabaseField(generatedId = true,columnName = "id")
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "age" , canBeNull = false)
    private int age;

    @DatabaseField(columnName = "phoneNumber" , canBeNull = false)
    private String phoneNumber;

    public ORMUser(String name, String phoneNumber,int age) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public ORMUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return id+"    "+name+"    "+phoneNumber+"    "+age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return String.valueOf(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
