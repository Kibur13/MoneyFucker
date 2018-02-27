package com.example.mrclean.moneymapper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;

import com.example.mrclean.moneymapper.initialStart.BaseStart;


public class SharedPreferencesHelper extends ContextWrapper {

    public SharedPreferencesHelper(Context base){
        super(base);
    }

    private static final String TAG = "MoneyMapperSharedPref";

    private static SharedPreferences vSharedPref;
    private static SharedPreferences.Editor vEditor;
    private static SharedPreferences bSharedPref;
    private static SharedPreferences.Editor bEditor;


    //Initialized the SharedPreferences variables that are above
    public void StartSharedPreferences(){
        //sets up general sharedPreferences file
        vSharedPref = getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        vEditor = vSharedPref.edit();

        //sets up Balance sharedPreferences file
        bSharedPref = getSharedPreferences(getString(R.string.balance_preferences), Context.MODE_PRIVATE);
        bEditor = bSharedPref.edit();
    }


    //Checks if this is the first time the app has run and if an update has occurred
    public void VersionCheck(int mmVersionNumber){

        //gets the stored versionNumber, we can change the VersionNumber here when we do updates
        int versionNumber = vSharedPref.getInt(getString(R.string.versionNumber), 0 );
        Log.i(TAG, "onCreate: versionNumber = " + versionNumber);

        //checks to see if it's the first time the app has run
        if (versionNumber == 0 ){
            Intent baseStart = new Intent(this, BaseStart.class);
            startActivity(baseStart);
            Log.i(TAG, "VersionCheck: version = 0 and launching BaseStart");
        }

        //checks if an update has occurred
        if (versionNumber != mmVersionNumber) {

            // TODO: 12/12/17  where code would go on an update

            //sets versionNumber to the current version when we are done setting up
            //we might want to add this to the end of whatever method we call but for now this works
            vEditor.putInt(getString(R.string.versionNumber), mmVersionNumber);
            vEditor.apply();
        }
    }


    //Sets Balance amount
    public void SetBalance(Double balance){
        bEditor.putLong(getString(R.string.current_balance), Double.doubleToLongBits(balance));
        Log.i(TAG, "SetBalance: balance = " + balance);
        bEditor.apply();
    }


    //Gets Balance Amount
    public Double GetBalance(){
        Double balance = Double.longBitsToDouble(bSharedPref.getLong(getString(R.string.current_balance), 0));
        Log.i(TAG, "GetBalance: balance = " + balance);
        return balance;
    }

}

