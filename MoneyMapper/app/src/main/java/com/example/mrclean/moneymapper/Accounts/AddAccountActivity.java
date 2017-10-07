package com.example.mrclean.moneymapper.Accounts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;


public class AddAccountActivity extends AppCompatActivity {

    //variables for Calendar
    static final int DIALOG_ID=0;
    int year_x, month_x, day_x;
    String stringDate;
    Date accountDate;
    Calendar accountCal;
    final Calendar cal = Calendar.getInstance();


    private AccountRealmDataMethods Adding;
    private static final String TAG = AddAccountActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //starts dialog to select the date starting with the current date

        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick ();




        //Realm init
        Realm.init(this);
        Adding = new AccountRealmDataMethods();


        //submit button to collect the account information
        Button submitAccount = (Button) findViewById(R.id.submitTrans);
        submitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText ETAccountName = (EditText) findViewById(R.id.accountName);
                String accountName = ETAccountName.getText().toString();

                EditText ETAccountAmount = (EditText) findViewById(R.id.accountAmount);
                double accountAmount = Double.parseDouble(ETAccountAmount.getText().toString());

                EditText ETAccountType = (EditText) findViewById(R.id.accountType);
                String accountType = ETAccountType.getText().toString();

                Account newAccount = new Account(accountName, accountDate, accountType, accountAmount);

                Adding.createAccount(newAccount);

                Log.i(TAG, "Account: " + newAccount);



            }});




        Button deleteAll = (Button) findViewById(R.id.deleteAll);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adding.deleteEverything();
            }
        });

//moves back to the previous page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //all this code is to get the Dialog Date Selector to work
    public void showDialogOnButtonClick (){
        Button addDate = (Button) findViewById(R.id.selectDate);
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    DatePickerDialog.OnDateSetListener dpickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            cal.set(year_x, month_x, day_x);
            accountDate = cal.getTime();

            stringDate = DateFormat.getDateInstance().format(accountDate);

//            stringDate = Integer.toString(month_x) + "/"
//                        + Integer.toString(day_x) + "/"
//                        + Integer.toString(year_x);

            Toast.makeText(AddAccountActivity.this, "The date you entered was "
                    + stringDate , Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        else return null;
    }


    @Override
    protected void onResume(){
        super.onResume();
        Adding.open();

    }

    @Override
    protected void onPause(){
        super.onPause();
        Adding.close();

    }




}
