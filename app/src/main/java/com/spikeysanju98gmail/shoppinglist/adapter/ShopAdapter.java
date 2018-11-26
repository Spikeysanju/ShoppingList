package com.spikeysanju98gmail.shoppinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spikeysanju98gmail.shoppinglist.R;
import com.spikeysanju98gmail.shoppinglist.UpdateShopList;
import com.spikeysanju98gmail.shoppinglist.realmmodels.ShoppingModel;

import java.util.List;

public class ShopAdapter  extends  RecyclerView.Adapter<ShopAdapter.MyViewHolder>{


    private List<ShoppingModel> shopList;
    View colorview;
    LinearLayout shopCardColor;

    Context context;

    public  ShopAdapter(Context context, List<ShoppingModel> shopList){

        this.context = context;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_rv_layout,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        ShoppingModel shoppingModel = shopList.get(position);

        holder.title.setText(shoppingModel.getTitle());
        holder.item.setText(shoppingModel.getItems());
        holder.time.setText(shoppingModel.getDate());
        holder.color.setText(shoppingModel.getColor());

        colorview = (View)holder.itemView.findViewById(R.id.colorView);

        shopCardColor = (LinearLayout) holder.itemView.findViewById(R.id.shopCardColor);




        if (shoppingModel.getColor().equals("Green")){

            colorview.setBackgroundResource(R.color.md_green_400);
            shopCardColor.setBackgroundResource(R.color.md_green_50);
        } else if (shoppingModel.getColor().equals("Red")){
            colorview.setBackgroundResource(R.color.md_red_400);
            shopCardColor.setBackgroundResource(R.color.md_red_50);


        } else if (shoppingModel.getColor().equals("Blue")){
            colorview.setBackgroundResource(R.color.md_blue_400);
            shopCardColor.setBackgroundResource(R.color.md_blue_50);


        } else if (shoppingModel.getColor().equals("Black")){
            colorview.setBackgroundResource(R.color.md_blue_grey_400);
            shopCardColor.setBackgroundResource(R.color.md_blue_grey_50);


        } else {
            colorview.setBackgroundResource(R.color.md_yellow_400);
            shopCardColor.setBackgroundResource(R.color.md_yellow_50);

        }


    }




    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView time;
        TextView item;
        TextView color;
        View colorView;
        LinearLayout shopCardColor;



        public MyViewHolder(View itemView){

            super(itemView);

             title = itemView.findViewById(R.id.itemTitle);
             item = itemView.findViewById(R.id.singleItem);
             time = itemView.findViewById(R.id.time);
             color = itemView.findViewById(R.id.color);
             colorView = itemView.findViewById(R.id.colorView);
             shopCardColor = itemView.findViewById(R.id.shopCardColor);

             itemView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {

                     return false;

                 }
             });

             itemView.setOnClickListener(this);






        }

        @Override
        public void onClick(View v) {

            Intent update = new Intent(context, UpdateShopList.class);
            update.putExtra("id",shopList.get(getAdapterPosition()).getId() + "");
            update.putExtra("title",shopList.get(getAdapterPosition()).getTitle());
            update.putExtra("time",shopList.get(getAdapterPosition()).getTime());
            update.putExtra("color",shopList.get(getAdapterPosition()).getColor());
            update.putExtra("date",shopList.get(getAdapterPosition()).getDate());
            update.putExtra("item",shopList.get(getAdapterPosition()).getItems());
            update.putExtra("list",shopList.get(getAdapterPosition()).getItems());


            context.startActivity(update);
        }
    }

}
