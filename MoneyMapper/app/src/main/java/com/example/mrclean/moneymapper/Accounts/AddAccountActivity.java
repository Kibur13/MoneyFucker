package com.example.mrclean.moneymapper.Accounts;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;

import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;

import java.util.List;

public class AddAccountActivity extends AppCompatActivity {

    //private List<Account> accounts = AccountDataProvider.accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //submit button to collect the account information
        Button submitAccount = (Button) findViewById(R.id.submitTrans);
        submitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etTransName = (EditText) findViewById(R.id.transName);
                String accountName = etTransName.getText().toString();


                //Account account = new Account();

                Account newAccount = new Account("netflix","10/29/05", "monthly", 10.00);
                        AccountDataProvider.accountList.add(newAccount) ;

               // Snackbar.make(view, "Your new transaction is : " + accountName, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }});





//moves back to the previous page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
