package com.example.mrclean.moneymapper;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.mrclean.moneymapper.Accounts.AccountAdapter;
import com.example.mrclean.moneymapper.Accounts.AccountListFragment;
import com.example.mrclean.moneymapper.Transactions.AddTransaction;
import com.example.mrclean.moneymapper.Transactions.DeleteTransaction;
import com.example.mrclean.moneymapper.Transactions.TransactionAdapter;
import com.example.mrclean.moneymapper.Transactions.TransactionListFragment;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.text.DateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity
        implements AccountAdapter.AccountAdapterLongListener,
        AccountAdapter.AccountAdapterShortListener, AddTransaction.TransactionDateListener,
        DatePickerFragment.DateSetListener, TransactionAdapter.TransactionAdapterShortListener,
        TransactionAdapter.TransactionAdapterLongListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    //used to track the version number of the app
    private static final int mmVersionNumber = 1;

    //needed to pass information between Transaction methods (RecyclerView and Add)
    public static Date dateTransaction;

    //public static final int SCHEMA_VERSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // TODO: 11/29/17 is giving errors but seems to be working
        //chrome://inspect stetho setup for realm
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        //Realm Account setup
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("account.realm")
                .deleteRealmIfMigrationNeeded()
                .build();


        Realm.setDefaultConfiguration(config);

        //initializes SharedPreferencesHelper
        new SharedPreferencesHelper(this).StartSharedPreferences();

        //checks the version number of the app
        new SharedPreferencesHelper(this).VersionCheck(mmVersionNumber);

        AccountListFragment AccListFrag = new AccountListFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_holder, AccListFrag)
                    .commit();

    }



    //Long Listener from Account RecyclerView
    @Override
    public void onLongClick(String name) {
        Log.i(TAG, "onLongClick: Listener Received: " + name);
    }


    //Short Listener from Account RecyclerView
    @Override
    public void onShortClick(String name) {
        Log.i(TAG, "onShortClick: Listener Received: " + name);


        //sends the NAME from Realm to the Transaction Fragment
        Bundle args = new Bundle();
        args.putString(TransactionListFragment.MESSAGE_KEY, name);

        final TransactionListFragment transactionListFragment = new TransactionListFragment();
        transactionListFragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack("MAIN_ACTIVITY")
                .replace(R.id.fragment_holder, transactionListFragment)
                .commit();

        Log.i(TAG, "onShortClick: Sending Name to TransactionListFragment: " + name);
    }


    //Listener that launches the DatePickerFragment for AddTransaction
    @Override
    public void onDateButtonPressed(String tvLocation) {
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MESSAGE_KEY, tvLocation);

        final DialogFragment picker = new DatePickerFragment();
        picker.setArguments(args);
        picker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    //Listener that receives the the Date from the DatePicker Fragment, includes who sent request
    @Override
    public void onFragmentFinish(Date date, String tvPosition) {
        TextView tvTarget;

        switch (tvPosition){
            case "TRANSACTION_DATE":
                tvTarget = (TextView) findViewById(R.id.transactionDateHolder);
                tvTarget.setText(DateFormat.getDateInstance().format(date));
                dateTransaction = date;
                break;
        }

        Log.i(TAG, "onFragmentFinish: DatePickerDate  date: " + date + "\nPosition: " + tvPosition);

    }


    //passes along the name of the Realm object and the transaction to be edited to AddTransaction
    @Override
    public void onTransactionShortClick(String accountName, String transactionID) {
        Bundle args = new Bundle();
        args.putString(AddTransaction.ACCOUNT_MESSAGE_KEY, accountName);
        args.putString(AddTransaction.TRANSACTION_MESSAGE_KEY, transactionID);

        final AddTransaction addTransaction = new AddTransaction();
        addTransaction.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack("TRANSACTION_LIST_FRAGMENT")
                .replace(R.id.fragment_holder, addTransaction)
                .commit();

    }


    @Override
    public void onTransactionLongClick(String accountName, String transactionID) {

        Bundle args = new Bundle();
        args.putString(DeleteTransaction.ACCOUNT_MESSAGE_KEY, accountName);
        args.putString(DeleteTransaction.TRANSACTION_MESSAGE_KEY, transactionID);

        final DeleteTransaction deleteTransaction = new DeleteTransaction();
        deleteTransaction.setArguments(args);
        deleteTransaction.show(getFragmentManager(), "DELETE_TRANSACTION");

    }
}

