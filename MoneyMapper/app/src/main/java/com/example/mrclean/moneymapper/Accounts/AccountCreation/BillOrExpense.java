package com.example.mrclean.moneymapper.Accounts.AccountCreation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mrclean.moneymapper.R;

/**
 * This is the first of a series of Dialogs that will assist the user in setting up a new Account
 * Created by mrclean on 10/11/17.
 */

public class BillOrExpense extends DialogFragment {
    private static final String TAG = "BillOrExpense";
    private BillOrExpenseListener mListener;
    public int accountType = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (BillOrExpenseListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.billable_or_expense, container, false);


        //listens for Bill press
        Button btnBill = (Button) rootView.findViewById(R.id.newBill);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountType = 1;
                mListener.onBillOrExpenseChosen(accountType);
                Log.i(TAG, "onClick: new bill selected");
                dismiss();
            }
        });



        //listens for Expense press
        Button btnExpense = (Button) rootView.findViewById(R.id.newExpense);
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountType = 2;
                mListener.onBillOrExpenseChosen(accountType);
                Log.i(TAG, "onClick: new expense selected");
                dismiss();
            }
        });


        return rootView;
    }

    public interface BillOrExpenseListener{
        void onBillOrExpenseChosen(int accountType);
    }

}
