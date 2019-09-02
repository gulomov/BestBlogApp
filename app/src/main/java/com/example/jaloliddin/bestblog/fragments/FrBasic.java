package com.example.jaloliddin.bestblog.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaloliddin.bestblog.R;
import com.example.jaloliddin.bestblog.adapter.ViewPagerAdapter;

public class FrBasic extends Fragment {

    TabLayout frBasicTabLayout;
    ViewPager frBasicViewPager;

    public FrBasic() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fr_basic, container, false);
        findListener(view);
        setupViewPager(frBasicViewPager);
        frBasicTabLayout.setupWithViewPager(frBasicViewPager);

        return view;

    }

    private void findListener(View v) {
        frBasicTabLayout = (TabLayout) v.findViewById(R.id.frBasicTabLayout);
        frBasicViewPager = (ViewPager) v.findViewById(R.id.frBasicViewPager);
    }

    //Fragment va titlelardi massiv qib olyabmiz
    //Fragmentlardi viewPagerga solib olyapmiz
    private void setupViewPager(ViewPager viewPager) {
        Fragment FRAGMENTS[] = {new FrRecent(), new FrMostLiked()};
        String TITLES[] = {
                getActivity().getResources().getString(R.string.recent),
                getActivity().getResources().getString(R.string.most_liked)
        };

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), FRAGMENTS, TITLES);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
