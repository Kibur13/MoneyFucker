package com.example.mrclean.moneymapper.Database;


import android.util.Log;

import com.example.mrclean.moneymapper.Accounts.Account;
import com.example.mrclean.moneymapper.Accounts.AccountFields;

import com.example.mrclean.moneymapper.Transactions.Transaction;


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
        return accountRealm.where(Account.class).findAll();
    }


    //finds all the transactions that are associated with a specific Account
    public List<Transaction> getAllTransactions(String name){

        Account account;
        List<Transaction> transactions;

        account = accountRealm.where(Account.class).equalTo(AccountFields.NAME, name).findFirst();

            transactions = account.getTransaction();

            return transactions;


    }


    //adds a Transaction to a specific string Name
    public void addTransaction(final String realmName, final Date date, final double amount, final String reason){

        accountRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Transaction transaction = accountRealm.copyToRealm(new Transaction( date, amount, reason));
                accountRealm.where(Account.class).equalTo(AccountFields.NAME, realmName)
                        .findFirst()
                        .getTransaction()
                        .add(transaction);

            }
        });

    }
    public Account getPrimaryKeyByName(String id_Name){
        Log.d(TAG, "getPrimaryKeyByName method running..  " +id_Name);
        Account accounts = new Account();
          accounts = accountRealm.where(Account.class)
                .equalTo("name",id_Name)
                .findFirst();
          if(accounts !=  null )
          {
              String amount = String.valueOf(accounts.getAmount());
              Log.d(TAG, "This is account amount:" + amount);
              return accounts;
          }
          else
          {
             //throw new NullPointerException();
              Log.d(TAG, "Something went really bad " + id_Name);
              return accounts;
          }



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