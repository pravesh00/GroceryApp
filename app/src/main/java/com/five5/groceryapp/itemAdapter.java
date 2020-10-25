package com.five5.groceryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

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
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(curr.getId(),"email");
            }
        });

    }

    private void add(int id, String email) {
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference().child("Cart");
        Query query =mref.child(email).orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    for(DataSnapshot d:snapshot.getChildren()){
                        int n=Integer.parseInt(d.child("quantity").getValue().toString())+1;
                        mref.child(email).child(d.getKey()).child("quantity").setValue(n);
                    }else{
                    HashMap<String, Integer> map=new HashMap<>();
                    map.put("id",id);
                    map.put("quantity",1);
                    mref.child(email).push().setValue(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        FloatingActionButton add;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.itemImage);
            name=(TextView) itemView.findViewById(R.id.itemName);
            Rate=(TextView) itemView.findViewById(R.id.itemRate);
            addToCart=(ImageButton) itemView.findViewById(R.id.btnAdd);
            add=itemView.findViewById(R.id.btnAdd);
        }
    }
}
