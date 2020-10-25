package com.five5.groceryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link item_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class item_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView rate,iname,info;
    DatabaseReference mRef;
    FloatingActionButton btn;
    Spinner spin;
    int quantity=1;

    public item_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment item_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static item_frag newInstance(String param1, String param2) {
        item_frag fragment = new item_frag();
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
                    map.put("quantity",quantity);
                    mref.child(email).push().setValue(map);
                }
                getParentFragmentManager().beginTransaction().replace(R.id.holder,new CartFragment()).commit();
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
        View v= inflater.inflate(R.layout.fragment_item_frag, container, false);
        iname=(TextView)v.findViewById(R.id.name);
        rate=(TextView)v.findViewById(R.id.itemrate);
        info=(TextView)v.findViewById(R.id.info) ;
        btn=(FloatingActionButton) v.findViewById(R.id.buttonaddn);
        spin=v.findViewById(R.id.spinner);
        String[] ar={"1","2","3","4","5"};
        ArrayAdapter a= new ArrayAdapter(v.getContext(),R.layout.support_simple_spinner_dropdown_item,ar);
        a.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spin.setAdapter(a);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(id,"email");
            }


        });
        Bundle d= getArguments();
        id= d.getInt("itemId");

        setUI(id);
        quantity=spin.getSelectedItemPosition()+1;
        return v;
    }

    private void spinT(View v) {


    }

    private void setUI(int name) {
        mRef=FirebaseDatabase.getInstance().getReference();
        Query query=mRef.child("Products").orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    iname.setText(d.child("name").getValue().toString());
                    info.setText(d.child("info").getValue().toString());
                    rate.setText("Rs."+d.child("rate").getValue().toString()+"/Kg");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}