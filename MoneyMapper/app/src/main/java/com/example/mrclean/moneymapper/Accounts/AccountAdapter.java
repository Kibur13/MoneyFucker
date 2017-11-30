package com.example.mrclean.moneymapper.Accounts;

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


public class AccountAdapter extends RealmRecyclerViewAdapter<Account, AccountAdapter.ViewHolder>{

    private static final String TAG = AccountAdapter.class.getSimpleName();
    private AccountAdapterLongListener longListener;
    private AccountAdapterShortListener shortListener;


    public AccountAdapter(@Nullable OrderedRealmCollection<Account> data, boolean autoUpdate)
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
            Log.d(TAG, accountName + " "+ accountDate);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        //error handling and instantiating of Listeners
        if (recyclerView.getContext() instanceof AccountAdapterLongListener) {
            longListener = (AccountAdapterLongListener) recyclerView.getContext();
        } else {
            throw new RuntimeException(recyclerView.getContext().toString()
                    + " must implement AccountAdapterLongListener");
        }
        if (recyclerView.getContext() instanceof AccountAdapterLongListener) {
            shortListener = (AccountAdapterShortListener) recyclerView.getContext();
        } else {
            throw new RuntimeException(recyclerView.getContext().toString()
                    + " must implement AccountAdapterShortListener");
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_account,parent,false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Account account = getItem( position);


        if (account != null) {
            holder.accountName.setText(account.getName());
        }
        holder.accountDate.setText(account.getDateString());
        holder.accountType.setText(account.getType());
        holder.accountAmount.setText(String.valueOf(account.getAmount()));

        //short click listener method
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountName = account.getName();
                shortListener.onShortClick(accountName);
            }
        });

        //long click listener method
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String accountName = account.getName();
                longListener.onLongClick(accountName);
                return true;
            }
        });

    }

    //Short Listener from RecyclerView
    public interface AccountAdapterShortListener{
        void onShortClick(String name);
    }

    //Long Listener from RecyclerView
    public interface AccountAdapterLongListener{
        void onLongClick(String name);
    }
}