package com.example.mrclean.moneymapper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;
import com.example.mrclean.moneymapper.Accounts.AccountListAdapter;
import com.example.mrclean.moneymapper.Accounts.AddAccountActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Account> accounts = AccountDataProvider.accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        AccountListAdapter adapter = new AccountListAdapter(
                this, R.layout.list_account,accounts);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);





    }

    //creates options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //populates menu with items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
