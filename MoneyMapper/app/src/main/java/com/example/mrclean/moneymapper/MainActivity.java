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



public class MainActivity extends AppCompatActivity {


    private List<Account> accounts = AccountDataProvider.accountList;
    List<Account> ListAccounts; //= new ArrayList<>();
    private ListView lv;

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView accountRecyclerView;
    public AccountRealmDataMethods dataSource;
    private AccountAdapter accountAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Realm.init(this);

        accountRecyclerView = (RecyclerView) findViewById(R.id.account_RView);

        dataSource = new AccountRealmDataMethods();

        dataSource.open();



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



        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        AccountListAdapter adapter = new AccountListAdapter(
//                this, R.layout.list_account,accounts);
//        ListView lv = (ListView) findViewById(R.id.listView);
//        AccountingDB = new AccountDB(this,null, null,1);
//        lv.setAdapter(new ViewAdapter(AccountingDB.listfromdb()));


       /*
      lv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
              int id_To_Search = arg2+1;
              Bundle databundle = new Bundle();
              databundle.putInt("id",id_To_Search);
              Intent intent = new Intent(getApplicationContext(),AddAccountActivity);
              intent.putExtras(databundle);
              startActivity(intent);


          }
      });

      */

       // lv.setAdapter(adapter);





    }

    @Override
    protected void onResume() {
        super.onResume();

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

    //creates options menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    /*
    //populates menu with items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.item1:
                Bundle databundle = new Bundle();
                databundle.putInt("id",0);
                Intent intent = new Intent(getApplicationContext(),AddAccountActivity);
                intent.putExtras(databundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        /*

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    */


//    public boolean onKeyDown(int keycode, KeyEvent Event)
//    {
//        if(keycode == KeyEvent.KEYCODE_BACK)
//        {
//            moveTaskToBack(true);
//        }
//        return super.onKeyDown(keycode, Event);
//    }






}

