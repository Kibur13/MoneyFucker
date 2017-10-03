package com.example.mrclean.moneymapper.Accounts;

import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by mrclean on 8/29/17.
 */

public class Account {

    private String name;
    private String date;
    private String type;
    private double amount;


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


}
