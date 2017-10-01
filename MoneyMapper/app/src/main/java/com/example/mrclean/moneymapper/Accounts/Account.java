package com.example.mrclean.moneymapper.Accounts;

import java.util.UUID;

import android.os.Parcel;


import io.realm.RealmObject;
import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mrclean on 8/29/17.
 */

public class Account extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String name;
    private String date;
    private String type;
    private double amount;
    //private int id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    //constructor for Account class
    public Account(String name, String date, String type, double amount) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

//    public Account(Parcel in) {
//    }

    public Account(String name, double amount)
    {
        this.name = name;
        this.amount = amount;
    }

    //constructor 3 for account
    public Account()
    {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Account {" + "Name'"+name+", amount due '"+amount+" , date '"+date+" , type '"+type+"}";

        //return super.toString();
    }
}
