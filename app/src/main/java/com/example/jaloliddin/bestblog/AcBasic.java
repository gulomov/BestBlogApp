package com.example.jaloliddin.bestblog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jaloliddin.bestblog.fragments.FrBasic;
import com.example.jaloliddin.bestblog.retment.RetMet;
import com.example.jaloliddin.bestblog.shp.SharedPreference;

public class AcBasic extends AppCompatActivity
        {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ac_basic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPreference.getInstance(AcBasic.this).isServerReg()) {
                    startActivity(new Intent(AcBasic.this, AcWrite.class));
                } else {
                    startActivity(new Intent(AcBasic.this, AcAccount.class)
                            .putExtra("appData", "reg"));
                }
            }
        });



        frDefault();
    }

    @Override
    public void onBackPressed() {
            if (SharedPreference.getInstance(this).isBackPressed()) {
                exitApp();
            } else {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count == 0) {
                    exitApp();
                } else {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }

    private void exitApp() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.exit_the_Problem))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ac_basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_account) {
            if (SharedPreference.getInstance(this).isServerReg()) {
                startActivity(new Intent(this, AcAccount.class).putExtra("appData", "profile"));
            } else {
                startActivity(new Intent(this, AcAccount.class).putExtra("appData", "reg"));
            }
            Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_share) {
            Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_rate) {
            Toast.makeText(this, "rate", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_settings) {
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void frDefault() {
        RetMet.getInstance(this)
                .replaceFragment(getSupportFragmentManager(), new FrBasic(), R.id.acBasicContentConsL);
    }

}
