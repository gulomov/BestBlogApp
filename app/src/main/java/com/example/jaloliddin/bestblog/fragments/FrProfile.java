package com.example.jaloliddin.bestblog.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaloliddin.bestblog.R;

public class FrProfile extends Fragment {


    public FrProfile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_profile, container, false);
        return view;
    }

}
