package com.example.mrclean.moneymapper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.mrclean.moneymapper.Features.AccountAdapter;
import com.example.mrclean.moneymapper.Features.AccountListFragment;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity
        implements AccountAdapter.AccountAdapterLongListener, AccountAdapter.AccountAdapterShortListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    //public static final int SCHEMA_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_holder, AccListFrag)
                .commit();
    }


    @Override
    public void onLongClick(String name) {
        Log.i(TAG, "onLongClick: Listener Received: " + name);
    }

    @Override
    public void onShortClick(String name) {
        Log.i(TAG, "onShortClick: Listener Received: " + name);
    }
}

