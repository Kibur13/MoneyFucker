package com.example.mrclean.moneymapper.Database;


import android.util.Log;

import com.example.mrclean.moneymapper.Accounts.Account;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by j2md7_000 on 9/24/2017.
 */

public class AccountRealmDataMethods {

    private static final String TAG = AccountRealmDataMethods.class.getSimpleName();

    private Realm AccRealm;

    public void open()
    {
        AccRealm = Realm.getDefaultInstance();

        Log.d(TAG, "opened:");

    }
    public void close()
    {
        AccRealm.close();

        Log.d(TAG, "Closed");
    }

    public void createAccount (final Account account)
    {
        AccRealm.executeTransaction(new Realm.Transaction()
                                    {
                                        @Override
                                        public void execute(Realm realm)
                                        {
                                            realm.insertOrUpdate(account);
                                        }
                                    });

                                  Log.d(TAG, "createdRecipe: the id: " + account.getId());



    }
    public List<Account> getAllAccounts()
    {
        return AccRealm.where(Account.class).findAll();
    }

    public Account getAccount (String ACCid)
    {
        return AccRealm.where(Account.class).distinct(ACCid).first();

    }

    public void modifyStuff()
    {
        final Account account = AccRealm.where(Account.class).findFirst();

        AccRealm.executeTransaction(new Realm.Transaction()

                                    {
                                        @Override
                                        public void execute(Realm realm) {
                                            account.setName("Renters");
                                        }
                                    }




        );
    }

//    public void deleteAccount(Account account)
//    {
//        final Account accountManaged = AccRealm.where(Account.class).equalTo(AccountFields.ID,account.getId()).findFirst();
//
//
//        AccRealm.executeTransaction(new Realm.Transaction()
//                                    {
//                                        @Override
//                                        public void execute(Realm realm) {
//                                            accountManaged.deleteFromRealm();
//                                        }
//                                    }
//
//
//
//        );
//    }


    public void deleteEverything()
    {
        final RealmResults<Account> accounts = AccRealm.where(Account.class).findAll();

        AccRealm.executeTransaction(new Realm.Transaction()
                                    {
                                        @Override
                                        public void execute(Realm realm) {
                                            accounts.deleteAllFromRealm();
                                        }
                                    }



        );
    }

    private class AccountFields {
        public class ID {
        }
    }
}
