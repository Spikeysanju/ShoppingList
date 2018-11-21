package com.spikeysanju98gmail.shoppinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.spikeysanju98gmail.shoppinglist.R;
import com.spikeysanju98gmail.shoppinglist.UpdateShopList;
import com.spikeysanju98gmail.shoppinglist.realmmodels.ShoppingModel;
import com.spikeysanju98gmail.shoppinglist.realmmodels.Task;

import java.util.List;

public class TaskAdapter extends  RecyclerView.Adapter<TaskAdapter.MyViewHolder>{


    private List<Task> taskList;

    Context context;

    public TaskAdapter(Context context, List<Task> tasks){

        this.context = context;
        this.taskList = tasks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        final Task task = taskList.get(position);

        holder.item.setText(task.getItem());
        holder.quantity.setText(task.getQuantity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+task.getListID(), Toast.LENGTH_SHORT).show();
            }
        });




    }




    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item;
        TextView quantity;



        public MyViewHolder(View itemView){

            super(itemView);

            item = itemView.findViewById(R.id.items);
            quantity = itemView.findViewById(R.id.quantity);


             itemView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {

                     return false;

                 }
             });

//             itemView.setOnClickListener(this);






        }

//        @Override
//        public void onClick(View v) {
//
//            Toast.makeText(context, "Helloooo", Toast.LENGTH_SHORT).show();
//
//        }
    }




}
