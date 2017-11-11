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
import android.widget.TextView;
import com.example.mrclean.moneymapper.MainActivity;
import com.example.mrclean.moneymapper.R;


/**
 * Created by mrclean on 10/12/17.
 */

public class NewExpense extends Fragment implements AdapterView.OnItemSelectedListener{


    private static final String TAG = "NewExpense";
    private NewExpenseListener mListener;
    private DispenseDateListener dateListener;

    EditText textExpenseAmount;
    EditText textExpenseName;
    TextView tvDateHolder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //needed for callback method / listener
        //error handling for (this)fragment finish
        if (context instanceof NewBill.NewBillListener) {
            mListener = (NewExpense.NewExpenseListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NewBillListener");
        }

        //error handling for Date selection
        if (context instanceof DispenseDateListener) {
            dateListener = (DispenseDateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DispenseDateListener");
        }
    }

    public NewExpense(){
        //Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setup_expense, container, false);

        textExpenseName = (EditText) rootView.findViewById(R.id.expenseName);
        textExpenseAmount = (EditText) rootView.findViewById(R.id.expenseAmount);
        tvDateHolder = (TextView) rootView.findViewById(R.id.dateHolder);



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



        //sends datepicker request to AddAccountActivity
        Button despenseDate = (Button) rootView.findViewById(R.id.dispenseDate);
        despenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: dispenseDate button pressed");

                if (dateListener == null){
                    throw new AssertionError();
                }else {
                    final String dispenseDate = "DISPENSE_DATE";
                    dateListener.onDateButtonPressed(dispenseDate);
                    Log.i(TAG, "NewBill dueDate: button pressed and Listener activated ");
                }
            }
        });


        //done button that will package up user input to send to the AddAccountActivity
        Button finishedExpense = (Button) rootView.findViewById(R.id.finishedExpense);
        finishedExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = textExpenseName.getText().toString();
                double expenseAmount = Double.parseDouble(textExpenseAmount.getText().toString());
                //todo int Priority level needs to be passed
                String expensePriority = prioritySpinner.getSelectedItem().toString();
                String expenseRegularity = regularitySpinner.getSelectedItem().toString();

                ExpenseFinished(expenseName, expenseAmount, expensePriority, expenseRegularity);

                //logs output of current values at time of collections
                Log.i(TAG, "onNewExpenseDone: \nName: " + expenseName
                        + "\nAmount: " + expenseAmount
                        + "\nPriority: " + expensePriority
                        + "\nRegularity: " + expenseRegularity);

            }

            private void ExpenseFinished(String name, Double amount,
                                         String priority, String regularity) {
                if (mListener == null){
                    throw new AssertionError();
                }
                mListener.onExpenseFinish( name, amount, priority, regularity);

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

    //captures user input from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    //required by Spinner / onItemSelected
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //callback method
    public interface NewExpenseListener{
        void onExpenseFinish(String name, Double amount,
                             String priority, String regularity);
    }


    //sends account data to AddAccountActivity
    public interface DispenseDateListener{
        void onDateButtonPressed(String tvLocation);
    }

}
