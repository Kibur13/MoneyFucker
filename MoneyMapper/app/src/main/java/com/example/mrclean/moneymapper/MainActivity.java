package com.example.mrclean.moneymapper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;
import com.example.mrclean.moneymapper.Accounts.AddAccountActivity;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.Features.AccountAdapter;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //public static final int SCHEMA_VERSION = 1;

    private RecyclerView accountRecyclerView;
    public AccountRealmDataMethods dataSource;
    private AccountAdapter accountAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Realm setup
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("account.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);

        //instantiates realm methods and opens the database
        dataSource = new AccountRealmDataMethods();
        dataSource.open();

        //sets up the RecyclerView for Realm db
        accountRecyclerView = (RecyclerView) findViewById(R.id.account_RView);
        setupRecyclerView();


        //FAB goes to adding a new Account
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addAccount = new Intent(MainActivity.this, AddAccountActivity.class);
                startActivity(addAccount);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();

        for (Account account : AccountDataProvider.HCAccountList)
        {
            dataSource.createAccount(account);
        }

        List<Account> allAccounts = dataSource.getAllAccounts();

        for(Account account : allAccounts)
        {
            Log.i(TAG, "account : " + account);
        }

    }

    @Override
    protected void onDestroy() {
        dataSource.close();
        super.onDestroy();
    }

    private void setupRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        accountRecyclerView.setLayoutManager(layoutManager);

        accountRecyclerView.setHasFixedSize(true);

        accountAdapter = new AccountAdapter((OrderedRealmCollection<Account>)
                dataSource.getAllAccounts(),true);
        accountRecyclerView.setAdapter(accountAdapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }






}

