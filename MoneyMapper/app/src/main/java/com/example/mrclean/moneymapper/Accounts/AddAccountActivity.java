package com.example.mrclean.moneymapper.Accounts;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import com.example.mrclean.moneymapper.Accounts.AccountCreation.BillOrExpense;
import com.example.mrclean.moneymapper.Accounts.AccountCreation.FuturePayCalculator;
import com.example.mrclean.moneymapper.Accounts.AccountCreation.NewBill;
import com.example.mrclean.moneymapper.Accounts.AccountCreation.NewExpense;
import com.example.mrclean.moneymapper.Accounts.AccountCreation.NewIncome;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.DatePickerFragment;
import com.example.mrclean.moneymapper.R;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import io.realm.Realm;

public class AddAccountActivity extends AppCompatActivity
        implements BillOrExpense.BillOrExpenseListener, NewExpense.NewExpenseListener,
        NewBill.NewBillListener, DatePickerFragment.DateSetListener, NewBill.NewBillDateListener,
        NewExpense.DispenseDateListener, NewIncome.NewIncomeDateListener, NewIncome.NewIncomeListener{

    private AccountRealmDataMethods Adding;
    private static final String TAG = "AddAccountActivity";

    Date billedOnDate;
    Date billDue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Realm init
        Realm.init(this);
        Adding = new AccountRealmDataMethods();

        BillOrExpense billOrExpense = new BillOrExpense();
        billOrExpense.show(getFragmentManager(), "BILL_OR_EXPENSE");



        //moves back to the previous page
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    //opens account db
    @Override
    protected void onResume(){
        super.onResume();
        Adding.open();

    }

    //closes account db
    @Override
    protected void onPause(){
        super.onPause();
        Adding.close();

    }


    //Listener for new account type (BillorExpense) and chooses the correct fragment to use
    @Override
    public void onBillOrExpenseChosen(int accountType) {
        switch (accountType){

            case 1:
                Log.i(TAG, "onBillOrExpenseChosen: Bill Chosen" + accountType);
                //todo add the new Bill once the class is created
                NewBill bill = new NewBill();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, bill)
                        .commit();
                break;
            case 2:
                Log.i(TAG, "onBillOrExpenseChosen: Expense Chosen");
                NewExpense expense = new NewExpense();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, expense)
                        .commit();
                break;
            case 3:
                Log.i(TAG, "onBillOrExpenseChosen: Income Chosen");
                NewIncome income = new NewIncome();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, income)
                        .commit();
                break;
            case 4:
                Log.i(TAG, "onBillOrExpenseChosen: FuturePay Chosen");
                FuturePayCalculator futurePayCalculator = new FuturePayCalculator();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_account_container, futurePayCalculator)
                        .commit();
                break;



        }
    }


    //Listener for when the user finishes creating the Expense
    @Override
    public void onExpenseFinish(String name, Double amount, String priority,
                                String regularity) {

        String type = "Expense";
        Date dateBilled = null;
        boolean autoWithdraw = true;
        boolean amountChanges = false;
        boolean paymentStatus = true;

        Account account = new Account(name, type, dateBilled, billDue, amount, priority, regularity,
                autoWithdraw, amountChanges, paymentStatus);
        Adding.createAccount(account);

        Log.i(TAG, "onExpenseFinish: user account added to realm");
    }


    //when the NewBill Fragment's "Done" button is pressed
    @Override
    public void onBillFinish(String name, double amount,
                             String priority, String regularity, String autoWithDraw,
                             String changes, String status) {

        boolean nAutoWithDraw, nChanges, nStatus;

        String type = "Bill";
        nAutoWithDraw = Objects.equals(autoWithDraw, "Yes");
        nChanges = Objects.equals(changes, "Yes");
        nStatus = Objects.equals(status, "Yes");



        Account account = new Account(name, type, billedOnDate, billDue, amount, priority, regularity,
                nAutoWithDraw, nChanges, nStatus);
        Adding.createAccount(account);


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

        final DatePickerFragment picker = new DatePickerFragment();
        picker.setArguments(args);
        picker.show(getFragmentManager(), "DATE_PICKER");

        Log.i(TAG, "AddAccountActivity onDateButtonPressed: tvLocation = " + tvLocation);
    }

    @Override
    public void onIncomeFinish(String name, double amount, String regularity, String autoWithDraw,
                               String changes, String status) {

        boolean nAutoWithDraw, nChanges, nStatus;

        String priority = "High";
        String type = "Income";
        nAutoWithDraw = Objects.equals(autoWithDraw, "Yes");
        nChanges = Objects.equals(changes, "Yes");
        nStatus = Objects.equals(status, "Yes");



        Account account = new Account(name, type, billedOnDate, billDue, amount, priority, regularity,
                nAutoWithDraw, nChanges, nStatus);

        Adding.createAccount(account);


    }
}
