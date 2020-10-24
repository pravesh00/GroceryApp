package com.five5.groceryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link categoryfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class categoryfrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<category> categories= new ArrayList<>();
    categoryAdapter c;
    RecyclerView recyclerView;


    public categoryfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment categoryfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static categoryfrag newInstance(String param1, String param2) {
        categoryfrag fragment = new categoryfrag();
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
        View v= inflater.inflate(R.layout.fragment_categoryfrag, container, false);
        recyclerView= v.findViewById(R.id.CategoryRecycler);
        c= new categoryAdapter(categories,getParentFragmentManager());
        GridLayoutManager g=new GridLayoutManager(v.getContext(),2);
        recyclerView.setLayoutManager(g);
        categories.add(new category("Fruits"));
        categories.add(new category("Vegetables"));
        categories.add(new category("Juice"));
        categories.add(new category("Grains"));
        categories.add(new category("Fruits"));
        categories.add(new category("Vegetables"));
        categories.add(new category("Juice"));
        categories.add(new category("Grains"));
        recyclerView.setAdapter(c);
        return v;
    }
}