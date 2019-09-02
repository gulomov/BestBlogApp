package com.example.jaloliddin.bestblog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jaloliddin.bestblog.database.DbHelper;
import com.example.jaloliddin.bestblog.networks.ServerConnnectionServiceR1;
import com.example.jaloliddin.bestblog.retment.RetMet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AcWrite extends AppCompatActivity {

    private MenuItem sendMenuItem;
    private LinearLayout AcWriteLinearLay;
    private ProgressBar AcWriteProgressBar;
    private EditText acWriteTitleEt, acWriteDescEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ac_write);
        findListener();
        toolbar();

    }

    private void findListener() {
        acWriteTitleEt = (EditText) findViewById(R.id.acWriteTitleEt);
        acWriteDescEt = (EditText) findViewById(R.id.acWriteDescEt);
        AcWriteLinearLay = (LinearLayout) findViewById(R.id.AcWriteLinearLay);
        AcWriteProgressBar = (ProgressBar) findViewById(R.id.AcWriteProgressBar);
    }

    private void toolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.AcWriretoolbar);
        toolbar.setTitle(getResources().getString(R.string.write_story));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ac_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addPhoto) {
            Toast.makeText(this, "Add photo", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.addVoiceMes) {
            Toast.makeText(this, "Add Voice Message", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.send) {
            if (isEmpty()) {
                sendMenuItem = item;
                addDataToServer();
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private void addDataToServer() {

        String colID = DbHelper.getInstance(getApplicationContext()).getUserData()[0];
        Log.d("MyTag", getClass().getName() + ">> colID: " + colID);

        String colUserName = DbHelper.getInstance(getApplicationContext()).getUserData()[1];
        Log.d("MyTag", getClass().getName() + ">> colUserName: " + colUserName);

        String colPass = DbHelper.getInstance(getApplicationContext()).getUserData()[2];
        Log.d("MyTag", getClass().getName() + ">> colPass: " + colPass);

        if (RetMet.getInstance(this).checkInternetConnection()) {
            visibilityPBar(true);

            String userId = DbHelper.getInstance(this).getUserData()[0];

            Log.d("MyTag", getClass().getName() + ">> userId :" +userId);

            ServerConnnectionServiceR1.getInstance().getServerApiR1().postStory(


                    "" + acWriteTitleEt.getText().toString(),
                    "" + acWriteDescEt.getText().toString(),
                    userId,
                    //bu yerda serverdan javob qaytadi
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


                    });
        } else {
            RetMet.getInstance(this).toast(R.string.problem_internet, 1);
        }
    }

    private void jsonParser(String out) {
        Log.d("MyTag", getClass().getName() + ">> jsonParser: Out=" + out);
        visibilityPBar(false);
        if (out.length() != 0) {
            onBackPressed();
        }

    }

    private boolean isEmpty() {
        String error = "";
        if (acWriteTitleEt.getText().toString().equals("")) {
            error += "\n Title is empty";
        }
        if (acWriteDescEt.getText().toString().equals("")) {
            error += "\n Description is empty";
        }
        if (error.equals("")) {
            return true;
        } else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void visibilityPBar(boolean visibilityPBar) {
        if (visibilityPBar) {
            sendMenuItem.setVisible(false);
            AcWriteLinearLay.setVisibility(View.GONE);
            AcWriteProgressBar.setVisibility(View.VISIBLE);
        } else {
            sendMenuItem.setVisible(true);
            AcWriteProgressBar.setVisibility(View.GONE);
            AcWriteLinearLay.setVisibility(View.VISIBLE);
        }
    }
}
