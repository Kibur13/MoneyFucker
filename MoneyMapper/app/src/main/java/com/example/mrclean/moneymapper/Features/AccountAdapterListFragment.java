package com.example.mrclean.moneymapper.Features;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Database.AccountRealmDataMethods;
import com.example.mrclean.moneymapper.Features.AccountFragment.OnListFragmentInteractionListener;
import com.example.mrclean.moneymapper.Features.dummy.DummyContent.DummyItem;
import com.example.mrclean.moneymapper.R;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AccountAdapterListFragment extends RecyclerView.Adapter<AccountAdapterListFragment.ViewHolder> {

    private final List<DummyItem> mValues;

    private final OnListFragmentInteractionListener mListener;

    private RecyclerView accountRecyclerView;

    private AccountAdapter adapt;
    AccountRealmDataMethods RealmSource;


    public AccountAdapterListFragment(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_account, parent, false);
        AccountRealmDataMethods RealmSource = new AccountRealmDataMethods();
        RealmSource.open();
       // AccountAdapter adapt = new AccountAdapter((OrderedRealmCollection<Account>)
         //       RealmSource.getAllAccounts(),true);

        setupRecyclerView(view);

        RealmSource.close();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    private void setupRecyclerView(View view)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        accountRecyclerView.setLayoutManager(layoutManager);

        accountRecyclerView.setHasFixedSize(true);

        adapt = new AccountAdapter((OrderedRealmCollection<Account>)
                RealmSource.getAllAccounts(),true);
        accountRecyclerView.setAdapter(adapt);
    }
}
