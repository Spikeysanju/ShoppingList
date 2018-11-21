package com.spikeysanju98gmail.shoppinglist.realmmodels;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm){

        this.realm = realm;
    }

    //save data to  realm
    public void save(final ShoppingModel shoppingModel){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm!=null){

                    Log.e("Log", "Database was created  " );


                    Number currentID = realm.where(ShoppingModel.class).max("id");
                    int nextId;
                    if (currentID == null){

                        nextId=1;

                    } else {
                        nextId = currentID.intValue() + 1;
                    }


                    shoppingModel.setId(nextId);


                    ShoppingModel s = realm.copyToRealm(shoppingModel);
                } else {
                    Log.e("Log", "Database not exits " );
                }
            }
        });
    }


    public List<ShoppingModel> getAllShoppingList(){

        RealmResults<ShoppingModel> shopResult = realm.where(ShoppingModel.class).findAll();


        return shopResult;
    }

   // save items to realm
    public void saveTask(final Task task){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm!=null){

                    Log.e("Log", "Database was created  " );


                    Number currentID = realm.where(Task.class).max("id");
                    int nextId;
                    if (currentID == null){

                        nextId=1;

                    } else {
                        nextId = currentID.intValue() + 1;
                    }


                    task.setId(nextId);


                    Task s = realm.copyToRealm(task);
                } else {
                    Log.e("Log", "Database not exits " );
                }
            }
        });
    }







    public void update(final int id, final String title, final String items, final String color, final String date, final String time){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {



                ShoppingModel shoppingModel = realm.where(ShoppingModel.class).equalTo("id",id)
                        .findFirst();

                shoppingModel.setTitle(title);
                shoppingModel.setItems(items);
                shoppingModel.setTime(time);
                shoppingModel.setDate(date);
                shoppingModel.setColor(color);

            }
        }, new Realm.Transaction.OnSuccess(){


            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError(){

            @Override
            public void onError(Throwable error) {

                error.printStackTrace();
            }
        });
    }


    // delete from realm

    public void delete(final int id, final String title, final String items, final String color, final String date, final String time){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                ShoppingModel shoppingModel = realm.where(ShoppingModel.class).equalTo("id",id)
                        .findFirst();

                shoppingModel.setTitle(title);
                shoppingModel.setItems(items);
                shoppingModel.setTime(time);
                shoppingModel.setDate(date);
                shoppingModel.setColor(color);

                shoppingModel.deleteFromRealm();
                realm.commitTransaction();

            }
        }, new Realm.Transaction.OnSuccess(){


            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError(){

            @Override
            public void onError(Throwable error) {

                error.printStackTrace();
            }
        });
    }

}
