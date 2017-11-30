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
// * {@link ExpenseDetail.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link ExpenseDetail#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ExpenseDetail extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_NAME = "id_name";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG  = ExpenseDetail.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String name;
    private String mParam2;

    private OnDetailEdit mListener;

    private AccountRealmDataMethods dataSource;

    private Account account;

    public ExpenseDetail() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ExpenseDetail.
//     */
    // TODO: Rename and change types and number of parameters
//    public static ExpenseDetail newInstance(String param1, String param2) {
//        ExpenseDetail fragment = new ExpenseDetail();
//        Bundle args = new Bundle();
//        args.putString(ID_NAME, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ID_NAME);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dataSource = new AccountRealmDataMethods();

        dataSource.open();

        Log.i(TAG, "onCreate: this is the getarguement: " + name);


        //Account account = new Account();
        account = dataSource.getPrimaryKeyByName(name);

        Log.i(TAG, "onCreate: this is what the datsource has " + account.getAmount() );
        //dataSource.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_detail, container, false);


        TextView accountName;
        TextView accountType;
        TextView accountRegularity;
        TextView accountPriorty;
        TextView accountAmount;

        TextView accountDispenseDate;
        TextView accountBilledDate;


        accountName = (TextView) view.findViewById(R.id.expense_detail_name_holder);
        Log.i(TAG, "onCreateView account.getName: " +  account.getName());
        accountName.setText(account.getName());

        accountType = (TextView) view.findViewById(R.id.expense_detail_type_holder);
        accountType.setText(account.getType());


        accountAmount = (TextView) view.findViewById(R.id.expense_detail_amount_holder);
        accountAmount.setText(String.valueOf(account.getAmount()));


        accountRegularity = (TextView) view.findViewById(R.id.expense_detail_regularity_holder);
        accountRegularity.setText(account.getRegularity());

        accountPriorty = (TextView) view.findViewById(R.id.expense_detail_priorty_holder);
        accountPriorty.setText(account.getPriority());

        accountDispenseDate = (TextView) view.findViewById(R.id.expense_detail_dispensedate_holder);
        accountDispenseDate.setText(account.getDateDue().toString());


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
         //   mListener.onDetailEditClick(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailEdit) {
            mListener = (OnDetailEdit) context;
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
    public interface OnDetailEdit {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onDetailEditClick(String account_id,String type, Boolean isChanging);
    }
}
