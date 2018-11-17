package com.spikeysanju98gmail.shoppinglist;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.spikeysanju98gmail.shoppinglist.realmmodels.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class UpdateShopList extends AppCompatActivity {

    private EditText uTitle,uItems;
    private ImageButton updateBtn,deleteBtn,searchBtn;

    private RealmHelper realmHelper;
    private Realm realm;
    private String title;
    private String color;
    private String item;
    private String  date;
    private String  time;
    private int id;
    private RelativeLayout mBackground;
    private String COLOR = "";


    private View colorview;
    private String etitle,ecolor,edate,etime,eitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shop_list);

        updateBtn = (ImageButton) findViewById(R.id.updateList);
        searchBtn = (ImageButton)findViewById(R.id.searchItem);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateShopList.this,ChooseItemActivity.class));
            }
        });

        mBackground = (RelativeLayout)findViewById(R.id.listBackground);



        uTitle = (EditText)findViewById(R.id.uTitle);
        uItems = (EditText)findViewById(R.id.uItems);
        colorview = (View)findViewById(R.id.colorBtn);

        colorview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(UpdateShopList.this,colorview);

                popupMenu.getMenuInflater().inflate(R.menu.color_menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        switch (id){

                            case R.id.blue:
                                COLOR = item.getTitle().toString();
                                Toast.makeText(UpdateShopList.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                            case R.id.green:
                                COLOR = item.getTitle().toString();

                                Toast.makeText(UpdateShopList.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                            case R.id.red:
                                COLOR = item.getTitle().toString();


                                Toast.makeText(UpdateShopList.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                            case R.id.black:
                                COLOR = item.getTitle().toString();

                                Toast.makeText(UpdateShopList.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();





                        }
                        return false;
                    }
                });

            }
        });



        // getting data from selected shopping todo list

        deleteBtn = (ImageButton) findViewById(R.id.deleteList);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });



         id = Integer.parseInt(getIntent().getStringExtra("id"));
         title = getIntent().getStringExtra("title");
        color = getIntent().getStringExtra("color");
        item = getIntent().getStringExtra("item");
        date = getIntent().getStringExtra("date");
         time = getIntent().getStringExtra("time");



         uTitle.setText(title);
         uItems.setText(item);
         if (color.contains("Blue")){

             mBackground.setBackgroundResource(R.color.md_blue_200);

         } else if (color.contains("Green")){
             mBackground.setBackgroundResource(R.color.md_green_200);
         } else if(color.contains("Red")){
             mBackground.setBackgroundResource(R.color.md_red_200);
         } else if (color.contains("Black")) {

             mBackground.setBackgroundResource(R.color.md_blue_grey_200);
         }



        RealmConfiguration configuration = new RealmConfiguration.Builder().build();

        realm = Realm.getInstance(configuration);




        etitle = uTitle.getText().toString();
        eitem = uItems.getText().toString();







        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                realmHelper = new RealmHelper(realm);
                realmHelper.update(id,uTitle.getText().toString(),uItems.getText().toString(),COLOR,date,time);

                Toast.makeText(UpdateShopList.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                finish();

            }
        });

    }




    private void deleteItem() {

        realmHelper = new RealmHelper(realm);
        realmHelper.delete(id,uTitle.getText().toString(),uItems.getText().toString(),color,date,time);

        Toast.makeText(UpdateShopList.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();


        uTitle.setText("");
        uItems.setText("");

        startActivity(new Intent(UpdateShopList.this,ShopListActivity.class));


    }
}
