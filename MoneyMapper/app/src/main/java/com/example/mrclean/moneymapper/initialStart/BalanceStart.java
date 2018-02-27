package com.example.mrclean.moneymapper.initialStart;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mrclean.moneymapper.SharedPreferencesHelper;
import com.example.mrclean.moneymapper.R;


public class BalanceStart extends DialogFragment {

    private static final String TAG = "BalanceStart";

    private BalanceStartListener mListener;


    //initializes Listener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //error handling for Listener
        if (context instanceof BalanceStartListener) {
            mListener = (BalanceStartListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BalanceStartListener");
        }
    }


    //sets up Dialog and collects information, sets Balance, passes back to BaseStart
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.balance_start, container, false);

        final EditText etCurrentBalance = (EditText) rootView.findViewById(R.id.etCurrrentBalance);

        Button btnBalanceDone = (Button) rootView.findViewById(R.id.btnBalanceDone);
        btnBalanceDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentBalance = Double.parseDouble(etCurrentBalance.getText().toString());

                new SharedPreferencesHelper(rootView.getContext()).SetBalance(currentBalance);

                Log.i(TAG, "onClick: btnBalanceDone, currentBalance = " + currentBalance);

                mListener.onBalanceStartedDone();
            }
        });

        return rootView;
    }


    //lets BaseStart Dialog is finished
    public interface BalanceStartListener{
        void onBalanceStartedDone();
    }

}
