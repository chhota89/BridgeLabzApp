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

    public ORMUser(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
