package com.example.mrclean.moneymapper.Database;


import android.util.Log;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AccountFields;
import com.example.mrclean.moneymapper.Transactions.Transaction;
import com.example.mrclean.moneymapper.Transactions.TransactionFields;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
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
        return accountRealm.where(Account.class).findAllSorted(AccountFields.DATE_DUE);
    }


    //finds a specific Transaction
    public Transaction getTransaction(String accountName, String transactionID){

        Transaction transaction;

        transaction = accountRealm.where(Account.class)
                .equalTo(AccountFields.NAME, accountName)
                .findFirst()
                .getTransaction()
                .where()
                .equalTo(TransactionFields.ID, transactionID)
                .findFirst();

        return transaction;
    }


    //finds all the transactions that are associated with a specific Account
    public List<Transaction> getAllTransactions(String name){

        List<Transaction> transactions;

        transactions = accountRealm.where(Account.class)
                .equalTo(AccountFields.NAME, name)
                .findFirst()
                .getTransaction();

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
    public void addTransaction(final String realmName, final Date date, final double amount,
                               final String reason){

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

        //final Account account;
        final Transaction transactionToDelete;

        transactionToDelete = accountRealm.where(Account.class)
                .equalTo(AccountFields.NAME, accountName)
                .findFirst()
                .getTransaction()
                .where()
                .equalTo(TransactionFields.ID, transactionID)
                .findFirst();

        accountRealm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {

                transactionToDelete.deleteFromRealm();

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