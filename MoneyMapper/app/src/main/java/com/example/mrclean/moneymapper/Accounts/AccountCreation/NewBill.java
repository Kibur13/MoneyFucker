package com.example.mrclean.moneymapper.Accounts.AccountCreation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import android.widget.TextView;

import com.example.mrclean.moneymapper.DatePickerFragment;
import com.example.mrclean.moneymapper.R;

import java.text.DateFormat;
import java.util.Date;


public class NewBill extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String MESSAGE_KEY = "message_key";


    private static final String TAG = "NewBill";
    private NewBillListener mListener;
    private NewBillDateListener dateListener;

    EditText textBillAmount;
    EditText textBillName;
    //Holder for Date after user selects
    TextView tvDateHolder;



    public NewBill() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //error handling for when NewBill(this) fragment finish
        if (context instanceof NewBillListener) {
            mListener = (NewBillListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NewBillListener");
        }
        //error handling for Date selection
        if (context instanceof NewBillDateListener) {
            dateListener = (NewBillDateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NewBillDateListener");
        }
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setup_bill, container, false);

        textBillName = (EditText) rootView.findViewById(R.id.billName);
        textBillAmount = (EditText) rootView.findViewById(R.id.billAmount);
        //identifies textView for selected date to show to user
        tvDateHolder = (TextView) rootView.findViewById(R.id.dueDateHolder);



        //priority spinner setup
        final Spinner prioritySpinner = (Spinner) rootView.findViewById(R.id.prioritySpinner);
        //ArrayAdapter that brings together the string array and the spinner layout
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priorityLevel, android.R.layout.simple_spinner_dropdown_item);
        //layout to use when the list of choices appears
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        prioritySpinner.setAdapter(priorityAdapter);

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


        //// TODO: 10/30/17 current date selection setup
        //creates the datepickerdialog. allows it to know that this is the fragment that called it
//        final DialogFragment picker = new DatePickerFragment();
//        picker.setTargetFragment(this, 0);


        //starts date picker fragment
        Button dueDate = (Button) rootView.findViewById(R.id.dueDate);
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: dispenseDate button pressed");
                //DialogFragment picker = new DatePickerFragment();


                if (dateListener == null){
                    throw new AssertionError();
                }else {
                    final String dueDate = "DUE_DATE";
                    dateListener.onDateButtonPressed(dueDate);
                    Log.i(TAG, "NewBill dueDate: button pressed and Listener activated ");
                }

                //// TODO: 10/30/17 displays datepicker dialog fragment
                //picker.show(getFragmentManager(),"DUE_DATE_PICKER");
            }
        });

        Button billedOnDate = (Button) rootView.findViewById(R.id.billedOnDate);
        billedOnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: dispenseDate button pressed");
                //DialogFragment picker = new DatePickerFragment();


                if (dateListener == null){
                    throw new AssertionError();
                }else {
                    final String dueDate = "BILLED_DATE";
                    dateListener.onDateButtonPressed(dueDate);
                    Log.i(TAG, "NewBill dueDate: button pressed and Listener activated ");
                }
            }
        });


        //done button that will package up user input to send to the AddAccountActivity
        Button finishedBill = (Button) rootView.findViewById(R.id.finishedBill);
        finishedBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billName = textBillName.getText().toString();
                double billAmount = Double.parseDouble(textBillAmount.getText().toString());
                String billPriority = prioritySpinner.getSelectedItem().toString();
                String billRegularity = regularitySpinner.getSelectedItem().toString();
                String billWithdrawType = withDrawTypesSpinner.getSelectedItem().toString();
                String billChanges = changesSpinner.getSelectedItem().toString();
                String billStatus = statusSpinner.getSelectedItem().toString();

                //todo add second calendar and date
                ExpenseFinished(billName, billAmount, billPriority, billRegularity,
                        billWithdrawType, billChanges,billStatus);

                //logs output of current values at time of collections
                Log.i(TAG, "onNewBillDone: \nName: " + billName
                        + "\nAmount: " + billAmount
                        + "\nPriority: " + billPriority
                        + "\nRegularity: " + billRegularity);

            }

            private void ExpenseFinished(String name, Double amount,
                                         String priority, String regularity,
                                         String billWithdrawType, String changes, String status) {

                //todo add second calendar and date


                if (mListener == null){
                    throw new AssertionError();
                }

                mListener.onBillFinish( name, amount, priority, regularity,
                        billWithdrawType, changes, status);
            }
        });



        return rootView;
    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    //required by Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //// todo add second calendar and get the date



    public interface NewBillDateListener{
        void onDateButtonPressed(String tvLocation);
    }


    public interface NewBillListener {
        void onBillFinish(String name, double amount,
                              String priority, String regularity, String autoWithDraw,
                              String changes, String status);
    }
}
