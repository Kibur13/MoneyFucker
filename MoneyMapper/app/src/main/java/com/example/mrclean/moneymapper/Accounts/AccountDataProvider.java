package com.example.mrclean.moneymapper.Accounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;
import java.util.Map;

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
        HCAccountList.add( new Account("Food", accountDate, "weekly", 70));
        HCAccountList.add(new Account("Rent", accountDate, "Monthly", 415));
        HCAccountList.add(new Account("Power", accountDate, "Monthly", 65.35));
        HCAccountList.add(new Account("Water", accountDate, "Bi-Monthly", 100));
    }




}
