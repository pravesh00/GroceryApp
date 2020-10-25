package com.five5.groceryapp;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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
        holder.Rate.setText("Rs."+curr.getRate()+"/Kg");
        int t=curr.getRate()*curr.getQuantity();
        holder.total.setText(t+"");
        holder.add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(curr.getId());
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtract(curr.getId());
            }
        });


    }

    private void subtract(int id) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Cart").child("email").orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    int nee=Integer.parseInt(d.child("quantity").getValue().toString())-1;
                    if(nee>0)
                    FirebaseDatabase.getInstance().getReference().child("Cart").child("email").child(d.getKey()).child("quantity").setValue(nee);
                    else{
                        FirebaseDatabase.getInstance().getReference().child("Cart").child("email").child(d.getKey()).setValue(null);
                        checkifEmpty("email");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkifEmpty(String email) {
        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Cart").child("email");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    fg.beginTransaction().replace(R.id.holder,new HomeFragment()).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void add(int c) {
        Query query=FirebaseDatabase.getInstance().getReference().child("Cart").child("email").orderByChild("id").equalTo(c);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    int nee=Integer.parseInt(d.child("quantity").getValue().toString())+1;
                    FirebaseDatabase.getInstance().getReference().child("Cart").child("email").child(d.getKey()).child("quantity").setValue(nee);
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
        TextView total;
        ImageButton sub;
        ImageButton add1;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.itemImageCart);
            name=(TextView) itemView.findViewById(R.id.txtName);
            Rate=(TextView) itemView.findViewById(R.id.txtRate);
            total=(TextView)itemView.findViewById(R.id.total);
            add1=itemView.findViewById(R.id.addN);
            sub=itemView.findViewById(R.id.subtract);

        }
    }
}
