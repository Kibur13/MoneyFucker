package com.example.mrclean.moneymapper.Accounts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;

import io.realm.Realm;


public class AddAccountActivity extends AppCompatActivity {

    //private List<Account> accounts = AccountDataProvider.accountList;
    private AccountRealmDataMethods Adding;
    private static final String TAG = AddAccountActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Realm.init(this);


        Adding = new AccountRealmDataMethods();
        AccountRealmDataMethods AddToRealm;

        //submit button to collect the account information
        Button submitAccount = (Button) findViewById(R.id.submitTrans);
        submitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText ETAccountName = (EditText) findViewById(R.id.EditAccountName);
                String accountName = ETAccountName.getText().toString();

                EditText ETAccountAmount = (EditText) findViewById(R.id.EditAccountAmount);
                double accountAmount = Integer.parseInt(ETAccountAmount.getText().toString());

                EditText ETAccountDate = (EditText) findViewById(R.id.editAccountDate);
                String accountDate = ETAccountDate.getText().toString();

                EditText ETAccountType = (EditText) findViewById(R.id.EditAccountType);
                String accountType = ETAccountType.getText().toString();


                //Account account = new Account();

                Account newAccount = new Account(accountName, accountDate, accountType, accountAmount);




                Adding.createAccount(newAccount);

                Log.i(TAG, "Account: " + newAccount);



               // AccountDB AccountingDB = new AccountDB(this,null, null,1);

//                AccountingDB.addAccountDB(new Account("netflix","10/29/05", "monthly", 10.00));
//                        AccountDataProvider.accountList.add(newAccount) ;


               // Snackbar.make(view, "Your new transaction is : " + accountName, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }});





//moves back to the previous page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
