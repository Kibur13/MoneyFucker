package com.example.mrclean.moneymapper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.mrclean.moneymapper.Accounts.AccountCreation.AccountEditing;
import com.example.mrclean.moneymapper.Features.AccountAdapter;
import com.example.mrclean.moneymapper.Features.AccountListFragment;
import com.example.mrclean.moneymapper.Features.DetailFragment;
import com.example.mrclean.moneymapper.Features.Details.BillDetail;
import com.example.mrclean.moneymapper.Features.Details.ExpenseDetail;
import com.example.mrclean.moneymapper.Features.Details.IncomeDetail;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity
        implements AccountAdapter.AccountAdapterLongListener, AccountAdapter.AccountAdapterShortListener, DetailFragment.OnFragmentInteractionListener,
        BillDetail.OnDetailEdit ,ExpenseDetail.OnDetailEdit, IncomeDetail.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String ID_NAME = "id_name";
    private static final String ID_TYPE = "id_type";


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
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_holder, AccListFrag)
                .commit();
    }


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

    @Override
    public void onShortClick(String name) {
        Log.i(TAG, "onShortClick: Listener Received: " + name);
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

