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

public class cartAdapter1 extends RecyclerView.Adapter<cartAdapter1.itemViewHolder> {
    ArrayList<item> items;
    FragmentManager fg;

    public cartAdapter1(ArrayList<item> items) {
        this.items = items;

    }

    public cartAdapter1() {
    }

    @NonNull
    @Override
    public cartAdapter1.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout,parent,false);
        return new cartAdapter1.itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull cartAdapter1.itemViewHolder holder, int position) {
        item curr=items.get(position);
        holder.name.setText(curr.getName());
        holder.Rate.setText("Rs."+curr.getRate()+"/Kg");
        int t=curr.getRate()*curr.getQuantity();
        holder.qty.setText(curr.getQuantity()+"");
        holder.total.setText(t+"");


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView Rate;
        TextView qty;
        TextView total;


        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.itemImageCartcheck);
            name=(TextView) itemView.findViewById(R.id.txtNamecheck);
            Rate=(TextView) itemView.findViewById(R.id.txtRateckeck);
            qty=itemView.findViewById(R.id.quantity);
            total=itemView.findViewById(R.id.total1);



        }
    }
}
