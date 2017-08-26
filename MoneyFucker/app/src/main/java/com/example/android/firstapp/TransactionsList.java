package com.example.android.firstapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
   //     extends AppCompatActivity
public class TransactionsList  extends Activity {


    private ListView TransListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /*
        String[] chores = {"rent", "water","MAgic cards","Open house","Cookies"};
        ListAdapter simpleadapt = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,chores);
        ListView ChoresLV = (ListView)findViewById(R.id.listView1);
        ChoresLV.setAdapter(simpleadapt);
        */


        Transactions Transact_items[]=new Transactions[]
                {
                        new Transactions("rent", 700),
                        new Transactions("Water", 120),
                        new Transactions("gas-car",150),
                        new Transactions("food",200),
                        new Transactions("other",500)

                };


        TransactionAdapter adapter = new TransactionAdapter(this,
                R.layout.listview_item_row,Transact_items);

        TransListView = (ListView)findViewById(R.id.listView1);

       View header =
                (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        TransListView.addHeaderView(header);
        TransListView.setAdapter(adapter);


    }
}
