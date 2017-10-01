package com.example.mrclean.moneymapper.Accounts;

import java.util.ArrayList;
import java.util.List;
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;
import java.util.Map;

/**
 * Created by mrclean on 8/29/17.
 */

public final class AccountDataProvider {


    public static List<Account> HCAccountList;



    //allows the information being brought in to be constructed into an Array

//    private static void addAccount(String name, String date,
//                                   String type, double amount) {
//        Account account = new Account(name, date, type, amount);
//        accountList.add(account);
//
//    }

    //raw data that is being fed into the array List
    public static List<Account> accountList = new ArrayList<>();


    static {

        HCAccountList = new ArrayList<>();


      //  addAccount("Food", "8/27/17", "weekly", 70);
      //  addAccount("Gas", "9/1/17", "Weekly", 40);
      //  addAccount("Rent", "9/1/17", "Monthly", 415);
       // addAccount("Power", "9/5/17", "Monthly", 65.35);
       // addAccount("Water", "10/12/17", "Bi-Monthly", 100);



        HCAccountList.add( new Account("Food", "8/27/17", "weekly", 70));
        //AccountingDB.addAccountDB(new Account("Food", "8/27/17", "weekly", 70);

        HCAccountList.add(new Account("Rent", "9/1/17", "Monthly", 415));
       // AccountingDB.addAccountDB(new Account("Rent", "9/1/17", "Monthly", 415));

        HCAccountList.add(new Account("Power", "9/5/17", "Monthly", 65.35));
       // AccountingDB.addAccountDB(new Account("Power", "9/5/17", "Monthly", 65.35));

        HCAccountList.add(new Account("Water", "10/12/17", "Bi-Monthly", 100));
       // AccountingDB.addAccountDB(new Account("Water", "10/12/17", "Bi-Monthly", 100));

    }




}
