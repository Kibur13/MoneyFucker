package com.example.mrclean.moneymapper.Accounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mrclean on 8/29/17.
 */

public final class AccountDataProvider {


    public static List<Account> HCAccountList;


    //hard-coding for realm start-up
    static {

        HCAccountList = new ArrayList<>();

        //creates Calendar and makes it a Date set to the current date
        final Calendar cal = Calendar.getInstance();
        Date accountDate = cal.getTime();


        //objects getting added to realm
        HCAccountList.add(new Account("Food", "Expense", accountDate, accountDate, 70, "Medium", "Weekly", true, false, true));
        HCAccountList.add(new Account("Rent", "Bill", accountDate, accountDate, 415, "High", "Monthly", false, false, true));
        HCAccountList.add(new Account("Power", "Bill", accountDate, accountDate, 65.35, "High", "Monthly", false, true, true));
        HCAccountList.add(new Account("Water", "Bill", accountDate, accountDate, 100, "High", "Bi-Monthly", false, true, false));
    }




}
