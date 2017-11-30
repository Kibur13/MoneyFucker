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

    // TODO: 11/21/17 uncomment when the listeners are set up
//    private AccountAdapterLongListener longListener;
//    private AccountAdapterShortListener shortListener;


    public TransactionAdapter(@Nullable OrderedRealmCollection<Transaction> data, boolean autoUpdate)
    {
        super(data,autoUpdate);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
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

    //listeners
    // TODO: 11/21/17 uncomment this when the listeners are set up
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//        //error handling and instantiating of Listeners
//        if (recyclerView.getContext() instanceof AccountAdapterLongListener) {
//            longListener = (AccountAdapterLongListener) recyclerView.getContext();
//        } else {
//            throw new RuntimeException(recyclerView.getContext().toString()
//                    + " must implement AccountAdapterLongListener");
//        }
//        if (recyclerView.getContext() instanceof AccountAdapterLongListener) {
//            shortListener = (AccountAdapterShortListener) recyclerView.getContext();
//        } else {
//            throw new RuntimeException(recyclerView.getContext().toString()
//                    + " must implement AccountAdapterShortListener");
//        }
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_transaction,parent,false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Transaction transaction = getItem( position);


        if (transaction != null) {
            holder.tvTransactionAmount.setText(String.valueOf(transaction.getAmount()));
            holder.tvTransactionDate.setText(transaction.getDateString());
            holder.tvTransactionReason.setText(transaction.getReason());
        }else Log.i(TAG, "onBindViewHolder: transaction is null");



        //listeners
        // TODO: 11/21/17 uncomment once the listeners are set up
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String accountName = transaction.getId();
//                shortListener.onShortClick(accountName);
//            }
//        });
//
//        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                String accountName = transaction.getId();
//                longListener.onLongClick(accountName);
//
//                return true;
//            }
//        });

    }

    // TODO: 11/21/17 uncomment when the listeners are set up
//    //Short Listener from RecyclerView
//    public interface AccountAdapterShortListener{
//        void onShortClick(String name);
//    }
//
//    //Long Listener from RecyclerView
//    public interface AccountAdapterLongListener{
//        void onLongClick(String name);
//    }
//
}
