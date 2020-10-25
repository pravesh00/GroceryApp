package com.five5.groceryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    cartAdapter itemAdapte;
    ArrayList<item> items= new ArrayList<>();
    TextView tot;
    int total=0;
    LinearLayoutManager lm;
    TextView clear;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView=(RecyclerView) v.findViewById(R.id.cartrecycle);
        //recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
       lm=new LinearLayoutManager(v.getContext());
        itemAdapte= new cartAdapter(items,getParentFragmentManager());
        items.add(new item("Apples",80,"","",1));
        items.add(new item("Banana",90,"","",2));
        items.add(new item("Oranges",110,"","",3));
        items.add(new item("Tomato",130,"","",4));
        //recyclerView.setAdapter(itemAdapte);
        cartIt("email");
        tot=v.findViewById(R.id.totalCost);
        clear=v.findViewById(R.id.textView3);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mref=FirebaseDatabase.getInstance().getReference();
                mref.child("Cart").child("email").setValue(null);
                getParentFragmentManager().beginTransaction().replace(R.id.holder,new HomeFragment()).commit();
                total=0;
                tot.setText("0");
            }
        });
        if(items.size()>0)
        {}
        else{
            tot.setText("0");
            total=0;
        }


        return v;
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

    private void okRecycle() {
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(itemAdapte);
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
                if(items.size()>0)
                    okRecycle();
                else{
                    tot.setText("0");
                    total=0;
                    getParentFragmentManager().beginTransaction().replace(R.id.holder,new HomeFragment()).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}