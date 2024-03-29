package com.example.jaloliddin.bestblog.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaloliddin.bestblog.R;
import com.example.jaloliddin.bestblog.adapter.MyRecyclerAdapter;
import com.example.jaloliddin.bestblog.models.ModelRecycler;
import com.example.jaloliddin.bestblog.networks.ServerConnnectionServiceR1;
import com.example.jaloliddin.bestblog.retment.RetMet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FrMostLiked extends Fragment {

    private RecyclerView frMostLikedRecVW;
    private List<ModelRecycler> modelRecyclers;
    private MyRecyclerAdapter myRecyclerAdapter;

    public FrMostLiked() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_most_liked, container, false);
        findListener(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addData();
    }

    private void findListener(View view) {
        modelRecyclers =new ArrayList<>();

        frMostLikedRecVW = (RecyclerView) view.findViewById(R.id.frMostLikedRecVW);
        frMostLikedRecVW.setHasFixedSize(false);
        frMostLikedRecVW.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addData() {

        if (RetMet.getInstance(getActivity()).checkInternetConnection()) {
            ServerConnnectionServiceR1.getInstance().getServerApiR1().readMostLiked(
                    "best-blog-most-liked",
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
                            Log.d("MyTag", getClass().getName() + ">> :retrofit:Error "+error.getMessage());
//                            RetMet.getInstance(getActivity()).alertDialogBuilder(null, "Problem with Internet", true);

                        }
                    }
            );
        }
        /*
        modelRecyclers=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            modelRecyclers.add(new ModelRecycler("Title","Desc","1500","1000","100","1 December 2018"));
        }
        myRecyclerAdapter=new MyRecyclerAdapter(getActivity(),modelRecyclers);
        frMostLikedRecVW.setAdapter(myRecyclerAdapter);
*/
    }

    private void jsonParser(String json) {
//        Log.d("MyTag", getClass().getName() + ">> Json: " + json);

        try {

            String title = "", description = "", time = "";
            int id = 0, likes = 0, see = 0, comment_count;

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                id = object.getInt("id");
                title = object.getString("title");
                description = object.getString("description");
                likes = object.getInt("likes");
                comment_count = object.getInt("commentCount");
                see = object.getInt("see");
                time = object.getString("time");
//                Log.d("MyTag", getClass().getName() + ">> jsonParserID: " + id);

                modelRecyclers.add(new ModelRecycler(id,title, description, likes, comment_count,  see, time));
            }
        } catch (JSONException e) {
            Log.d("MyTag", getClass().getName() + ">> JsonException:"+e.getMessage());

            e.printStackTrace();
        }
        myRecyclerAdapter = new MyRecyclerAdapter(getActivity(), modelRecyclers);
        frMostLikedRecVW.setAdapter(myRecyclerAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (modelRecyclers.size() != 0) {
            myRecyclerAdapter.clear();
        }
    }
}
