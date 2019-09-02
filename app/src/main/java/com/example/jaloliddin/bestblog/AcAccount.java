package com.example.jaloliddin.bestblog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.jaloliddin.bestblog.fragments.FrProfile;
import com.example.jaloliddin.bestblog.fragments.FrReg;
import com.example.jaloliddin.bestblog.retment.RetMet;

public class AcAccount extends AppCompatActivity {

    private String appData = "";
    private String appCom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ac_account);

        setAppData(getIntent().getStringExtra("appData"));
        setAppCom(getIntent().getStringExtra("appCom"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getAppData().equals("reg")) {
            RetMet.getInstance(this)
                    .replaceFragment(getSupportFragmentManager(), new FrReg(), R.id.acAccountBasicContainer);

        } else if (getAppData().equals("profile")) {
            RetMet.getInstance(this)
                    .replaceFragment(getSupportFragmentManager(), new FrProfile(), R.id.acAccountBasicContainer);
        }

    }

//    private void frDefault() {
//        RetMet.getInstance(this)
//                .replaceFragment(getSupportFragmentManager(), new FrProfile(), R.id.acAccountBasicContainer);
//    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }

    public String getAppCom() {
        return appCom;
    }

    public void setAppCom(String appCom) {
        this.appCom = appCom;
    }
}
