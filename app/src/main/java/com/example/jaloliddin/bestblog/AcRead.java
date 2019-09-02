package com.example.jaloliddin.bestblog;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaloliddin.bestblog.adapter.MylRecyclerAdapterComments;
import com.example.jaloliddin.bestblog.database.DbHelper;
import com.example.jaloliddin.bestblog.models.ModelRecyclerComments;
import com.example.jaloliddin.bestblog.networks.ServerConnnectionServiceR1;
import com.example.jaloliddin.bestblog.retment.RetMet;
import com.example.jaloliddin.bestblog.shp.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AcRead extends AppCompatActivity {

    private RecyclerView acReadRecyclerVW;
    private List<ModelRecyclerComments> modelRecyclerCommentsList;
    private MylRecyclerAdapterComments mylRecyclerAdapterComments;

    private int postID = 0;
    private int storyID = 0;
    private int likesCount = 0;
    private EditText acReadCommentEditT;
    private TextView acReadByTv, acReadTimeTv, acReadTitleTv, acReadDescTv, acReadLikeTv, acReadSeeTv;
    private ImageView acReadCommentSendIc;
    private LinearLayout acReadLinearLayLikeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_read);
        findLister();
        setPostID(getIntent().getIntExtra("postID", 0));

    }


    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer();

        addCommentToServer(false);
    }

    private void findLister() {
        acReadLinearLayLikeTv = (LinearLayout) findViewById(R.id.acReadLinearLayLikeTv);
        acReadLinearLayLikeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreference.getInstance(getApplicationContext()).isServerReg()) {
                    sendLikeCCC();
                } else {
                    startActivity(new Intent(getApplicationContext(), AcAccount.class)
                            .putExtra("appData", "reg").putExtra("appCom", "Com"));
                }
            }
        });
        acReadCommentSendIc = (ImageView) findViewById(R.id.acReadCommentSendIc);
        acReadCommentEditT = (EditText) findViewById(R.id.acReadCommentEditT);
        acReadRecyclerVW = (RecyclerView) findViewById(R.id.acReadRecyclerView);
        acReadRecyclerVW.setHasFixedSize(false);
        acReadRecyclerVW.setLayoutManager(new LinearLayoutManager(this));
        acReadByTv = (TextView) findViewById(R.id.acReadByTv);
        acReadTimeTv = (TextView) findViewById(R.id.acReadTimeTv);
        acReadTitleTv = (TextView) findViewById(R.id.acReadTitleTv);
        acReadDescTv = (TextView) findViewById(R.id.acReadDescTv);
        acReadLikeTv = (TextView) findViewById(R.id.acReadLikeTv);
        acReadSeeTv = (TextView) findViewById(R.id.acReadSeeTv);
    }


    private void sendLikeCCC(){

        int likeccc=getLikesCount()+1;
        String colId=DbHelper.getInstance(this).getUserData()[0];

        if (RetMet.getInstance(this).checkInternetConnection()){
            ServerConnnectionServiceR1.getInstance().getServerApiR1().checkLike(
                    likeccc,
                    colId,
                    getStoryID(),
                    new Callback<Response>() {
                         String out="";

                        @Override
                        public void success(Response response, Response response2) {
                            try {
                                BufferedReader reader= new BufferedReader(new  InputStreamReader(response.getBody().in()));
                                out=reader.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            jsonParserLike(out);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("MyTag", getClass().getName() + ">> retrofit:Error " + error.getMessage());

                        }
                    }
            );
        }else{
            Log.d("MyTag", getClass().getName() + ">> checkingInternet: " + RetMet.getInstance(this).checkInternetConnection());
            RetMet.getInstance(this).alertDialogBuilder(null, "Problem with Internet", true);

        }



    }

    private void jsonParserLike(String out) {
        Log.d("MyTag", getClass().getName() + ">> LikeCCC :" + out);

    }

    //
//    private void sendLikeToServer() {
//        int likeCount = getLikesCount() + 1;
//        String colID = DbHelper.getInstance(this).getUserData()[0];
//
//        if (RetMet.getInstance(this).checkInternetConnection()) {
//
//            ServerConnnectionServiceR1.getInstance().getServerApiR1().likes(
//                    likeCount,
//                    colID,
//                    getStoryID(),
//                    new Callback<Response>() {
//
//                        String out = "";
//
//                        @Override
//                        public void success(Response response, Response response2) {
//                            try {
//
//                                Log.d("MyTag", getClass().getName() + ">> : Url = " + response.getUrl());
//                                Log.d("MyTag", getClass().getName() + ">> : Status = " + response.getStatus());
//
//                                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
//                                out = reader.readLine();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            jsonParserForLikes(out);
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//                            Log.d("MyTag", getClass().getName() + ">> retrofit:Error " + error.getMessage());
//
//                        }
//                    }
//            );
//        } else {
//            Log.d("MyTag", getClass().getName() + ">> checkingInternet: " + RetMet.getInstance(this).checkInternetConnection());
//            RetMet.getInstance(this).alertDialogBuilder(null, "Problem with Internet", true);
//
//        }
//    }
//
//    private void jsonParserForLikes(String out) {
//        Log.d("MyTag", getClass().getName() + ">> Likes Status: " + out);}

    private void getDataFromServer() {
        if (RetMet.getInstance(this).checkInternetConnection()) {
            ServerConnnectionServiceR1.getInstance().getServerApiR1().postDataById(
                    getPostID(),
                    new Callback<Response>() {
                        String out = "";

                        @Override
                        public void success(Response response, Response response2) {
                            try {
                                BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(response.getBody().in()));
                                out = reader.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            jsonParser(out);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("MyTag", getClass().getName() + ">> retrofit:Error ");
                        }

                    }
            );
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.problem_internet));
            builder.create().show();
        }
    }

    private void jsonParser(String out) {
//        Log.d("MyTag", getClass().getName() + ">> outpur: " + out);

//        [{"id":"12","userId":"1","title":"ghugvkhf","description":"gjfmmj","likes":"15","commentId":"0","commentCount":"0","see":"0","time":"18 Aug 2019","user_name":"jalol"}]

        String title = "", description = "", time = "", user_name = "";
        int id = 0, userId = 0, likes = 0, commentCount = 0, see = 0;
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(out);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getInt("id");
                userId = jsonObject.getInt("userId");
                title = jsonObject.getString("title");
                description = jsonObject.getString("description");
                likes = jsonObject.getInt("likes");
                commentCount = jsonObject.getInt("commentCount");
                see = jsonObject.getInt("see");
                time = jsonObject.getString("time");
                user_name = jsonObject.getString("user_name");
            }
            acReadByTv.setText("By " + user_name);
            acReadTimeTv.setText(time);
            acReadTitleTv.setText(title);
            acReadDescTv.setText(description);
            acReadLikeTv.setText("" + likes);
            acReadSeeTv.setText("" + see);

            setStoryID(id);
            Log.d("MyTag", getClass().getName() + ">> setPostID : " + id);

            setLikesCount(likes);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }


    public void sendComment(View view) {
        if (SharedPreference.getInstance(getApplicationContext()).isServerReg()) {
            if (isEmpty()) {
                addCommentToServer(true);
            }
        } else {
            startActivity(new Intent(getApplicationContext(), AcAccount.class)
                    .putExtra("appData", "reg").putExtra("appCom", "Com"));
        }

    }


    private void addCommentToServer(boolean sendComment) {
        String colID = DbHelper.getInstance(getApplicationContext()).getUserData()[0];
        String userName = DbHelper.getInstance(getApplicationContext()).getUserData()[1];

        if (sendComment) {
            if (RetMet.getInstance(this).checkInternetConnection()) {
                ServerConnnectionServiceR1.getInstance().getServerApiR1().postComment(
                        "" + userName,
                        getPostID(),
                        "" + acReadCommentEditT.getText().toString(),
                        "" + colID,

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
                                jsonParserForComment(out);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("MyTag", getClass().getName() + ">> failure:RetrofitError= "
                                        + error.getMessage());
                            }
                        }
                );
            }
        } else {

            if (RetMet.getInstance(this).checkInternetConnection()) {
                ServerConnnectionServiceR1.getInstance().getServerApiR1().getComments(
                        getPostID(),
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
                                jsonParserForComment(out);
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

    private void jsonParserForComment(String out) {
        Log.d("MyTag", getClass().getName() + ">> Json: " + out);
        if (out.length() != 0) {
            modelRecyclerCommentsList = new ArrayList<>();

//            Json: [{"id":"7","userName":"By e","comment":"fhg","time":"23 Aug 2019"}
            String userName = "", comment = "", time = "";

            try {
                JSONArray jsonArray = new JSONArray(out);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    userName = jsonObject.getString("userName");
                    comment = jsonObject.getString("comment");
                    time = jsonObject.getString("time");

                    modelRecyclerCommentsList.add(new ModelRecyclerComments(userName, comment, time));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mylRecyclerAdapterComments = new MylRecyclerAdapterComments(this, modelRecyclerCommentsList);
            acReadRecyclerVW.setAdapter(mylRecyclerAdapterComments);


        }


    }

    private boolean isEmpty() {
        String emt = "";
        if (acReadCommentEditT.getText().toString().equals("")) {
            emt += "\n Comment is empty";
        }
        if (emt.equals("")) {
            return true;
        } else {
            Toast.makeText(this, emt, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public int getStoryID() {
        return storyID;
    }

    public void setStoryID(int storyID) {
        this.storyID = storyID;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
