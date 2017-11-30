package com.example.mrclean.moneymapper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

<<<<<<< HEAD
import com.example.mrclean.moneymapper.Accounts.AccountAdapter;
import com.example.mrclean.moneymapper.Accounts.AccountListFragment;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.Transactions.AddTransaction;
import com.example.mrclean.moneymapper.Transactions.Transaction;
import com.example.mrclean.moneymapper.Transactions.TransactionListFragment;
=======
import com.example.mrclean.moneymapper.Accounts.AccountCreation.AccountEditing;
import com.example.mrclean.moneymapper.Features.AccountAdapter;
import com.example.mrclean.moneymapper.Features.AccountListFragment;
import com.example.mrclean.moneymapper.Features.DetailFragment;
import com.example.mrclean.moneymapper.Features.Details.BillDetail;
import com.example.mrclean.moneymapper.Features.Details.ExpenseDetail;
import com.example.mrclean.moneymapper.Features.Details.IncomeDetail;
>>>>>>> JulTest
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.text.DateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity
<<<<<<< HEAD
        implements AccountAdapter.AccountAdapterLongListener,
        AccountAdapter.AccountAdapterShortListener, AddTransaction.TransactionDateListener,
        AddTransaction.AddTransactionListener, DatePickerFragment.DateSetListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    //needed to pass information between AddTransaction methods
    Date dateTransaction;

    private AccountRealmDataMethods dataSource;
=======
        implements AccountAdapter.AccountAdapterLongListener, AccountAdapter.AccountAdapterShortListener, DetailFragment.OnFragmentInteractionListener,
        BillDetail.OnDetailEdit ,ExpenseDetail.OnDetailEdit, IncomeDetail.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String ID_NAME = "id_name";
    private static final String ID_TYPE = "id_type";
>>>>>>> JulTest


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
    public void onLongClick(String name, String type) {
        Log.i(TAG, "onLongClick: Listener Received: " + name +" this is the type: "+type);

        Bundle detailName = new Bundle();
        detailName.putString(ID_NAME,name);
        switch (type){
            case "Bill":
                final BillDetail billDetail = new BillDetail();
                billDetail.setArguments(detailName);
                getFragmentManager()
                .beginTransaction()
                        .replace(R.id.fragment_holder, billDetail)
                        .addToBackStack(null)
                         .commit();
                break;


            case "Expense":
                ExpenseDetail expenseDetail = new ExpenseDetail();
                expenseDetail.setArguments(detailName);
               getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_holder,expenseDetail)
                       .addToBackStack(null)
                        .commit();
               break;


            case "Income":
                IncomeDetail incomeDetail = new IncomeDetail();
                incomeDetail.setArguments(detailName);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_holder,incomeDetail)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;



        }


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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDetailEditClick(String account_id, String accType, Boolean isChanging) {
        if (isChanging = true)
        {
            EditAccount(account_id,accType);
        }

    }
    public void EditAccount (String account_id,String accountType)
    {
            AccountEditing accountEditing = new AccountEditing(account_id, accountType);




    }


}

