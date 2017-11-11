package com.example.mrclean.moneymapper.Accounts.AccountCreation;

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
        View rootView = inflater.inflate(R.layout.account_type_selection, container, false);

        //listens for Bill press
        Button btnBill = (Button) rootView.findViewById(R.id.newBill);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountType = 1;
                mListener.onBillOrExpenseChosen(accountType);
                Log.i(TAG, "btnBill onClick: new bill selected");
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
                Log.i(TAG, "btnExpense onClick: new expense selected");
                dismiss();
            }
        });


        //listens for Income press
        Button btnIncome = (Button) rootView.findViewById(R.id.newIncome);
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountType = 3;
                mListener.onBillOrExpenseChosen(accountType);
                Log.i(TAG, "btnIncome onClick: new income selected");
                dismiss();
            }
        });


        //listens for Income press
        Button btnFuturePay = (Button) rootView.findViewById(R.id.futurePay);
        btnFuturePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountType = 4;
                mListener.onBillOrExpenseChosen(accountType);
                Log.i(TAG, "btnFuturePay onClick: Future Pay Calculator selected");
                dismiss();
            }
        });

        return rootView;
    }

    //sends data back to MainActivity
    public interface BillOrExpenseListener{
        void onBillOrExpenseChosen(int accountType);
    }

}
