package com.example.android.firstapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.android.firstapp.R.drawable.apple_pie;

/**
 * Created by j2md7_000 on 8/21/2017.
 */

public class TransactionAdapter extends ArrayAdapter<Transactions>{

    Context context;
     int layoutResourceId;
    Transactions data[]=null;

    public TransactionAdapter(Context context, int layoutResourceId, Transactions[] data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data=data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TransactionHolder holder = null;

        if ( row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TransactionHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
           // holder.imgIcon.setImageResource(apple_pie);
            holder.txtTitle= (TextView)row.findViewById(R.id.txtTitle);
            holder.txtLine= (TextView)row.findViewById(R.id.amount);

            row.setTag(holder);


        }
        else
        {
            holder = (TransactionHolder)row.getTag();

        }

        Transactions transact = data[position];
        holder.txtTitle.setText(transact.name);
        holder.imgIcon.setImageResource(apple_pie);
        double amounts = transact.getAmount();
        holder.txtLine.setText(""+amounts);

        return row;
        //return super.getView(position, convertView, parent);
    }

    static class TransactionHolder
    {
        TextView txtTitle;
        ImageView imgIcon;
        TextView txtLine;
    }
}
