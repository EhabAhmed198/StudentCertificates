package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Studentguide12 extends AppCompatActivity {
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentguide12);
        recyclerView=findViewById(R.id.recycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> listeitems=new ArrayList<String>();
        listeitems.add(getResources().getString(R.string.lland1));
        listeitems.add(getResources().getString(R.string.lland2));
        studentguide_Adapter adapter=new studentguide_Adapter(listeitems,this);
        recyclerView.setAdapter(adapter);
    }
}
