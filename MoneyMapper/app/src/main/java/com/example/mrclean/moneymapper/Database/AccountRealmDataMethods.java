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

    private Realm AccountRealm;

    //opens realm instance
    public void open()
    {
        //this is where it tells Realm which db to use
        AccountRealm = Realm.getDefaultInstance();

        Log.d(TAG, "opened:");

    }

    //closes realm instance
    public void close()
    {
        AccountRealm.close();

        Log.d(TAG, "Closed");
    }

    //creates or adds new account
    public void createAccount (final Account account)
    {
        AccountRealm.executeTransaction(new Realm.Transaction()
                                    {
                                        @Override
                                        public void execute(Realm realm)
                                        {
                                            realm.insertOrUpdate(account);
                                        }
                                    });

                                  Log.d(TAG, "insertOrUpdate: " + account.getName());



    }

    //lists all accounts
    public List<Account> getAllAccounts()
    {
        return AccountRealm.where(Account.class).findAll();
    }


    //changes specific items in Realm
//    public void modifyStuff()
//    {
//        final Account account = AccountRealm.where(Account.class).findFirst();
//
//        AccountRealm.executeTransaction(new Realm.Transaction()
//
//                                    {
//                                        @Override
//                                        public void execute(Realm realm) {
//                                            account.setName("Renters");
//                                        }
//                                    }
//
//
//
//
//        );
//    }
//    //deletes particular Realm object
//    public void deleteAccount(Account account)
//    {
//        final Account accountManaged = AccountRealm.where(Account.class).equalTo(AccountFields.ID,account.getId()).findFirst();
//
//
//        AccountRealm.executeTransaction(new Realm.Transaction()
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


    //Deletes everything in Realm
    public void deleteEverything()
    {
        final RealmResults<Account> accounts = AccountRealm.where(Account.class).findAll();

        AccountRealm.executeTransaction(new Realm.Transaction()
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
