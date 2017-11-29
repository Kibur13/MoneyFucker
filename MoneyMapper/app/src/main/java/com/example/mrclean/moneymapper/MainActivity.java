package com.example.mrclean.moneymapper;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.mrclean.moneymapper.Accounts.AccountAdapter;
import com.example.mrclean.moneymapper.Accounts.AccountListFragment;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.Transactions.AddTransaction;
import com.example.mrclean.moneymapper.Transactions.Transaction;
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
        AddTransaction.AddTransactionListener, DatePickerFragment.DateSetListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    //needed to pass information between AddTransaction methods
    Date dateTransaction;

    private AccountRealmDataMethods dataSource;


    //public static final int SCHEMA_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // TODO: 11/29/17 currently isn't working and should be set back up
        //chrome://inspect stetho setup for realm
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        //Realm setup
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("account.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

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
                .addToBackStack("ACCOUNT_LIST_FRAGMENT")
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

    //adds the new Transaction to Realm received from AddTransaction
    @Override
    public void onAddTransactionFinish(String name, Double amount, String reason) {
        dataSource = new AccountRealmDataMethods();
        dataSource.open();
        dataSource.addTransaction(name, dateTransaction, amount, reason);
        dataSource.close();
    }
}

