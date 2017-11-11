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
import com.example.mrclean.moneymapper.Accounts.AccountDataProvider;
import com.example.mrclean.moneymapper.Accounts.AddAccountActivity;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;


public class AccountListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String MESSAGE_KEY = "something";

    private List<Account> Acc = AccountDataProvider.HCAccountList;
    private List<Account> CopiedAccountList = new ArrayList<>();
    private ArrayList<String> IDsFucked;
    private boolean yes = true;
    private RecyclerView accountRecyclerView;
    private AccountAdapter accountAdapter;
    private RecyclerView.LayoutManager mlayout;

    private AccountRealmDataMethods dataSource;

    private static final String TAG = AccountListFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Realm realmView;


    public AccountListFragment() {
        // Required empty public constructor
    }

    //***************************************************
    //Creating the fragment. setting up realm to extract data from it
    //
    //*************************************************
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


//***********************Setting up the RecyclerView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_account_list2, container, false);
        view.setTag(TAG);
        accountRecyclerView = (RecyclerView) view.findViewById(R.id.account_RView);
        Log.i(TAG, "Inside onCreateView");

        //The next part is setting up the oreintation of the recyclerview


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


        //passing the data along to the customadapter
        accountAdapter = new AccountAdapter((OrderedRealmCollection)CopiedAccountList,true);
        accountRecyclerView.setAdapter(accountAdapter);

        return view;
    }
}