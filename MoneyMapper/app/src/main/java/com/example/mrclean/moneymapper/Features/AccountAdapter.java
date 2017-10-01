package com.example.mrclean.moneymapper.Features;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

        public ViewHolder (View v)
        {
            super (v);
                    accountName = (TextView) v.findViewById(R.id.addAccountName);
                    accountDate = (TextView) v.findViewById(R.id.accountDate);
                    accountAmount = (TextView) v.findViewById(R.id.accountAmount);
                    accountType = (TextView) v.findViewById(R.id.accountType);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Account account = getItem( position);
        holder.accountName.setText(account.getName());
        holder.accountDate.setText(account.getDate());
        holder.accountType.setText(account.getType());
        holder.accountAmount.setText(String.valueOf(account.getAmount()));




        //super.onBindViewHolder(holder, position, payloads);
    }
}
