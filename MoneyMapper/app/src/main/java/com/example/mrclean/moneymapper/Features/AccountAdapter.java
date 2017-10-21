package com.example.mrclean.moneymapper.Features;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SupportActivity.ExtraData;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mrclean.moneymapper.Accounts.AccountDetailFragment;
import com.example.mrclean.moneymapper.MainActivity;
import com.squareup.picasso.Picasso;


import com.example.mrclean.moneymapper.R;
import com.example.mrclean.moneymapper.Accounts.Account;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;




/**
 * Created by j2md7_000 on 9/24/2017.
 */

public class AccountAdapter extends RealmRecyclerViewAdapter<Account,AccountAdapter.ViewHolder>

{
    private static final String TAG = AccountAdapter.class.getSimpleName();

   private Context mContext;

    private static final String ACCOUNT_ID = "ID";
    public AccountAdapter(@Nullable OrderedRealmCollection<Account> data,boolean autoUpdate)
    {
        super(data,autoUpdate);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView accountName;
        TextView accountDate;
        TextView accountAmount;
        TextView accountType;
       public View mView;

        public ViewHolder (View v)
        {
            super (v);
                    accountName = (TextView) v.findViewById(R.id.addAccountName);
                    accountDate = (TextView) v.findViewById(R.id.accountDate);
                    accountAmount = (TextView) v.findViewById(R.id.accountAmount);
                    accountType = (TextView) v.findViewById(R.id.accountType);
                    mView = itemView;

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_account,parent,false);
        return new ViewHolder(v);
       // return null;



    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //final Context context;

        final Account account = getItem( position);
        if (account != null) {
            holder.accountName.setText(account.getName());
        }
        holder.accountDate.setText(account.getDate());
        holder.accountType.setText(account.getType());
        holder.accountAmount.setText(String.valueOf(account.getAmount()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log.i(TAG, "what happens at this point");


                                                int currentIDHolder = holder.accountName.getId();

                                                Log.i(TAG, "What I clicked on " + account.getName());

                                                FragmentManager manager = ((MainActivity)mContext).getSupportFragmentManager();

                                                AccountDetailFragment oldFrag = (AccountDetailFragment) manager.findFragmentById(currentIDHolder);
                                                if(oldFrag != null)
                                                {
                                                    manager.beginTransaction().remove(oldFrag).commit();
                                                }

                                                int NewAccountID = Integer.parseInt(account.getId());

                                                AccountDetailFragment newADF = new AccountDetailFragment();

                                                manager.beginTransaction().add(newADF,"ACCOUNT_ID").commit();



                                                //String AccID = account.getId();
//                                                AccountDetailFragment AccDetFrag =
//                                                        AccountDetailFragment.newInstance(account.getId());
//                                                FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();



                                                };


                                            });





        //super.onBindViewHolder(holder, position, payloads);
    }
}
