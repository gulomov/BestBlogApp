package com.example.jaloliddin.bestblog.retment;

import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.widget.Toast;

import com.example.jaloliddin.bestblog.fragments.FrBasic;

public class RetMet {
    private static Context context;
    private static RetMet instance;

    public static RetMet getInstance(Context context) {
        if (instance == null) instance = new RetMet(context);
        return instance;
    }

    public RetMet(Context context) {
        this.context = context;
    }

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int container) {
        fragmentManager.beginTransaction().replace(container, fragment)
                .addToBackStack(null).commitAllowingStateLoss();


    }
    /*check internet connection*/

    public boolean checkInternetConnection() {

        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connect.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connect.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connect.getNetworkInfo(1).getState() ==
                        NetworkInfo.State.CONNECTING ||
                connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connect.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connect.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED
        ) {
            return false;
        }
        return false;
    }

    /*Toast*/
    public void toast(int massageId, int length) {
        if (length == 1) {
            Toast.makeText(context, context.getResources().getString(massageId), Toast.LENGTH_SHORT).show();
        }
        if (length == 2) {
            Toast.makeText(context, context.getResources().getString(massageId), Toast.LENGTH_LONG).show();

        }
    }

    public AlertDialog.Builder alertDialogBuilder(String title, String msg, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(isCancelable);
        builder.create().show();
        return builder;
    }
}
