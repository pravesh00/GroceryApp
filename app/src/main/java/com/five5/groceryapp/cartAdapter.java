package com.five5.groceryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.itemViewHolder> {
    ArrayList<item> items;
    FragmentManager fg;

    public cartAdapter(ArrayList<item> items, FragmentManager fg) {
        this.items = items;
        this.fg = fg;
    }

    public cartAdapter() {
    }

    @NonNull
    @Override
    public cartAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        return new cartAdapter.itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull cartAdapter.itemViewHolder holder, int position) {
        item curr=items.get(position);
        holder.name.setText(curr.getName());
        holder.Rate.setText(curr.getRate());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView Rate;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.itemImageCart);
            name=(TextView) itemView.findViewById(R.id.txtName);
            Rate=(TextView) itemView.findViewById(R.id.txtRate);

        }
    }
}
