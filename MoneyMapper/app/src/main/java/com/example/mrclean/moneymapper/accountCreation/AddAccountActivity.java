package com.example.mrclean.moneymapper.accountCreation;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.mrclean.moneymapper.FuturePayCalculator;
import com.example.mrclean.moneymapper.DatePickerFragment;
import com.example.mrclean.moneymapper.R;

import java.text.DateFormat;
import java.util.Date;


public class AddAccountActivity extends AppCompatActivity
        implements BillOrExpense.BillOrExpenseListener,
         DatePickerFragment.DateSetListener, NewBill.NewBillDateListener,
        NewExpense.DispenseDateListener, NewIncome.NewIncomeDateListener{

    private static final String TAG = "AddAccountActivity";

    public static Date billedOnDate;
    public static Date billDue;


    //launches BillOrExpense
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BillOrExpense billOrExpense = new BillOrExpense();
        billOrExpense.show(getSupportFragmentManager(), "BILL_OR_EXPENSE");
    }


    //Listener for new account type (BillOrExpense) and chooses the correct fragment to use
    @Override
    public void onBillOrExpenseChosen(String accountType) {


        switch (accountType){

            case "BILL":
                Log.i(TAG, "onBillOrExpenseChosen: Bill Chosen" + accountType);
                NewBill bill = new NewBill();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, bill)
                        .commit();
                break;
            case "EXPENSE":
                Log.i(TAG, "onBillOrExpenseChosen: Expense Chosen");
                NewExpense expense = new NewExpense();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, expense)
                        .commit();
                break;
            case "INCOME":
                Log.i(TAG, "onBillOrExpenseChosen: Income Chosen");
                NewIncome income = new NewIncome();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, income)
                        .commit();
                break;
            case "FUTURE_PAY":
                Log.i(TAG, "onBillOrExpenseChosen: FuturePay Chosen");
                FuturePayCalculator futurePayCalculator = new FuturePayCalculator();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, futurePayCalculator)
                        .commit();
                break;



        }
    }


    //return from DatePickerDialogFragment
    @Override
    public void onFragmentFinish(Date date, String tvPosition) {

        TextView tvTarget;

        switch (tvPosition){
            case "DUE_DATE":
                Log.i(TAG, "AddAccount from DatePickerDialog, DUE_DATE: " + tvPosition);
                tvTarget = (TextView) findViewById(R.id.dueDateHolder);
                tvTarget.setText(DateFormat.getDateInstance().format(date));
                billDue = date;
                break;
            case "BILLED_DATE":
                Log.i(TAG, "AddAccount from DatePickerDialog, BILLED_DATE: " + tvPosition);
                tvTarget = (TextView) findViewById(R.id.dateBilledHolder);
                tvTarget.setText(DateFormat.getDateInstance().format(date));
                billedOnDate = date;
                break;
            case "DISPENSE_DATE":
                tvTarget = (TextView) findViewById(R.id.dateHolder);
                tvTarget.setText(DateFormat.getDateInstance().format(date));
                billDue = date;
                break;
            case "PAY_DATE":
                Log.i(TAG, "AddAccount from DatePickerDialog, BILLED_DATE: " + tvPosition);
                tvTarget = (TextView) findViewById(R.id.payDateHolder);
                tvTarget.setText(DateFormat.getDateInstance().format(date));
                billDue = date;
                break;
            case "DEPOSIT_DATE":
                tvTarget = (TextView) findViewById(R.id.depositDateHolder);
                tvTarget.setText(DateFormat.getDateInstance().format(date));
                billedOnDate = date;
                break;
        }


        Log.i(TAG, "onFragmentFinish: DatePickerDate  date: " + date + "\nPosition: " + tvPosition);

    }


    //return args from NewBill to be passed to DatePickerDialogFragment
    @Override
    public void onDateButtonPressed(String tvLocation) {
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MESSAGE_KEY, tvLocation);

        final DialogFragment picker = new DatePickerFragment();
        picker.setArguments(args);
        picker.show(getSupportFragmentManager(), "DATE_PICKER");

        Log.i(TAG, "AddAccountActivity onDateButtonPressed: tvLocation = " + tvLocation);
    }

}
