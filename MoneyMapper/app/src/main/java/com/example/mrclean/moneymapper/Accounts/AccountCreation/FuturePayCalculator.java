package com.example.mrclean.moneymapper.Accounts.AccountCreation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mrclean.moneymapper.Accounts.AddAccountActivity;
import com.example.mrclean.moneymapper.R;

import java.util.Locale;


public class FuturePayCalculator extends Fragment {
    private static final String TAG = "FuturePayCalculator";

    EditText etGrossPay;
    EditText etNetPay;
    EditText etHoursWorked;
    EditText etPayRate;
    TextView tvTaxPercentage;
    TextView tvEstimatedPay;

    public FuturePayCalculator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.future_pay_calculator, container, false);

        //places that will be collecting information from user
        etGrossPay = (EditText) rootView.findViewById(R.id.grossPay);
        etNetPay = (EditText) rootView.findViewById(R.id.netPay);
        etHoursWorked = (EditText) rootView.findViewById(R.id.hoursWorked);
        etPayRate = (EditText) rootView.findViewById(R.id.payRate);

        //text fields that will be changing when the calculations are done
        tvTaxPercentage = (TextView) rootView.findViewById(R.id.taxPercentage);
        tvEstimatedPay = (TextView) rootView.findViewById(R.id.estimatedPay);

        //takes the user back to the MainActivity
        Button futurePayBack = (Button) rootView.findViewById(R.id.futurePayBack);
        futurePayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 11/9/17 check to see if this actually works (getContext) might not be right
                Intent addAccount = new Intent(getContext(), AddAccountActivity.class);
                startActivity(addAccount);
            }
        });

        //triggers calculations
        Button calculateFuturePay = (Button) rootView.findViewById(R.id.futurePayCalc);
        calculateFuturePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double grossPay = Double.parseDouble(etGrossPay.getText().toString());
                double netPay = Double.parseDouble(etNetPay.getText().toString());
                double hoursWorked = Double.parseDouble(etHoursWorked.getText().toString());
                double payRate = Double.parseDouble(etPayRate.getText().toString());

                CalcPay(grossPay, netPay, hoursWorked,payRate);
            }
        });

        return rootView;
    }

    //Calculated everything needed to fill textViews
    public void CalcPay(double grossPay, double netPay, double hoursWorked, double payRate){
        double percentPaid;
        double taxRate;
        double calcPay;
        double estimatedFuturePay;

        //calculated effective tax rate
        percentPaid = (netPay / grossPay);
        taxRate = Math.round((1 - percentPaid) * 100);
        Log.i(TAG, "CalcPay: taxRate: " +taxRate);

        //takes tax rate and estimated gross pay to determine future gross pay
        calcPay = (hoursWorked * payRate);
        Log.i(TAG, "CalcPay: calcPay: " + calcPay);
        estimatedFuturePay = (calcPay * percentPaid);

        tvTaxPercentage.setText(String.format(Locale.ENGLISH, Double.toString(taxRate)));

        tvEstimatedPay.setText(Double.toString(estimatedFuturePay));
        Log.i(TAG, "CalcPay: estimatedFuturePay: " + estimatedFuturePay);
    }

}
