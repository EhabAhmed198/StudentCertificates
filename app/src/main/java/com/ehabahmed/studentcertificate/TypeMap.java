package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TypeMap extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<MapsObject> listitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_map);
        recyclerView=findViewById(R.id.rv_maps);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        putdata();

    }

    private void putdata() {
        listitems=new ArrayList<MapsObject>();
        listitems.add(new MapsObject("1","https://ehab01998.com/AppImage/masq.png","المسجد"));
        listitems.add(new MapsObject("7","https://ehab01998.com/AppImage/atec.jpg","ATEC"));
        listitems.add(new MapsObject("2","https://ehab01998.com/AppImage/b1.png","B1"));
        listitems.add(new MapsObject("3","https://ehab01998.com/AppImage/b2.png","B2"));
        listitems.add(new MapsObject("4","https://ehab01998.com/AppImage/b3.PNG","B3"));
        listitems.add(new MapsObject("5","https://ehab01998.com/AppImage/b5.jpg","B5"));
        listitems.add(new MapsObject("6","https://ehab01998.com/AppImage/b7.png","الشؤن"));


        MapsAdapter adapter=new MapsAdapter(this,listitems);
        recyclerView.setAdapter(adapter);


    }
}
