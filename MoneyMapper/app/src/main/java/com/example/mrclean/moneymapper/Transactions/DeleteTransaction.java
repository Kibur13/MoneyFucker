package com.example.mrclean.moneymapper.Transactions;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.R;


public class DeleteTransaction extends DialogFragment {

    private static final String TAG = "DeleteTransaction";
    private AccountRealmDataMethods dataSource = new AccountRealmDataMethods();

    public static final String ACCOUNT_MESSAGE_KEY = "delete_account_key";
    public static final String TRANSACTION_MESSAGE_KEY = "delete_transaction_key";

    Transaction deleteTransaction;
    String accountName;
    String transactionID;


    //opens Realm
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataSource.open();
    }


    public DeleteTransaction() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            accountName = args.getString(ACCOUNT_MESSAGE_KEY);
            transactionID = args.getString(TRANSACTION_MESSAGE_KEY);
        }


        deleteTransaction = dataSource.getTransaction(accountName, transactionID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_delete_transaction, container, false);

        Button btnDeleteTransaction = (Button) rootView.findViewById(R.id.deleteTransaction);
        btnDeleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.deleteTransaction(accountName, transactionID);
                dismiss();
            }
        });

        Button btnDoNotDeleteTransaction = (Button) rootView.findViewById(R.id.doNotDeleteTransaction);
        btnDoNotDeleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        dataSource.close();
    }
}
