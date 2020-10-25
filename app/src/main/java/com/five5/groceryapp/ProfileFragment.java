package com.five5.groceryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText name,email,phone;
    TextView address;
    DatabaseReference mRef;
    ImageButton edit;
    String nameU;
    View view;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        ui();


    }

    private void ui() {
        mRef= FirebaseDatabase.getInstance().getReference().child("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d:snapshot.getChildren()){
                    name.setText(d.child("name").getValue().toString());
                    phone.setText(d.child("phone").getValue().toString());
                    email.setText(d.child("email").getValue().toString());
                    nameU=d.child("email").getValue().toString();
                    address.setText(d.child("Address").getValue().toString());
                }
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
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        name=v.findViewById(R.id.username);
        email=v.findViewById(R.id.email);
        phone=v.findViewById(R.id.phone);
        address=v.findViewById(R.id.address);
        edit=v.findViewById(R.id.edit);
        view=v;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.isEnabled()){
                    name.setEnabled(false);
                    email.setEnabled(false);
                    phone.setEnabled(false);
                    edit.setImageResource(R.drawable.edit);
                    changeData(name.getText().toString(),email.getText().toString(),phone.getText().toString());
                }else{
                name.setEnabled(true);
                name.requestFocus();
                email.setEnabled(false);
                phone.setEnabled(true);
                edit.setImageResource(R.drawable.done);}

            }
        });
        return v;
    }

    private void changeData(String toString, String toString1, String toString2) {
        Query query= FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(nameU);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    FirebaseDatabase.getInstance().getReference().child("Users").child(d.getKey()).child("name").setValue(toString);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(d.getKey()).child("email").setValue(toString1);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(d.getKey()).child("phone").setValue(toString2);
                    ui();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}