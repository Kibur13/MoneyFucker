package com.example.mrclean.moneymapper.Features.Details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AccountCreation.NewBill;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.Features.DetailFragment;
import com.example.mrclean.moneymapper.R;


public class BillDetail extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_NAME = "id_name";
    private static final String ARG_PARAM2 = "param2";
    TextView accountName;
    TextView accountType;
    TextView accountRegularity;
    TextView accountPriorty;

    TextView accountDueDate;
    TextView accountBilledDate;

    private static final String TAG = BillDetail.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String id_accountName;
    private String mParam2;

    private AccountRealmDataMethods dataSource;

    private OnDetailEdit bListener;

    private Account account;

    public BillDetail() {
        // Required empty public constructor
    }


//    public static BillDetail newInstance(String param1, String param2) {
//        BillDetail fragment = new BillDetail();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            id_accountName = getArguments().getString(ID_NAME,"");
            //mParam2 = getArguments().getString(ARG_PARAM2);
            //Log.i(TAG, "Get arguements is not null!!  " + id_accountName + " and the other shit" + mParam2);
        }

        dataSource = new AccountRealmDataMethods();

        dataSource.open();

        Log.i(TAG, "onCreate: this is the getarguement: " +id_accountName);


        //Account account = new Account();
       account = dataSource.getPrimaryKeyByName(id_accountName);

        Log.i(TAG, "onCreate: this is what the datsource has " + account.getAmount() );
        //dataSource.close();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill_detail, container, false);


        TextView accountWithdraw;
        TextView accountChanges;
        TextView accountStatus;

        accountName = (TextView) view.findViewById(R.id.bill_detail_name_holder);
        Log.i(TAG, "onCreateView account.getName: " +  account.getName());
        accountName.setText(account.getName());

        accountType = (TextView) view.findViewById(R.id.bill_detail_type_holder);
        accountType.setText(account.getType());

        accountRegularity = (TextView) view.findViewById(R.id.bill_detail_regularity_holder);
        accountRegularity.setText(account.getRegularity());

        accountPriorty = (TextView) view.findViewById(R.id.bill_detail_priorty_holder);
        accountPriorty.setText(account.getPriority());

        accountDueDate = (TextView) view.findViewById(R.id.bill_detail_duedate_holder);
        accountDueDate.setText(account.getDateDue().toString());

        accountBilledDate = (TextView) view.findViewById(R.id.bill_detail_billon_holder);
        accountBilledDate.setText(account.getBilledOnDate().toString());

        Button EditBill = (Button) view.findViewById(R.id.editBillButton);
        EditBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creates the old add account

//                Bundle bun = new Bundle();
//                bun.putString(ID_NAME, account.getName());
//                final NewBill newBill = new NewBill();
//                newBill.setArguments(bun);
//                getFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_holder, newBill)
//                        .addToBackStack(null)
//                        .commit();

                bListener.onDetailEditClick(account.getName(),account.getType(), true);



//                Intent editingBill = new Intent(getActivity(), NewBill.class);
//                editingBill.putExtra(ID_NAME,account.getName());
//                startActivity(editingBill);
            }
        });



        return view;


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bill_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (bListener != null) {
            bListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailEdit) {
            bListener = (OnDetailEdit) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bListener = null;
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
