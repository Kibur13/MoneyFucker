package com.example.mrclean.moneymapper.Features.Details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.R;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link IncomeDetail.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link IncomeDetail#newInstance} factory method to
// * create an instance of this fragment.
// */
public class IncomeDetail extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_NAME = "id_name";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG= IncomeDetail.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String id_Name;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private AccountRealmDataMethods dataSource;

    private Account account;

    public IncomeDetail() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment IncomeDetail.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static IncomeDetail newInstance(String param1, String param2) {
//        IncomeDetail fragment = new IncomeDetail();
//        Bundle args = new Bundle();
//        args.putString(ID_NAME,param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_Name = getArguments().getString(ID_NAME);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }


        dataSource = new AccountRealmDataMethods();

        dataSource.open();

        Log.i(TAG, "onCreate: this is the getarguement (income) : " + id_Name);


        //Account account = new Account();
        account = dataSource.getPrimaryKeyByName(id_Name);

        Log.i(TAG, "onCreate: this is what the datasource has (income) " + account.getAmount() );
        //dataSource.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_income_detail, container, false);


        TextView accountName;
        TextView accountRegularity;
        TextView accountDirectDeposit;
        TextView accountAmount;
        TextView accountStatus;


        TextView accountChanges;


        accountName = (TextView) view.findViewById(R.id.income_detail_name_holder);
        Log.i(TAG, "onCreateView account.getName: " +  account.getName());
        accountName.setText(account.getName());


        accountAmount = (TextView) view.findViewById(R.id.income_detail_amount_holder);
        accountAmount.setText(String.valueOf(account.getAmount()));


        accountRegularity = (TextView) view.findViewById(R.id.income_detail_regularity_holder);
        accountRegularity.setText(account.getRegularity());

        accountDirectDeposit = (TextView) view.findViewById(R.id.income_detail_directdeposit_holder);
        accountDirectDeposit.setText(String.valueOf(account.isAutoWithdrawl()));

        accountStatus = (TextView) view.findViewById(R.id.income_detail_status_holder);
        accountStatus.setText(String.valueOf(account.isPaymentStatus()));

        accountChanges = (TextView) view.findViewById(R.id.income_detail_changes_holder);
        accountChanges.setText(String.valueOf(account.isAmountChanges()));



        return view;




    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
