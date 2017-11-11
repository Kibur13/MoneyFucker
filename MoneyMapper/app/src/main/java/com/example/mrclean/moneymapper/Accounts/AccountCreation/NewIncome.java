package com.example.mrclean.moneymapper.Accounts.AccountCreation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;

/**
 * Created by mrclean on 11/6/17.
 */

public class NewIncome extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "NewIncome";
    private NewIncome.NewIncomeListener mListener;
    private NewIncome.NewIncomeDateListener dateListener;
    EditText textIncomeAmount;
    EditText textIncomeName;


    public NewIncome() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //error handling for when NewIncome(this) fragment finish
        if (context instanceof NewIncome.NewIncomeListener) {
            mListener = (NewIncome.NewIncomeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NewIncomeListener");
        }
        //error handling for Date selection
        if (context instanceof NewIncome.NewIncomeDateListener) {
            dateListener = (NewIncome.NewIncomeDateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NewIncomeDateListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setup_income, container, false);

        textIncomeName = (EditText) rootView.findViewById(R.id.incomeName);
        textIncomeAmount = (EditText) rootView.findViewById(R.id.incomeAmount);

        //regularity spinner setup
        final Spinner regularitySpinner = (Spinner) rootView.findViewById(R.id.regularitySpinner);
        ArrayAdapter<CharSequence> regularityAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.regularity, android.R.layout.simple_spinner_dropdown_item);
        regularitySpinner.setAdapter(regularityAdapter);

        //withdraw spinner setup
        final Spinner withDrawTypesSpinner = (Spinner) rootView.findViewById(R.id.withDrawTypeSpinner);
        ArrayAdapter<CharSequence> withDrawTypesAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.yesOrNo, android.R.layout.simple_spinner_dropdown_item);
        withDrawTypesSpinner.setAdapter(withDrawTypesAdapter);

        //changes spinner setup
        final Spinner changesSpinner = (Spinner) rootView.findViewById(R.id.changesSpinner);
        ArrayAdapter<CharSequence> changesAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.yesOrNo, android.R.layout.simple_spinner_dropdown_item);
        changesSpinner.setAdapter(changesAdapter);

        //status spinner setup
        final Spinner statusSpinner = (Spinner) rootView.findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.yesOrNo, android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);



        //starts date picker fragment
        Button payDate = (Button) rootView.findViewById(R.id.payDate);
        payDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: payDate button pressed");

                if (dateListener == null){
                    throw new AssertionError();
                }else {
                    final String payDate = "PAY_DATE";
                    dateListener.onDateButtonPressed(payDate);
                    Log.i(TAG, "NewIncome payDate: Listener activated ");
                }

            }
        });

        Button depositDate = (Button) rootView.findViewById(R.id.depositDate);
        depositDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: depositDate button pressed");

                if (dateListener == null){
                    throw new AssertionError();
                }else {
                    final String depositDate = "DEPOSIT_DATE";
                    dateListener.onDateButtonPressed(depositDate);
                    Log.i(TAG, "NewIncome depositDate: Listener activated ");
                }
            }
        });


        //done button that will package up user input to send to the AddAccountActivity
        Button finishedIncome = (Button) rootView.findViewById(R.id.finishedIncome);
        finishedIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String incomeName = textIncomeName.getText().toString();
                double incomeAmount = Double.parseDouble(textIncomeAmount.getText().toString());
                String incomeRegularity = regularitySpinner.getSelectedItem().toString();
                String incomeWithdrawType = withDrawTypesSpinner.getSelectedItem().toString();
                String incomeChanges = changesSpinner.getSelectedItem().toString();
                String incomeStatus = statusSpinner.getSelectedItem().toString();


                IncomeFinished(incomeName, incomeAmount, incomeRegularity,
                        incomeWithdrawType, incomeChanges, incomeStatus);

                //logs output of current values at time of collections
                Log.i(TAG, "onNewIncomeDone: \nName: " + incomeName
                        + "\nAmount: " + incomeAmount
                        + "\nRegularity: " + incomeRegularity);

            }

            private void IncomeFinished(String name, Double amount,
                                         String regularity,
                                         String billWithdrawType, String changes, String status) {

                if (mListener == null){
                    throw new AssertionError();
                }
                mListener.onIncomeFinish( name, amount, regularity,
                        billWithdrawType, changes, status);

                Intent backToMain = new Intent(getContext(), MainActivity.class);
                startActivity(backToMain);
            }
        });



        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //used for spinner, listens for which item was selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    //required by Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //sends position of DatePicker button
    public interface NewIncomeDateListener{
        void onDateButtonPressed(String tvLocation);
    }

    //sends account data to AddAccountActivity
    public interface NewIncomeListener {
        void onIncomeFinish(String name, double amount,
                            String regularity, String autoWithDraw,
                            String changes, String status);
    }


}
