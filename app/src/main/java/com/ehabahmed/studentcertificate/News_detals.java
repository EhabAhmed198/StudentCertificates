package com.ehabahmed.studentcertificate;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class News_detals extends AppCompatActivity implements View.OnClickListener {
    CollapsingToolbarLayout collapsetitle;
   ImageView iv_collapse;
   TextView tv_detals;
    String news_detals,news_ivname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detals);
        collapsetitle=findViewById(R.id.collapseing);
        iv_collapse=findViewById(R.id.iv_collapseing);
        tv_detals=findViewById(R.id.detals);
        news_detals=getIntent().getExtras().getString("news_detals");
        news_ivname=getIntent().getExtras().getString("news_ivname");
        tv_detals.setText(news_detals);
        Glide.with(getApplicationContext()).load(news_ivname).into(iv_collapse);
        iv_collapse.setOnClickListener(this);
        tv_detals.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Show_image.class);
        intent.putExtra("news_ivname",news_ivname);
        startActivity(intent);
    }
}
