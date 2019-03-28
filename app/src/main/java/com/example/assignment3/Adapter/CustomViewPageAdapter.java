package com.example.assignment3.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;

import com.example.assignment3.MainActivity;
import com.example.assignment3.fragment.AddStudentFragment;
import com.example.assignment3.fragment.ShowStudentFragment;

public class CustomViewPageAdapter extends FragmentStatePagerAdapter {
    Fragment frag;
    String titles[]={"Students","Add/edit Student"};
    public CustomViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            frag = new ShowStudentFragment();
            Bundle bdl = new Bundle();
            bdl.putString("message", "Show Students");
            frag.setArguments(bdl);

            return frag;

        }
        if (i == 1) {
            frag = new AddStudentFragment();
            Bundle bdl = new Bundle();
            bdl.putString("message", "Show Students");
            frag.setArguments(bdl);
            return frag;
        }
        else return null;

    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
