package com.example.mrclean.moneymapper.Transactions;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;


public class TransactionListFragment extends Fragment {

    private static final String TAG = "TransactionListFragment";
    public static final String MESSAGE_KEY = "transaction_list_key";
    private List<Transaction> copiedTransactionList = new ArrayList<>();

    private String transaction;

    private AccountRealmDataMethods dataSource;



    public TransactionListFragment() {
        // Required empty public constructor
    }


    //Creating the fragment, setting up realm to extract data
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null){
            transaction = args.getString(MESSAGE_KEY);
        }

        Log.i(TAG, "onCreate: setting up Realm with Transaction Name: " + transaction);

        dataSource = new AccountRealmDataMethods();
        dataSource.open();

        copiedTransactionList = dataSource.getAllTransactions(transaction);

        dataSource.close();

    }


    //      Setting up the RecyclerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView transactionRecyclerView;

        View view = inflater.inflate(R.layout.fragment_account_list2, container, false);
        view.setTag(TAG);
        transactionRecyclerView = (RecyclerView) view.findViewById(R.id.account_RView);
        Log.i(TAG, "Inside onCreateView");

        //The next part is setting up the orientation of the RecyclerView
        LinearLayoutManager mLayout = new LinearLayoutManager(getActivity());
        mLayout.setOrientation(LinearLayoutManager.VERTICAL);
        transactionRecyclerView.setLayoutManager(mLayout);


        //take user to addTransaction fragment
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passes along the name of the Realm object
                Bundle args = new Bundle();
                args.putString(AddTransaction.MESSAGE_KEY, transaction);

                final AddTransaction addTransaction = new AddTransaction();
                addTransaction.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack("TRANSACTION_LIST_FRAGMENT")
                        .replace(R.id.fragment_holder, addTransaction)
                        .commit();
            }
        });


        //passing the data along to the custom adapter
        TransactionAdapter transactionAdapter = new TransactionAdapter((OrderedRealmCollection) copiedTransactionList, true);
        transactionRecyclerView.setAdapter(transactionAdapter);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }

}
