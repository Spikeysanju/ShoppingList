package com.spikeysanju98gmail.shoppinglist;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.spikeysanju98gmail.shoppinglist.adapter.TaskAdapter;
import com.spikeysanju98gmail.shoppinglist.realmmodels.RealmHelper;
import com.spikeysanju98gmail.shoppinglist.realmmodels.Task;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class UpdateShopList extends AppCompatActivity {

    private EditText uTitle,uItems;
    private ImageButton updateBtn,deleteBtn,searchBtn;
    private EditText enterItemEd,enterQuantityEd;
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
    private FloatingActionButton addButton;
    private RecyclerView taskRV;
    private TaskAdapter adapter;
    List<Task> taskList;


    private View colorview;
    private String etitle,ecolor,edate,etime,eitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shop_list);


        // Setting Up RecyclerView
        taskRV = (RecyclerView)findViewById(R.id.taskRV);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);

        taskList = new ArrayList<>();

        taskList = getAllTaskList(id);
        adapter = new TaskAdapter(this,taskList);
        taskRV.setAdapter(adapter);



        updateBtn = (ImageButton) findViewById(R.id.updateList);
        searchBtn = (ImageButton)findViewById(R.id.searchItem);

        enterItemEd = (EditText)findViewById(R.id.enterItemEd);
        enterQuantityEd = (EditText)findViewById(R.id.enterQuantityEd);
        addButton = (FloatingActionButton) findViewById(R.id.addButton);






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
                            //    COLOR = item.getTitle().toString();
                                color = item.getTitle().toString();
                                mBackground.setBackgroundResource(R.color.md_blue_200);
                                colorview.setBackgroundResource(R.color.md_blue_200);

                            case R.id.green:
                              //  COLOR = item.getTitle().toString();
                                color = item.getTitle().toString();

                                colorview.setBackgroundResource(R.color.md_green_200);
                                mBackground.setBackgroundResource(R.color.md_green_200);




                            case R.id.red:
                              //  COLOR = item.getTitle().toString();
                                color = item.getTitle().toString();

                                mBackground.setBackgroundResource(R.color.md_red_200);

                                colorview.setBackgroundResource(R.color.md_red_200);



                            case R.id.black:
                              //  COLOR = item.getTitle().toString();
                                color = item.getTitle().toString();


                                mBackground.setBackgroundResource(R.color.md_blue_grey_200);

                                colorview.setBackgroundResource(R.color.md_blue_grey_200);




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


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItems(id,query);
            }
        });

         uTitle.setText(title);
      //  uTitle.setText(String.valueOf(id));
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




        realm = Realm.getInstance(configuration);




        etitle = uTitle.getText().toString();
        eitem = uItems.getText().toString();







        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realmHelper = new RealmHelper(realm);
                        realmHelper.update(id,uTitle.getText().toString(),uItems.getText().toString(),color,date,time);

                        Toast.makeText(UpdateShopList.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });



            }
        });

    }


    String query = String.valueOf(id);


    private void addItems(int id,String query) {

        String item = enterItemEd.getText().toString();
        String quantity = enterQuantityEd.getText().toString();

        if (!item.isEmpty() && !quantity.isEmpty()) {

            final Task task = new Task();
            task.setItem(item);
            task.setListID(id);
            task.setLid(query);
            task.setQuantity(quantity);


            realmHelper = new RealmHelper(realm);

            realmHelper.saveTask(task);

            Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show();


            enterItemEd.setText("");
            enterQuantityEd.setText("");


        }
    }


    // added query to filter the list by id


    public List<Task> getAllTaskList(int id){


        RealmResults<Task> taskResults = realm.where(Task.class)
                .findAll();

        return taskResults;

    }



    private void deleteItem() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmHelper = new RealmHelper(realm);
                realmHelper.delete(id,uTitle.getText().toString(),uItems.getText().toString(),color,date,time);

                Toast.makeText(UpdateShopList.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();


                uTitle.setText("");
                uItems.setText("");

                startActivity(new Intent(UpdateShopList.this,ShopListActivity.class));

            }
        });



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }
}
