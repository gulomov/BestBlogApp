package com.example.jaloliddin.bestblog.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jaloliddin.bestblog.MainActivity;
import com.example.jaloliddin.bestblog.R;
import com.example.jaloliddin.bestblog.database.DbHelper;
import com.example.jaloliddin.bestblog.retment.RetMet;
import com.example.jaloliddin.bestblog.shp.SharedPreference;

public class FrProfile extends Fragment {

    private ImageView frProfileBackImageOne,BackArrowImageTwo;

    public FrProfile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_profile, container, false);


        frProfileBackImageOne=(ImageView) view.findViewById(R.id.frProfileBackImageOne);
        frProfileBackImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        BackArrowImageTwo=(ImageView) view.findViewById(R.id.BackArrowImageTwo);
        BackArrowImageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(getResources().getString(R.string.del_acc))
                        .setCancelable(true)
                        .setPositiveButton(getResources().getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DbHelper.getInstance(getActivity()).deleteUserData();
                                        SharedPreference.getInstance(getActivity()).setServerReg(false);
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        getActivity().finish();
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();


            }
        });
        return view;

    }

}
