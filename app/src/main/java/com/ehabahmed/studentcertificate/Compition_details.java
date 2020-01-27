package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class Compition_details extends AppCompatActivity implements View.OnClickListener {
    CollapsingToolbarLayout collapsetitle;
    ImageView iv_collapse;
    TextView tv_detals;
    String compition_detals,compition_image,compition_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compition_details);
        collapsetitle=findViewById(R.id.collapseing);
        iv_collapse=findViewById(R.id.iv_collapseing);
        tv_detals=findViewById(R.id.detals);
        compition_detals=getIntent().getExtras().getString("compition_detals");
        compition_image=getIntent().getExtras().getString("compition_image");
        compition_name=getIntent().getExtras().getString("compition_name");


        tv_detals.setText(compition_detals);
        Glide.with(getApplicationContext()).load(compition_image).into(iv_collapse);
        iv_collapse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Show_image.class);
        //news_ivname=comition_image
        intent.putExtra("news_ivname",compition_image);
        startActivity(intent);

    }
}
