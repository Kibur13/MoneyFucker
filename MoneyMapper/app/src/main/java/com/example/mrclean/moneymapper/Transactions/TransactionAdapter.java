package com.example.mrclean.moneymapper.Transactions;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrclean.moneymapper.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class TransactionAdapter extends RealmRecyclerViewAdapter<Transaction, TransactionAdapter.ViewHolder> {

    private static final String TAG = "TransactionAdapter";

    private TransactionAdapterShortListener shortListener;
    private TransactionAdapterLongListener longListener;


    //Super and autoUpdate
    TransactionAdapter(@Nullable OrderedRealmCollection<Transaction> data, boolean autoUpdate)
    {
        super(data,autoUpdate);
    }


    //creates up and instantiates textViews
    static class ViewHolder extends RecyclerView.ViewHolder
    {

        // TODO: 11/21/17 need to change these to the correct types and places
        TextView tvTransactionAmount;
        TextView tvTransactionDate;
        TextView tvTransactionReason;

        public View mView;

        public ViewHolder (View v)
        {
            super (v);
            tvTransactionAmount = (TextView) v.findViewById(R.id.transactionAmount);
            tvTransactionDate = (TextView) v.findViewById(R.id.accountDate);
            tvTransactionReason = (TextView) v.findViewById(R.id.accountReason);
            mView = itemView;

        }
    }


    //listener check to make sure the calling method implements Listener Methods
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        //error handling and instantiating of Listeners
        if (recyclerView.getContext() instanceof TransactionAdapterShortListener) {
            shortListener = (TransactionAdapterShortListener) recyclerView.getContext();
        } else {
            throw new RuntimeException(recyclerView.getContext().toString()
                    + " must implement AccountAdapterLongListener");
        }
        if (recyclerView.getContext() instanceof TransactionAdapterLongListener) {
            longListener = (TransactionAdapterLongListener) recyclerView.getContext();
        } else {
            throw new RuntimeException(recyclerView.getContext().toString()
                    + " must implement AccountAdapterLongListener");
        }
    }


    //chooses view to inflate
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_transaction,parent,false);
        return new ViewHolder(v);
    }


    //populates the Transaction Object inside of the RecyclerView and sends pos if clicked
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Transaction transaction = getItem(position);

        if (transaction != null) {
            holder.tvTransactionAmount.setText(String.valueOf(transaction.getAmount()));
            holder.tvTransactionDate.setText(transaction.getDateString());
            holder.tvTransactionReason.setText(transaction.getReason());
        }else Log.i(TAG, "onBindViewHolder: no transactions");


        //listener sends ID of Transaction clicked on to the Listener interface
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountName = TransactionListFragment.accountName;
                String transactionID = transaction.getId();
                shortListener.onTransactionShortClick(accountName, transactionID);
                Log.i(TAG, "onClick: accountName =" + accountName +"\n getID =" + transactionID);
            }
        });

        //listener sends ID of Transaction clicked on to the Listener interface
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String accountName = TransactionListFragment.accountName;
                String transactionID = transaction.getId();
                longListener.onTransactionLongClick(accountName, transactionID);
                Log.i(TAG, "onClick: accountName =" + accountName +"\n getID =" + transactionID);
                return true;
            }
        });
    }


    //Short Listener for Transaction list.  Sends accountName and transactionID
    public interface TransactionAdapterShortListener{
        void onTransactionShortClick(String accountName, String transactionID);
    }

    //Long Listener for Transaction List.  Sends accountName and transactionID
    public interface TransactionAdapterLongListener{
        void onTransactionLongClick(String accountName, String transactionID);
    }

}
