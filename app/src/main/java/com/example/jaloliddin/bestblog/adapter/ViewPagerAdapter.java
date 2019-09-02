package com.example.jaloliddin.bestblog.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment FRAGMENTS[];
    private String TITLES[];


    public ViewPagerAdapter(FragmentManager fm, Fragment[] FRAGMENTS,String[] TITLES) {
        super(fm);
        this.FRAGMENTS=FRAGMENTS;
        this.TITLES=TITLES;
    }

    //qaysi masivda turganiga qarab positiondi qaytaradi
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=FRAGMENTS[position];
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENTS.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String TITLE=TITLES[position];
        return TITLE;
    }
}
