package com.five5.groceryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    itemAdapter itemAdapte;
    ArrayList<item> items= new ArrayList<>();
    DatabaseReference mRef;
    SearchView searchView;

    public searchFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static searchFrag newInstance(String param1, String param2) {
        searchFrag fragment = new searchFrag();
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
        mRef= FirebaseDatabase.getInstance().getReference();
        loadProducts();

    }

    private void checkProducts(String s) {
        Query query=mRef.child("Products").orderByChild("name").startAt(s).endAt(s+"\uf8ff");
                query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();

                for(DataSnapshot d:snapshot.getChildren()){
                    item n = new item(d.child("name").getValue().toString(),Integer.parseInt(d.child("rate").getValue().toString()),d.child("info").getValue().toString(),d.child("category").getValue().toString(),Integer.parseInt(d.child("id").getValue().toString()));
                    items.add(n);
                }
                itemAdapte.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadProducts() {
        mRef.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();

                for(DataSnapshot d:snapshot.getChildren()){
                    item n = new item(d.child("name").getValue().toString(),Integer.parseInt(d.child("rate").getValue().toString()),d.child("info").getValue().toString(),d.child("category").getValue().toString(),Integer.parseInt(d.child("id").getValue().toString()));
                    items.add(n);
                }
                itemAdapte.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView=(RecyclerView) v.findViewById(R.id.searchitem);
        recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(),2));
        itemAdapte= new itemAdapter(items,getParentFragmentManager());
        items.add(new item("Apples",80,"","",1));
        items.add(new item("Banana",90,"","",2));
        items.add(new item("Oranges",110,"","",3));
        items.add(new item("Tomato",130,"","",4));
        searchView =(SearchView)v.findViewById(R.id.searchbox);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                checkProducts(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                checkProducts(s);
                return true;
            }
        });
        recyclerView.setAdapter(itemAdapte);
        return v;
    }
}