package com.example.android.firstapp;

/**
 * Created by j2md7_000 on 8/21/2017.
 */

public class Transactions {

    public double amount;
    public int date;
    public String name;

    public Transactions()
    {
        super();
    }

    public Transactions (String name, double amount )
    {
        super();
        this.amount = amount;
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
