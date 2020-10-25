package com.five5.groceryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemViewHolder>
{
    ArrayList<item> items;
    FragmentManager fg;

    public itemAdapter(ArrayList<item> items, FragmentManager fg) {
        this.items = items;
        this.fg = fg;
    }

    public itemAdapter() {
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        item curr=items.get(position);
        holder.name.setText(curr.getName());
        holder.Rate.setText("Rs. "+curr.getRate()+"/Kg");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_frag frag= new item_frag();
                Bundle n = new Bundle();
                n.putInt("itemId",curr.getId());
                frag.setArguments(n);
                fg.beginTransaction().replace(R.id.holder,frag).commit();



            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView Rate;
        ImageButton addToCart;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.itemImage);
            name=(TextView) itemView.findViewById(R.id.itemName);
            Rate=(TextView) itemView.findViewById(R.id.itemRate);
            addToCart=(ImageButton) itemView.findViewById(R.id.btnAdd);
        }
    }
}
