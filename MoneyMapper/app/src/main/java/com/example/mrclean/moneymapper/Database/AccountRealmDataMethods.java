package com.example.mrclean.moneymapper.Database;


import android.util.Log;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AccountFields;
import com.example.mrclean.moneymapper.Transactions.Transaction;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class AccountRealmDataMethods {

    private static final String TAG = AccountRealmDataMethods.class.getSimpleName();

    private Realm accountRealm;


    //opens realm instance
    public void open() {
        //this is where it tells Realm which db to use
        accountRealm = Realm.getDefaultInstance();

        Log.d(TAG, "opened:");
    }


    //closes realm instance
    public void close() {
        accountRealm.close();

        Log.d(TAG, "Closed");
    }


    //creates or adds new account
    public void createAccount(final Account account) {
        accountRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(account);
            }
        });

        Log.d(TAG, "insertOrUpdate: " + account.getName());
    }


    //lists all accounts
    public List<Account> getAllAccounts() {
        return accountRealm.where(Account.class).findAll();
    }


    //finds a specific Transaction
    public Transaction getTransaction(String accountName, String transactionID){
        Account account;
        Transaction transaction= null;
        RealmList<Transaction> transactions;

        account = accountRealm.where(Account.class).equalTo(AccountFields.NAME, accountName).findFirst();

        transactions = account.getTransaction();

        boolean transactionFound = false;
        do {
            for (int i = 0; i < transactions.size(); i++){
                if (transactions.get(i).getId().equals(transactionID)){
                    transaction = transactions.get(i);
                    transactionFound = true;
                }
            }
        }while (!transactionFound);

        return transaction;
    }


    //finds all the transactions that are associated with a specific Account
    public List<Transaction> getAllTransactions(String name){

        Account account;
        List<Transaction> transactions;

        account = accountRealm.where(Account.class).equalTo(AccountFields.NAME, name).findFirst();
            transactions = account.getTransaction();
            return transactions;
    }


    //edits Transaction
    public void editTransaction (final String transactionID,
                                 final Date date, final double amount, final String reason){

        final Transaction transactionToAdd = new Transaction(date, amount, reason);
        transactionToAdd.setId(transactionID);

        Log.i(TAG, "editTransaction: transactionID = " + transactionID);

        accountRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                accountRealm.copyToRealmOrUpdate(transactionToAdd);
            }
        });

    }


    //adds a Transaction to a specific string Name
    public void addTransaction(final String realmName, final Date date, final double amount, final String reason){

        accountRealm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                Transaction transaction = accountRealm.copyToRealm(new Transaction(date, amount, reason));
                accountRealm.where(Account.class).equalTo(AccountFields.NAME, realmName)
                        .findFirst()
                        .getTransaction()
                        .add(transaction);
            }
        });

    }


    //deletes Transaction from realm
    public void deleteTransaction(String accountName, final String transactionID){

        final Account account;

        account = accountRealm.where(Account.class).equalTo(AccountFields.NAME, accountName).findFirst();
        final RealmList<Transaction> transactions = account.getTransaction();

        accountRealm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                int position = 0;

                boolean transactionFound = false;
                do {
                    for (int i = 0; i < transactions.size(); i++){
                        if (transactions.get(i).getId().equals(transactionID)){
                            position = i;
                            transactionFound = true;
                        }
                    }
                }while (!transactionFound);

                Log.i(TAG, "execute: position: " + position);
                account.getTransaction().deleteFromRealm(position);
            }
        });



    }


    //Deletes everything in Realm
    public void deleteAll() {
        final RealmResults<Account> accounts = accountRealm.where(Account.class).findAll();

        accountRealm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                accounts.deleteAllFromRealm();
                                            }
                                        }
        );
    }
}