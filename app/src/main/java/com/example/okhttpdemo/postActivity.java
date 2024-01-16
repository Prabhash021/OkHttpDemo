package com.example.okhttpdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class postActivity extends AppCompatActivity {
    OkHttpClient client;

    String postURL = "https://reqres.in/api/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        client = new OkHttpClient();

        TextView tName = findViewById(R.id.txtName);
        TextView tRole = findViewById(R.id.txtRole);
        TextView tResult = findViewById(R.id.txtResult);

        Button sendBtn = findViewById(R.id.btnSend);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String name = tName.getText().toString();
                String role = tRole.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("name",name)
                        .add("job", role)
                        .build();

                Request request = new Request.Builder().url(postURL).post(requestBody).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        progressBar.setVisibility(View.INVISIBLE);
                        String responseData = response.body().string();

                        Log.e("postActivity", responseData);

                        postActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tResult.setText(responseData);
                            }
                        });
                    }
                });
            }
        });
    }
}