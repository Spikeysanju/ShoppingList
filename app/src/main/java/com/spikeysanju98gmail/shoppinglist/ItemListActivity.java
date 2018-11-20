package com.spikeysanju98gmail.shoppinglist;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class ItemListActivity extends AppCompatActivity {

    private RecyclerView mItemRV;
    private DatabaseReference mItemDB,mtitleDB,recentDB;
    FirebaseRecyclerAdapter mItemAdapter;
    private FloatingActionButton addItemBtn;
    private ImageButton addItemtoList,removeFromList;
    private TextView itemListName;
    String listID="";
    TodoItems current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mItemRV = (RecyclerView)findViewById(R.id.itemRV);
        mItemDB = FirebaseDatabase.getInstance().getReference().child("Items");
        mtitleDB = FirebaseDatabase.getInstance().getReference().child("Todolist");
        recentDB = FirebaseDatabase.getInstance().getReference().child("RecentItems");

        itemListName = (TextView)findViewById(R.id.itemListName);



        addItemBtn = (FloatingActionButton) findViewById(R.id.addItems);
       addItemBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent dogIntent = new Intent(getApplicationContext(), AddItems.class);
               dogIntent.putExtra("listID", listID);
               startActivity(dogIntent);
           }
       });


        mItemDB.keepSynced(true);
        LinearLayoutManager mLayout= new LinearLayoutManager(getApplicationContext());
        mItemRV.setLayoutManager(mLayout);
        mLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mItemRV.setNestedScrollingEnabled(false);

        if (getIntent()!=null){
            listID = getIntent().getStringExtra("listID");
            if (!listID.isEmpty() && listID!=null){

                loadItems(listID);
                loadName(listID);

            }
        }


    }

    private void loadName(String listID) {
        mtitleDB.child(listID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                current = dataSnapshot.getValue(TodoItems.class);
                itemListName.setText(current.getTitle());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    private void loadItems(final String listID) {


        mItemAdapter = new FirebaseRecyclerAdapter<Item, ItemListActivity.ItemViewHolder>(

                Item.class,
                R.layout.item_rv_layout,
                ItemListActivity.ItemViewHolder.class,
                mItemDB.orderByChild("menuID").equalTo(listID)
        ) {


            @Override
            protected void populateViewHolder(ItemViewHolder viewHolder, final Item model, final int position) {
                viewHolder.setItem(model.getItem());
                viewHolder.setQuantity(model.getQuantity());
                viewHolder.setImage(ItemListActivity.this,model.getImage());
                addItemtoList = (ImageButton)viewHolder.mView.findViewById(R.id.addtoList);

                //add item to the list
                addItemtoList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Item item = new Item(
                                model.getItem(),
                                model.getMenuID(),
                                model.getImage(),
                                model.getQuantity()


                        );

                        final String RandomUID = UUID.randomUUID().toString();

                        recentDB.child(RandomUID).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ItemListActivity.this, "Item Added To Recent", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                });
                removeFromList = (ImageButton)viewHolder.mView.findViewById(R.id.deletefromlist);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ItemListActivity.this, ""+ model.getItem(), Toast.LENGTH_SHORT).show();


                    }
                });

            }


        };
        mItemAdapter.notifyDataSetChanged();
        mItemRV.setAdapter(mItemAdapter);
    }





    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public ItemViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setItem(String item){

            TextView itemTV = (TextView)mView.findViewById(R.id.itemTV);
            itemTV.setText(item);
        }

        public void setQuantity(String quantity){

            TextView itemQuantity = (TextView)mView.findViewById(R.id.itemQuantityTv);
            itemQuantity.setText(quantity);
        }

        public void setImage(Context ctx, String image){

            ImageView itemImage = (ImageView) mView.findViewById(R.id.itemImage);
            itemImage.setClipToOutline(true);
            Picasso.with(ctx).load(image).into(itemImage);

        }
    }
}
