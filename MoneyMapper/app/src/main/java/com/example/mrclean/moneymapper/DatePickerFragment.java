package com.example.mrclean.moneymapper;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mrclean on 10/12/17.
 */

public class DatePickerFragment extends DialogFragment implements OnDateSetListener{

    private static final String TAG = "DatePickerFragment";

    //constant for Bundle arguments
    public static final String MESSAGE_KEY = "message_key";
    public static String tvPosition;

    //for on date set interface
    private DateSetListener mListener;
    //private Date setDateDue;



    //listener to send date back to the calling method


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DateSetListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Bundle args = getArguments();
        if (args != null){
            tvPosition = args.getString(MESSAGE_KEY);
        }


// Create a new instance of DatePickerFragment and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

        @Override
        public void onDateSet (DatePicker view,int year, int month, int dayOfMonth){
            Calendar c = Calendar.getInstance();
            c.set(year, month, dayOfMonth);
            DateSet(c.getTime());
            Log.i(TAG, "onDateSet: date collected");
        }

        private void DateSet(Date date){
            if (mListener == null){
                throw new AssertionError();
            }
            mListener.onFragmentFinish(date, tvPosition);
    }

        public interface DateSetListener {
            void onFragmentFinish (Date date, String tvPosition);
        }

}
