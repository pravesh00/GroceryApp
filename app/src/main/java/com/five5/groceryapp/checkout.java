package com.five5.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class checkout extends AppCompatActivity {
    cartAdapter1 itemAdapte;
    ArrayList<item> items= new ArrayList<>();
    int total=0;
    TextView tot;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
      recyclerView=(RecyclerView) findViewById(R.id.checkoutcart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tot=findViewById(R.id.amount);
        cartIt("email");
        itemAdapte=new cartAdapter1(items);
        recyclerView.setAdapter(itemAdapte);
        Toolbar t= findViewById(R.id.toolba);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });





    }
    private void cartIt(String email) {
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("Cart").child(email);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total=0;
                items.clear();
                for(DataSnapshot d:snapshot.getChildren()){

                    addItemtocart(Integer.parseInt(d.child("id").getValue().toString()),Integer.parseInt(d.child("quantity").getValue().toString()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void addItemtocart(int id, int quantity) {
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("Products");
        Query query =mRef.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    item curr = new item(d.child("name").getValue().toString(),
                            Integer.parseInt(d.child("rate").getValue().toString()),
                            d.child("info").getValue().toString(),d.child("category").getValue().toString(),Integer.parseInt(d.child("id").getValue().toString()));
                    curr.setQuantity(quantity);
                    total=curr.getQuantity()*Integer.parseInt(d.child("rate").getValue().toString())+total;
                    tot.setText(total+"");
                    items.add(curr);
                    itemAdapte.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}