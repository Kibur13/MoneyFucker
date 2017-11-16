package com.example.mrclean.moneymapper.Accounts;

import com.example.mrclean.moneymapper.Transactions.Transaction;

import java.text.DateFormat;
import java.util.Date;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mrclean on 8/29/17.
 */

public class Account extends RealmObject {

    @PrimaryKey
    private String name;

    private String type;
    private Date billedOnDate;
    private Date dateDue;
    private double amount;
    private String priority;
    private String regularity;
    private boolean autoWithDrawl;
    private boolean amountChanges;
    private boolean paymentStatus;
    private RealmList <Transaction> transaction;

    public RealmList<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(RealmList<Transaction> transactions) {
        this.transaction = transactions;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBilledOnDate() {
        return billedOnDate;
    }

    public void setBilledOnDate(Date billedOnDate) {
        this.billedOnDate = billedOnDate;
    }

    public boolean isAmountChanges() {
        return amountChanges;
    }

    public void setAmountChanges(boolean amountChanges) {
        this.amountChanges = amountChanges;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public String getDateString(){return DateFormat.getDateInstance().format(dateDue);}


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getRegularity() {
        return regularity;
    }

    public void setRegularity(String regularity) {
        this.regularity = regularity;
    }


    public boolean isAutoWithdrawl() {
        return autoWithDrawl;
    }

    public void setAutoWithdrawl(boolean autoWithDrawl) {
        this.autoWithDrawl = autoWithDrawl;
    }



    public Account() {

    }


    //constructor for all of Account class
    public Account(String name, String type, Date billedOnDate, Date dateDue, double amount,
                   String priority, String regularity, boolean autoWithDrawl,
                   boolean amountChanges, boolean paymentStatus) {
        this.name = name;
        this.type = type;
        this.billedOnDate = billedOnDate;
        this.dateDue = dateDue;
        this.amount = amount;
        this.priority = priority;
        this.regularity = regularity;
        this.autoWithDrawl = autoWithDrawl;
        this.amountChanges = amountChanges;
        this.paymentStatus = paymentStatus;
    }


//    public Account(Parcel in) {
//    }


//    public Account(String name, double amount)
//    {
//        this.name = name;
//        this.amount = amount;
//    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Account {" + "Name: "+name+", Amount: "+amount+" , Date: "
                + DateFormat.getDateInstance().format(dateDue)+"}";

        //return super.toString();
    }
}
