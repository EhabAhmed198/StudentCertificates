package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowMemberGroup extends AppCompatActivity {
String GroupId;
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
        setContentView(R.layout.activity_show_member_group);
        recyclerView=findViewById(R.id.memberList);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        GroupId=getIntent().getExtras().getString("GroupId");
        listitems=new ArrayList<>();
        progressBar=findViewById(R.id.pb_member);
        getData();

    }

    private void getData() {
        retrofit=new Retrofit.Builder()
                .baseUrl("http://ehab01998.com").addConverterFactory(GsonConverterFactory.create())
                .build();
        apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.getGroupMember(GroupId).enqueue(new Callback<ArrayList<member>>() {
            @Override
            public void onResponse(Call<ArrayList<member>> call, Response<ArrayList<member>> response) {
                listitems=response.body();
                adapter=new MemberGroupAdapter(ShowMemberGroup.this,listitems);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ArrayList<member>> call, Throwable t) {
                Toast.makeText(ShowMemberGroup.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
        
    }
}
