package com.example.mrclean.moneymapper.Features;

/**
 * Created by jmd71_000 on 11/10/2017.
 */





        import android.support.annotation.Nullable;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.mrclean.moneymapper.Accounts.Account;
        import com.example.mrclean.moneymapper.R;

        import io.realm.OrderedRealmCollection;
        import io.realm.RealmRecyclerViewAdapter;





/**
 * Created by jmd71_000 on 11/5/2017.
 */

public class AccountAdapter extends RealmRecyclerViewAdapter<Account,AccountAdapter.ViewHolder> implements AdapterView.OnClickListener{

    private static final String TAG = AccountAdapter.class.getSimpleName();



    public AccountAdapter(@Nullable OrderedRealmCollection<Account> data, boolean autoUpdate)
    {
        super(data,autoUpdate);
    }

    @Override
    public void onClick(View v) {

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_account,parent,false);
        return new ViewHolder(v);
        // return null;

        // return null;
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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), account.getName(), Toast.LENGTH_LONG).show();
            }


        });



    }
}