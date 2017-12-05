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

import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;

import java.text.DateFormat;
import java.util.Date;


public class AddTransaction extends Fragment {
    private static final String TAG = "AddTransaction";

    public static final String ACCOUNT_MESSAGE_KEY = "add_account_key";
    public static final String TRANSACTION_MESSAGE_KEY = "add_transaction_key";

    private TransactionDateListener dateListener;

    private AccountRealmDataMethods dataSource = new AccountRealmDataMethods();

    EditText textTransactionAmount;
    EditText textTransactionReason;
    TextView tvTransactionDateHolder;

    String accountName;
    String transactionID = null;


    //opens Realm and has listeners for date and end of adding a transaction
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dataSource.open();

        //error handling for Date selection
        if (context instanceof TransactionDateListener) {
            dateListener = (TransactionDateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DispenseDateListener");
        }
    }


    //required
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
            accountName = args.getString(ACCOUNT_MESSAGE_KEY);
            transactionID = args.getString(TRANSACTION_MESSAGE_KEY);

            Log.i(TAG, "onCreateView: account_message_key string: " + accountName
                    + "\ntransactionID = " + transactionID);
        }

        textTransactionAmount = (EditText) rootView.findViewById(R.id.transactionAmount);
        textTransactionReason = (EditText) rootView.findViewById(R.id.transactionReason);
        tvTransactionDateHolder = (TextView) rootView.findViewById(R.id.transactionDateHolder);

        
        //If this is coming from the TransactionShortClickListener to edit, populates fields
        if (transactionID != null){
           Transaction editTransaction;
           editTransaction = dataSource.getTransaction(accountName, transactionID);
           Log.i(TAG, "onCreateView: amount = $" + editTransaction.getAmount());

           textTransactionAmount.setText(String.valueOf(editTransaction.getAmount()));
           textTransactionReason.setText(editTransaction.getReason());
           tvTransactionDateHolder.setText(DateFormat.getDateInstance().format(editTransaction.getDate()));
        }


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


        //done button that will package up user input to send to the AddAccountActivity
        Button expenseDone = (Button) rootView.findViewById(R.id.finishedTransaction);
        expenseDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactionReason = textTransactionReason.getText().toString();
                double transactionAmount = Double.parseDouble(textTransactionAmount.getText().toString());

                TransactionFinished(transactionAmount, transactionReason);
            }

            //adds or edits Transaction and passes it to Realm
            private void TransactionFinished(Double amount, String reason) {

                //checks to see if it is a new Transaction or is an edited one
                if (transactionID != null){
                    dataSource.editTransaction(transactionID,
                                                MainActivity.dateTransaction, amount, reason);
                }else {
                    dataSource.addTransaction(accountName, MainActivity.dateTransaction, amount, reason);
                }

                Log.i(TAG, "TransactionFinished: transactionName: " + accountName);

                //sends the NAME from Realm to the Transaction Fragment
                final TransactionListFragment transactionListFragment = new TransactionListFragment();
                Bundle args = new Bundle();
                args.putString(TransactionListFragment.MESSAGE_KEY, accountName);

                transactionListFragment.setArguments(args);
                getFragmentManager().popBackStackImmediate();
            }
        });

        return rootView;
    }


    //sets Listeners to null and closes Realm
    @Override
    public void onDetach() {
        super.onDetach();
        dataSource.close();
        dateListener = null;
    }
    

    //sends transaction date to MainActivity
    public interface TransactionDateListener{
        void onDateButtonPressed(String tvLocation);
    }

}
