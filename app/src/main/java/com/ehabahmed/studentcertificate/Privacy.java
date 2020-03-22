package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Privacy extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<setting_object> listitem;
    AppSettings adapter;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy);
        recyclerView=findViewById(R.id.recycleview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        putdata();
    }
    void putdata() {
        listitem=new ArrayList<setting_object>();
        listitem.add(new setting_object(1,"Fingerprint",R.drawable.ic_fingerprint_black_24dp));
        adapter=new AppSettings(Privacy.this,listitem,'p');
        recyclerView.setAdapter(adapter);

    }
}
