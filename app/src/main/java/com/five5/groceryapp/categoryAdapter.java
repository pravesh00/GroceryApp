package com.five5.groceryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryViewholder> {
    ArrayList<category> categories;
    FragmentManager fg;

    public categoryAdapter(ArrayList<category> categories, FragmentManager fg) {
        this.categories = categories;
        this.fg = fg;
    }

    public categoryAdapter() {
    }

    @NonNull
    @Override
    public categoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent,false);
        return new categoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewholder holder, int position) {
        category curr= categories.get(position);
        holder.name.setText(curr.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFrag frag= new searchFrag();
                Bundle n = new Bundle();
                n.putString("Category",curr.getName());
                frag.setArguments(n);
                fg.beginTransaction().replace(R.id.holder,frag).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class categoryViewholder extends RecyclerView.ViewHolder{
        TextView name;

        public categoryViewholder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.CategoryName);
        }
    }
}
