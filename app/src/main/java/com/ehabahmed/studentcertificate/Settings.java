package com.ehabahmed.studentcertificate;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Settings extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<setting_object> listitem;
AppSettings adapter;
LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        recyclerView=findViewById(R.id.recycleview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
putdata();
    }
    void putdata() {
        listitem=new ArrayList<setting_object>();
    listitem.add(new setting_object(1,"Privacy",R.drawable.privacy));
  adapter=new AppSettings(Settings.this,listitem,'s');
        recyclerView.setAdapter(adapter);

    }
}