package com.spikeysanju98gmail.shoppinglist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.spikeysanju98gmail.shoppinglist.adapter.ShopAdapter;
import com.spikeysanju98gmail.shoppinglist.realmmodels.RealmHelper;
import com.spikeysanju98gmail.shoppinglist.realmmodels.ShoppingModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ShopListActivity extends AppCompatActivity {

    private Realm realm;
    private RealmHelper realmHelper;
    private RecyclerView shopRV;
    List<ShoppingModel> shoplist;
    private ShopAdapter adapter;
    private FloatingActionButton addList;


    private static final String TAG = "ShopListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);

        shopRV = (RecyclerView)findViewById(R.id.shopRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        shopRV.setLayoutManager(layoutManager);

        addList = (FloatingActionButton)findViewById(R.id.addList);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopListActivity.this,AddShopListActivity.class));
            }
        });

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();

        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);


        shoplist = new ArrayList<>();

        shoplist = realmHelper.getAllShoppingList();

        adapter = new ShopAdapter(this,shoplist);

        shopRV.setAdapter(adapter);



    }


    @Override
    protected void onRestart() {
        super.onRestart();

        adapter.notifyDataSetChanged();
    }
}


