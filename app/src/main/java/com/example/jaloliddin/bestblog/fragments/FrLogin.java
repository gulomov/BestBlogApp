package com.example.jaloliddin.bestblog.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaloliddin.bestblog.AcAccount;
import com.example.jaloliddin.bestblog.AcWrite;
import com.example.jaloliddin.bestblog.R;
import com.example.jaloliddin.bestblog.database.DbHelper;
import com.example.jaloliddin.bestblog.networks.ServerConnnectionServiceR1;
import com.example.jaloliddin.bestblog.retment.RetMet;
import com.example.jaloliddin.bestblog.shp.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FrLogin extends Fragment {


    private EditText frLoginUserName, frLoginUserPass;
    private TextView frLoginSignUp;
    private Button frLogin;

    public FrLogin() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_login, container, false);
        findListener(view);
        return view;
    }

    private void findListener(View v) {
        frLoginSignUp = (TextView) v.findViewById(R.id.frLoginSignUp);
        frLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetMet.getInstance(getContext()).replaceFragment(getActivity().getSupportFragmentManager(), new FrReg(), R.id.acAccountBasicContainer);

            }
        });
        frLoginUserPass = (EditText) v.findViewById(R.id.frLoginUserPass);
        frLoginUserName = (EditText) v.findViewById(R.id.frLoginUserName);
        frLogin=(Button)v.findViewById(R.id.frLogin);
        frLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getDataFromServer();
                Log.d("MyTag", getClass().getName() + ">> onClickTest: ");

            }
        });
    }

    private void getDataFromServer() {
            if (RetMet.getInstance(getActivity()).checkInternetConnection()){
                if (checkEditText()){
                    Log.d("MyTag", getClass().getName() + ">> onClickTest: ");

                    ServerConnnectionServiceR1.getInstance().getServerApiR1().login(
                            frLoginUserName.getText().toString(),
                            frLoginUserPass.getText().toString(),
                            new Callback<Response>() {

                                String out="";

                                @Override
                                public void success(Response response, Response response2) {
                                    try {
                                        BufferedReader reader=new BufferedReader(new InputStreamReader(response.getBody().in()));
                                        out=reader.readLine();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    jsonParser(out);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("MyTag", getClass().getName() + ">> failure:RetrofitError= "
                                            + error.getMessage());
                                }
                            }

                    );
                }
            }
    }

    private void jsonParser(String out) {
        Log.d("MyTag", getClass().getName() + ">> :out = " + out);
        Log.d("MyTag", getClass().getName() + ">> : out.charAt = "+ out.charAt(0));
        if (out.charAt(0) == 'N'){
            Log.d("MyTag", getClass().getName() + ">> : NO ");

            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.Invalid),Toast.LENGTH_SHORT).show();

        }else {
//        [{"id":"17","user_name":"s","pass":"s"}]
            try {

                int id = 0;
                String user_name = " ", pass = " ";

                JSONArray jsonArray = new JSONArray(out);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);

                    id = jo.getInt("id");
                    user_name = jo.getString("user_name");
                    pass = jo.getString("pass");
                }

                boolean b = DbHelper.getInstance(getActivity()).insertUserData(id, user_name, pass);

                if (b) {
                    SharedPreference.getInstance(getActivity()).setServerReg(true);
                    if (((AcAccount) getActivity()).getAppData().equals("reg")) {

                        if (((AcAccount) getActivity()).getAppCom() != null){
                            if (((AcAccount) getActivity()).getAppCom().equals("Com")) {
                                getActivity().finish();
                            } else {
                                startActivity(new Intent(getActivity(), AcWrite.class));
                                getActivity().finish();
                            }
                        }else {
                            startActivity(new Intent(getActivity(), AcWrite.class));
                            getActivity().finish();

                        }

                    } else {
                        RetMet.getInstance(getActivity()).replaceFragment(getActivity().getSupportFragmentManager(),
                                new FrProfile(), R.id.acAccountBasicContainer);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    private boolean checkEditText() {
        String error = "";
        if (TextUtils.isEmpty(frLoginUserName.getText().toString())) {
            error += "\n User Name empty \n";
        }
        if (TextUtils.isEmpty(frLoginUserPass.getText().toString())) {
            error += "\n Password empty \n";
        }
        if (!error.equals("")) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }
}
