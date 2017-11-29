package com.example.mrclean.moneymapper.Transactions;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mrclean.moneymapper.R;


public class AddTransaction extends Fragment {
    private static final String TAG = "AddTransaction";

    public static final String MESSAGE_KEY = "add_transaction_key";

    private AddTransactionListener mListener;
    private TransactionDateListener dateListener;

    EditText textTransactionAmount;
    EditText textTransactionReason;
    TextView tvTransactionDateHolder;

    String transaction;


//listeners for date and end of adding a transaction
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //needed for callback method / listener
        //error handling for (this)fragment finish
        if (context instanceof AddTransactionListener) {
            mListener = (AddTransactionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddTransactionListener");
        }

        //error handling for Date selection
        if (context instanceof TransactionDateListener) {
            dateListener = (TransactionDateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DispenseDateListener");
        }
    }

    public AddTransaction(){
        //Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_transaction, container, false);

        Bundle args = getArguments();
        if (args != null){
            transaction = args.getString(MESSAGE_KEY);
            Log.i(TAG, "onCreateView: message_key string: " + transaction);
        }

        textTransactionAmount = (EditText) rootView.findViewById(R.id.transactionAmount);
        textTransactionReason = (EditText) rootView.findViewById(R.id.transactionReason);
        tvTransactionDateHolder = (TextView) rootView.findViewById(R.id.transactionDateHolder);


        //sends datepicker request to MainActivity
        final Button bnTransactionDate = (Button) rootView.findViewById(R.id.bnTransactionDate);
        bnTransactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: bnTransactionDate button pressed");

                if (dateListener == null){
                    throw new AssertionError();
                }else {
                    final String transactionDate = "TRANSACTION_DATE";
                    dateListener.onDateButtonPressed(transactionDate);
                    Log.i(TAG, "Add Transaction onClick:  Date button being pressed");
                }
            }
        });


        // TODO: 11/22/17 make this to be adding a new transaction instead of adding a new account
        //done button that will package up user input to send to the AddAccountActivity
        Button expenseDone = (Button) rootView.findViewById(R.id.finishedTransaction);
        expenseDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactionReason = textTransactionReason.getText().toString();
                double transactionAmount = Double.parseDouble(textTransactionAmount.getText().toString());

                TransactionFinished(transactionAmount, transactionReason);

                //logs output of current values at time of collections
                Log.i(TAG, "AddTransaction on Click Done: " +
                        "\nAmount: " + transactionAmount
                        + "\nReason:" + transactionReason);

            }

            private void TransactionFinished(Double amount, String reason) {
                if (mListener == null){
                    throw new AssertionError();
                }
                mListener.onAddTransactionFinish(transaction, amount, reason);

                Log.i(TAG, "TransactionFinished: transactionName: " + transaction);


                //sends the NAME from Realm to the Transaction Fragment
                final TransactionListFragment transactionListFragment = new TransactionListFragment();
                Bundle args = new Bundle();
                args.putString(TransactionListFragment.MESSAGE_KEY, transaction);

                transactionListFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_holder, transactionListFragment)
                        .commit();
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //callback method to MainActivity
    public interface AddTransactionListener{
        void onAddTransactionFinish(String name, Double amount, String reason);
    }


    //sends transaction date to MainActivity
    public interface TransactionDateListener{
        void onDateButtonPressed(String tvLocation);
    }

}
