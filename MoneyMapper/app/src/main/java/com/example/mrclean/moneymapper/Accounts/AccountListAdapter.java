package com.example.mrclean.moneymapper.Accounts;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mrclean.moneymapper.R;

import java.text.NumberFormat;
import java.util.List;

/**
 * Custom ArrayAdapter that allows the ArrayAdapter to handle the Account Data
 * Created by mrclean on 8/30/17.
 */

public class AccountListAdapter extends ArrayAdapter<Account> {

    private List<Account> accounts;

    public AccountListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Account> objects) {
        super (context, resource, objects);
        accounts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_account, parent, false);
        }

        Account account = accounts.get(position);

        TextView nameText = (TextView) convertView.findViewById(R.id.addAccountName);
        nameText.setText(account.getName());

        TextView dateText = (TextView) convertView.findViewById(R.id.accountDate);
        dateText.setText(account.getDate());

        TextView typeText = (TextView) convertView.findViewById(R.id.accountType);
        typeText.setText(account.getType());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String amount = formatter.format(account.getAmount());
        TextView amountText = (TextView) convertView.findViewById(R.id.accountAmount);
        amountText.setText(amount);

        return convertView;
    }
}
