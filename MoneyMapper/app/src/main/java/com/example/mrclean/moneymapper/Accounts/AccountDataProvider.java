package com.example.mrclean.moneymapper.Accounts;

import java.util.ArrayList;
import java.util.List;
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;
import java.util.Map;

/**
 * Created by mrclean on 8/29/17.
 */

public final class AccountDataProvider {


    //allows the information being brought in to be constructed into an Array
    private static void addAccount(String name, String date,
                                   String type, double amount) {
        Account account = new Account(name, date, type, amount);
        accountList.add(account);

    }

    //raw data that is being fed into the array List
    public static List<Account> accountList = new ArrayList<>();

    static {

        addAccount("Food", "8/27/17", "weekly", 70);
        addAccount("Gas", "9/1/17", "Weekly", 40);
        addAccount("Rent", "9/1/17", "Monthly", 415);
        addAccount("Power", "9/5/17", "Monthly", 65.35);
        addAccount("Water", "10/12/17", "Bi-Monthly", 100);

    }




}
