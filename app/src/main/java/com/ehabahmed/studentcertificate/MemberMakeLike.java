package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MemberMakeLike extends AppCompatActivity {
String postId;
    ArrayList<member> listitems;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Retrofit retrofit;
    ApiConfig apiConfig;
    MemberGroupAdapter adapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_make_like);
        postId=getIntent().getExtras().getString("postId");
        recyclerView=findViewById(R.id.memberList);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listitems=new ArrayList<>();
        progressBar=findViewById(R.id.pb_member);
        getData();

    }
    private void getData() {
        retrofit=new Retrofit.Builder()
                .baseUrl("http://ehab01998.com").addConverterFactory(GsonConverterFactory.create())
                .build();
        apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.getMemberLike(postId).enqueue(new Callback<ArrayList<member>>() {
            @Override
            public void onResponse(Call<ArrayList<member>> call, Response<ArrayList<member>> response) {
                listitems=response.body();
                adapter=new MemberGroupAdapter(MemberMakeLike.this,listitems);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ArrayList<member>> call, Throwable t) {
                Toast.makeText(MemberMakeLike.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
}}
