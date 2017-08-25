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

    public Transactions (double amount, String name)
    {
        super();
        this.amount = amount;
        this.name = name;
    }

}
