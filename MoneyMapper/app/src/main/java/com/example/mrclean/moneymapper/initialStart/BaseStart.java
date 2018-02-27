package com.example.mrclean.moneymapper.initialStart;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;


public class BaseStart extends AppCompatActivity implements BalanceStart.BalanceStartListener {

    private static final String TAG = "BaseStart";


    //sets container and launches BalanceStart
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BalanceStart balanceStart = new BalanceStart();
        balanceStart.show(getFragmentManager(), "dialog_balance_start");

        Log.i(TAG, "onCreate: finished, launched BalanceStart");
    }


    //After collecting the Balance, we would take them through a tutorial or the other way around
    //right now it goes launches a new intent of the MainActivity
    @Override
    public void onBalanceStartedDone() {
        Log.i(TAG, "onBalanceStartedDone: Starting MainActivity Intent");

        Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
        startActivity(mainActivity);
    }
}
