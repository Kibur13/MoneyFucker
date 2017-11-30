package com.example.mrclean.moneymapper.Accounts.AccountCreation;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.R;

/**
 * Created by jmd71_000 on 11/27/2017.
 */

public class AccountEditing extends AppCompatActivity {


    private final String accountID;
    private final String accountTYPE;

    private Account account;


    public AccountEditing(String accountID, String accountTYPE)
    {
        this.accountID = accountID;
        this.accountTYPE = accountTYPE;

    }


    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        setContentView(R.layout.account_editing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        return super.onCreateView(parent, name, context, attrs);



    }
}
