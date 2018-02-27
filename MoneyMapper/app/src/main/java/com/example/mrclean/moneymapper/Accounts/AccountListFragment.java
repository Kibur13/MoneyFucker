package com.example.mrclean.moneymapper.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrclean.moneymapper.SharedPreferencesHelper;
import com.example.mrclean.moneymapper.accountCreation.AddAccountActivity;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.R;
import java.util.ArrayList;
import java.util.List;
import io.realm.OrderedRealmCollection;



public class AccountListFragment extends android.app.Fragment {

    private List<Account> copiedAccountList = new ArrayList<>();
    private RecyclerView accountRecyclerView;
    private AccountAdapter accountAdapter;
    private AccountRealmDataMethods dataSource;
    private static final String TAG = AccountListFragment.class.getSimpleName();


    public AccountListFragment() {
        // Required empty public constructor
    }


    //Creating the fragment. setting up realm to extract data from it
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new AccountRealmDataMethods();

        dataSource.open();
        copiedAccountList = dataSource.getAllAccounts();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }


//      Setting up the RecyclerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account_list2, container, false);
        view.setTag(TAG);
        accountRecyclerView = (RecyclerView) view.findViewById(R.id.account_RView);
        Log.i(TAG, "Inside onCreateView");

        //gets the currentBalance from SharedPrefs and sets the textField accordingly
        TextView rvCurrentBalance = (TextView) view.findViewById(R.id.rvCurrentBalance);
        Double currentBalance;
        currentBalance = new SharedPreferencesHelper(view.getContext()).GetBalance();
        rvCurrentBalance.setText("Balance: $" + String.valueOf(currentBalance));

        //The next part is setting up the orientation of the RecyclerView
        LinearLayoutManager mlayout = new LinearLayoutManager(getActivity());
        mlayout.setOrientation(LinearLayoutManager.VERTICAL);
        accountRecyclerView.setLayoutManager(mlayout);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addAccount = new Intent(getActivity(), AddAccountActivity.class);
                startActivity(addAccount);
            }
        });


        //passing the data along to the custom adapter
        accountAdapter = new AccountAdapter((OrderedRealmCollection) copiedAccountList,true);
        accountRecyclerView.setAdapter(accountAdapter);

        return view;
    }
}