package com.example.mrclean.moneymapper.Features;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AddAccountActivity;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.R;
import java.util.ArrayList;
import java.util.List;
import io.realm.OrderedRealmCollection;



public class AccountListFragment extends Fragment {

    private List<Account> CopiedAccountList = new ArrayList<>();
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
        CopiedAccountList = dataSource.getAllAccounts();
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

        //The next part is setting up the orientation of the RecyclerView
        LinearLayoutManager mlayout = new LinearLayoutManager(getActivity());
        mlayout.setOrientation(LinearLayoutManager.VERTICAL);
        accountRecyclerView.setLayoutManager(mlayout);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creates the old add account
                Intent addAccount = new Intent(getActivity(), AddAccountActivity.class);
                startActivity(addAccount);
            }
        });


        //passing the data along to the custom adapter
        accountAdapter = new AccountAdapter((OrderedRealmCollection)CopiedAccountList,true);
        accountRecyclerView.setAdapter(accountAdapter);

        return view;
    }
}