package com.five5.groceryapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class pageAdapter extends FragmentStatePagerAdapter {


    public pageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    { Fragment frag = null;
        switch(position){
        case 0: frag= new categoryfrag();
        break;
        case 1: frag= new vcategory_frag();
        break;
            case 2: frag= new vcategory_frag();
            break;
    }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Categories";
        else if (position == 1)
            title = "Budget Friendly";
        else if (position == 2)
            title = "Freshly Added";
        return title;
    }
}
