package com.example.mrclean.moneymapper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.Features.AccountAdapter3;
import com.example.mrclean.moneymapper.Features.AccountListFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity  {


    private List<Account> accounts = AccountDataProvider.accountList;
    List<Account> ListAccounts; //= new ArrayList<>();

    private ListView lv;

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView accountRecyclerView;
    public AccountRealmDataMethods dataSource;

    private AccountAdapter3 AccAdapt;


    public static final String ID_CRAP = "AccountIDShit";

    private List<String> ID_Numbers = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("account.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);



        dataSource = new AccountRealmDataMethods();

        dataSource.open();


        for (Account account : AccountDataProvider.HCAccountList)

        {
            dataSource.createAccount(account);
        }
        List<Account> allAccounts = dataSource.getAllAccounts();
        dataSource.close();


        AccountListFragment AccListFrag = new AccountListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_holder, AccListFrag)
                    .commit();


        Log.i(TAG, "It got to this Point!!!!!!!!");



        Log.i(TAG, "Now passed this point!!!!!!!!");


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent addAccount = new Intent(MainActivity.this, AddAccountActivity.class);
//                startActivity(addAccount);
//            }
//        });


//        FloatingActionButton DeleteFAB = (FloatingActionButton) findViewById(R.id.DeleteFAB);
//        DeleteFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dataSource.deleteEverything();
//                //Intent addAccount = new Intent(MainActivity.this, AddAccountActivity.class);
//                //startActivity(addAccount);
//            }
//        });



    }
    private static final String REALM_TAG = "__REALM___";

    public static Realm getRealm (Context context){
        return (Realm)context.getSystemService(REALM_TAG);

    }








}

