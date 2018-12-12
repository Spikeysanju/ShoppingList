package com.spikeysanju98gmail.shoppinglist;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.spikeysanju98gmail.shoppinglist.adapter.ShopAdapter;
import com.spikeysanju98gmail.shoppinglist.realmmodels.RealmHelper;
import com.spikeysanju98gmail.shoppinglist.realmmodels.ShoppingModel;
import com.spikeysanju98gmail.shoppinglist.realmmodels.Task;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class ShopListActivity extends AppCompatActivity {

    private Realm realm;
    private RealmHelper realmHelper;
    private RecyclerView shopRV;
    List<ShoppingModel> shoplist;
    private ShopAdapter adapter;
    private FloatingActionButton addList;
    private ImageButton changeLayout;
    private boolean isLinear= true;
    private ImageButton sortBtn;


    private static final String TAG = "ShopListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);

        shopRV = (RecyclerView)findViewById(R.id.shopRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        shopRV.setLayoutManager(layoutManager);

        addList = (FloatingActionButton)findViewById(R.id.addList);
        changeLayout = (ImageButton)findViewById(R.id.changeLayout);


        sortBtn = (ImageButton)findViewById(R.id.sortBtn);

        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog dialog = new BottomSheetDialog(ShopListActivity.this);

                dialog.setContentView(R.layout.bottom_dialog_shop);


                RadioGroup sortGroup = (RadioGroup) dialog.findViewById(R.id.sortGroup);

                int selectedSort = sortGroup.getCheckedRadioButtonId();

                RadioButton sortRadioBtn = (RadioButton)dialog.findViewById(selectedSort);


                if (selectedSort==-1){
                    Toast.makeText(ShopListActivity.this, "Not Selected", Toast.LENGTH_SHORT).show();
                } else  {

                    if (sortRadioBtn.getText().equals("Date Created")){

                        Toast.makeText(ShopListActivity.this, ""+sortRadioBtn.getText(), Toast.LENGTH_SHORT).show();
                      //  sortDateCreated();


                    } else if (sortRadioBtn.getText().equals("Alphabetically")){

                      //  sortAlphabetically();




                    }

                }



                dialog.show();


            }
        });

        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLinear){
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
                    shopRV.setLayoutManager(gridLayoutManager);
                    isLinear = false;
                } else {

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    shopRV.setLayoutManager(layoutManager);
                    isLinear = true;
                }

            }
        });

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

    private void sortAlphabetically() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                RealmResults<ShoppingModel> allSorted = realm.where(ShoppingModel.class).findAllSorted("title", Sort.ASCENDING);

                adapter = new ShopAdapter(getApplicationContext(),allSorted);
                shopRV.setAdapter(adapter);

            }
        });

    }

    private void sortDateCreated() {

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {


                    RealmResults<ShoppingModel> allSorted = realm.where(ShoppingModel.class).findAllSorted("date", Sort.DESCENDING);

                    adapter = new ShopAdapter(getApplicationContext(),allSorted);
                    shopRV.setAdapter(adapter);
                }
            });

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        adapter.notifyDataSetChanged();
    }
}


