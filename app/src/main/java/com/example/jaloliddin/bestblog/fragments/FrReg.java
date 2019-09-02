package com.example.jaloliddin.bestblog.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaloliddin.bestblog.AcAccount;
import com.example.jaloliddin.bestblog.AcRead;
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


public class FrReg extends Fragment {

    private EditText frRegUserNameEditText, frRegPasswordEditText, frRegConfirmEditText;
    private Button frRegSignInButton;
    private NestedScrollView frRegScrollView;
    private ProgressBar frRegProgressBar;
    private TextView frRegSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_reg, container, false);

        findListener(view);

        return view;
    }

    private void findListener(View v) {
        frRegSignUp=(TextView) v.findViewById(R.id.frRegSignUp);
        frRegSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetMet.getInstance(getContext()).replaceFragment(getActivity().getSupportFragmentManager(),new FrLogin(),R.id.acAccountBasicContainer);
            }
        });
        frRegUserNameEditText = (EditText) v.findViewById(R.id.frRegUserNameEditText);
        frRegPasswordEditText = (EditText) v.findViewById(R.id.frRegPasswordEditText);
        frRegConfirmEditText = (EditText) v.findViewById(R.id.frRegConfirmEditText);
        frRegSignInButton = (Button) v.findViewById(R.id.frRegSignInButton);
        frRegScrollView = (NestedScrollView) v.findViewById(R.id.frRegScrollView);
        frRegScrollView.setVisibility(View.VISIBLE);
        frRegProgressBar = (ProgressBar) v.findViewById(R.id.frRegProgressBar);
        frRegProgressBar.setVisibility(View.GONE);
        frRegSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToServer();
            }
        });
    }

    private void addDataToServer() {
        if (RetMet.getInstance(getActivity()).checkInternetConnection()) {
            if (checkEditText()) {
                visibilityPBar(true);
                ServerConnnectionServiceR1.getInstance().getServerApiR1().registration(
                        frRegUserNameEditText.getText().toString(),
                        frRegPasswordEditText.getText().toString(),
                        new Callback<Response>() {
                            String out = " ";

                            @Override
                            public void success(Response response, Response response2) {
                                try {
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                    out = reader.readLine();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                jsonParser(out);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("MyTag", getClass().getName() + ">> failure:RetrofitError= "
                                        + error.getMessage());
                                visibilityPBar(false);
                            }
                        }
                );
            }
        } else {
            RetMet.getInstance(getActivity()).toast(R.string.problem_internet, 1);
        }
    }

    private void jsonParser(String json) {
        Log.d("MyTag", getClass().getName() + ">> jsonParser: JSON=" + json);

        try {

            int id = 0;
            String user_name = " ", pass = " ";

            JSONArray jsonArray = new JSONArray(json);
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

        visibilityPBar(false);

    }

    private boolean checkEditText() {
        String error = "";
        if (TextUtils.isEmpty(frRegUserNameEditText.getText().toString())) {
            error += "\n User Name empty \n";
        }
        if (TextUtils.isEmpty(frRegPasswordEditText.getText().toString())) {
            error += "\n Password empty \n";
        }
        if (TextUtils.isEmpty(frRegConfirmEditText.getText().toString())) {
            error += "\n Confirm empty \n";
        }
        if (!frRegPasswordEditText.getText().toString().equals(frRegConfirmEditText.getText().toString())) {
            error += "\n Password does not match \n";
        }
        if (!error.equals("")) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void visibilityPBar(boolean visibilityPBar) {
        if (visibilityPBar) {
            frRegScrollView.setVisibility(View.GONE);
            frRegProgressBar.setVisibility(View.VISIBLE);
        } else {
            frRegProgressBar.setVisibility(View.GONE);
            frRegScrollView.setVisibility(View.VISIBLE);
        }
    }

}
