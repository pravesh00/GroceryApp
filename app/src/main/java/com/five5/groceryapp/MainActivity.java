package com.five5.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    TabLayout category;
    FrameLayout holder;
    BottomNavigationView bottom;
    Toolbar toolbar;
    DrawerLayout dl;
    ActionBarDrawerToggle dt;
    NavigationView nv;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        holder=(FrameLayout)findViewById(R.id.Category);
        bottom=(BottomNavigationView)findViewById(R.id.bottomAppBar);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        dl=(DrawerLayout)findViewById(R.id.activity_main);
        dt= new ActionBarDrawerToggle(this,dl,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        dl.addDrawerListener(dt);
        nv=(NavigationView)findViewById(R.id.nv);
        View v=nv.getHeaderView(0);
        ImageButton b=(ImageButton)v.findViewById(R.id.cancel);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.closeDrawers();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                dl.closeDrawers();
                switch(item.getItemId()){
                    case R.id.nav_home:{getSupportFragmentManager().beginTransaction().replace(R.id.holder, new HomeFragment()).commit();
                        bottom.setSelectedItemId(R.id.bottom_home);

                        i=0;}

                    break;
                    case R.id.nav_cart : {getSupportFragmentManager().beginTransaction().replace(R.id.holder, new CartFragment()).commit();
                        toolbar.setNavigationIcon(R.drawable.back);
                        bottom.setSelectedItemId(R.id.bottom_cart);
                        i=1;
                    }
                    break;
                    case R.id.nav_orders: {getSupportFragmentManager().beginTransaction().replace(R.id.holder, new ProfileFragment()).commit();;
                        toolbar.setNavigationIcon(R.drawable.back);
                        bottom.setSelectedItemId(R.id.bottom_profile);
                        i=2;}
                    break;
                }
                return true;
            }
        });

        dt.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(bottom.getSelectedItemId()==R.id.bottom_home)
                dl.openDrawer(GravityCompat.START);
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.holder, new HomeFragment()).commit();
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
                    bottom.setSelectedItemId(R.id.bottom_home);
                }

            }
        });



        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.bottom_home:{getSupportFragmentManager().beginTransaction().replace(R.id.holder, new HomeFragment()).commit();;
                        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
                    i=0;}

                        break;
                    case R.id.bottom_cart : {getSupportFragmentManager().beginTransaction().replace(R.id.holder, new CartFragment()).commit();
                    toolbar.setNavigationIcon(R.drawable.back);
                    i=1;
                    }
                        break;
                    case R.id.bottom_profile: {getSupportFragmentManager().beginTransaction().replace(R.id.holder, new ProfileFragment()).commit();;
                        toolbar.setNavigationIcon(R.drawable.back);
                    i=2;}
                        break;
                }
                return true;
            }
        });



        getSupportFragmentManager().beginTransaction().replace(R.id.holder, new HomeFragment()).commit();


    }


}