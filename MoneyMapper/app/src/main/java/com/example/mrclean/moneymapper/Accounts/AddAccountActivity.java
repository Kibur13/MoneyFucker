package com.example.mrclean.moneymapper.Accounts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;

import java.util.Calendar;
import java.util.Date;

public class AddAccountActivity extends AppCompatActivity {

    static final int DIALOG_ID=0;
    int year_x, month_x, day_x;
    Date date = null;
    String stringDate;

    //private List<Account> accounts = AccountDataProvider.accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //starts dialog to select the date starting with the current date
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick ();




        //submit button to collect the account information
        Button submitAccount = (Button) findViewById(R.id.submitTrans);
        submitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etTransName = (EditText) findViewById(R.id.accountName);
                String accountName = etTransName.getText().toString();


                //Account account = new Account();

                Account newAccount = new Account("netflix", "10/29/1991", "monthly", 10.00);
                        AccountDataProvider.accountList.add(newAccount) ;

               // Snackbar.make(view, "Your new transaction is : " + accountName, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }});

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
            stringDate = Integer.toString(year_x)+ Integer.toString(month_x) + Integer.toString(day_x);

            Toast.makeText(AddAccountActivity.this, "The date you entered was "
                    + month_x + "-"
                    + day_x + "-"
                    + year_x + " stringDate = " + stringDate, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        else return null;
    }

}
