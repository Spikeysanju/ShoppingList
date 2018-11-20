package com.spikeysanju98gmail.shoppinglist.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spikeysanju98gmail.shoppinglist.Item;
import com.spikeysanju98gmail.shoppinglist.ItemListActivity;
import com.spikeysanju98gmail.shoppinglist.R;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentsFragment extends Fragment {

    private DatabaseReference recentDB;
    private FirebaseRecyclerAdapter recentAdapter;
    private RecyclerView recentRV;
    View v;

    public RecentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_recents, container, false);


        recentDB = FirebaseDatabase.getInstance().getReference().child("RecentItems");


        recentRV = (RecyclerView)v.findViewById(R.id.recentRV);


        LinearLayoutManager mLayout= new LinearLayoutManager(getActivity());
        recentRV.setLayoutManager(mLayout);
        mLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recentRV.setNestedScrollingEnabled(false);


        loadRecentItems();
        return v;
    }

    private void loadRecentItems() {

        recentAdapter = new FirebaseRecyclerAdapter<Item,RecentViewHolder>(
                Item.class,
                R.layout.recent_rv_layout,
                RecentViewHolder.class,
                recentDB
        ) {
            @Override
            protected void populateViewHolder(RecentViewHolder viewHolder, Item model, int position) {

                viewHolder.setItem(model.getItem());
                viewHolder.setQuantity(model.getQuantity());
                viewHolder.setImage(getActivity(),model.getImage());
            }


        };

        recentRV.setAdapter(recentAdapter);
        recentAdapter.notifyDataSetChanged();

    }


    public static class RecentViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public RecentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setItem(String item){

            TextView itemTV = (TextView)mView.findViewById(R.id.ritemTV);
            itemTV.setText(item);
        }

        public void setQuantity(String quantity){

            TextView itemQuantity = (TextView)mView.findViewById(R.id.ritemQuantityTv);
            itemQuantity.setText(quantity);
        }

        public void setImage(Context ctx, String image){

            ImageView itemImage = (ImageView) mView.findViewById(R.id.ritemImage);
            Picasso.with(ctx).load(image).into(itemImage);

        }
    }

}
