package com.spikeysanju98gmail.shoppinglist;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.spikeysanju98gmail.shoppinglist.realmmodels.RealmHelper;
import com.spikeysanju98gmail.shoppinglist.realmmodels.ShoppingModel;
import com.spikeysanju98gmail.shoppinglist.realmmodels.Task;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddShopListActivity extends AppCompatActivity {

    private View colorBtn;
    private EditText edTitle,edItems;
    private Realm realm;
    private ImageButton saveBtn,alarmBtn;
    private RealmHelper realmHelper;

    private String COLOR = "";
    private String date = "10/11/18";
    private String time = "12.30 AM";

    Calendar calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shoplist_activity);


        edTitle = (EditText)findViewById(R.id.listTitle);
        edItems = (EditText)findViewById(R.id.listItems);


        saveBtn = (ImageButton)findViewById(R.id.savelistBtn);

        colorBtn = (View)findViewById(R.id.colorBtn);



        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseColor();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();
            }
        });


        //setting up realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

    }



    private void chooseColor() {


        PopupMenu popupMenu = new PopupMenu(AddShopListActivity.this,colorBtn);

        popupMenu.getMenuInflater().inflate(R.menu.color_menu,popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){

                    case R.id.blue:
                        COLOR = item.getTitle().toString();
                        Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                    case R.id.green:
                        COLOR = item.getTitle().toString();
                        Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                    case R.id.red:
                        COLOR = item.getTitle().toString();
                        Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                    case R.id.black:
                        COLOR = item.getTitle().toString();
                        Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();





                }
                return false;
            }
        });

    }


    private void getValues() {


        String title = edTitle.getText().toString();
        String items = edItems.getText().toString();


        if (!title.isEmpty() && !items.isEmpty() && !COLOR.isEmpty() && !date.isEmpty() && !time.isEmpty()){

            ShoppingModel shoppingModel = new ShoppingModel();



            shoppingModel.setTitle(title);
            shoppingModel.setItems(items);
            shoppingModel.setColor(COLOR);
            shoppingModel.setDate(date);
            shoppingModel.setTime(time);


            realmHelper =new RealmHelper(realm);

            realmHelper.save(shoppingModel);

            Toast.makeText(this, "List Created Successfully", Toast.LENGTH_SHORT).show();

            edTitle.setText("");
            edItems.setText("");

            startActivity(new Intent(AddShopListActivity.this,ShopListActivity.class));



        } else {

            Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show();
        }

    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.color_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.blue) {
//
//            COLOR = item.getTitle().toString();
//            Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
//            return true;
//        } else if(id==R.id.green) {
//                COLOR = item.getTitle().toString();
//                Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
//
//                return true;
//        } else if(id==R.id.red) {
//                COLOR = item.getTitle().toString();
//                Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
//                return true;
//        } else {
//                COLOR = item.getTitle().toString();
//                Toast.makeText(AddShopListActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
//                return true;
//        }
//
//    }



}
