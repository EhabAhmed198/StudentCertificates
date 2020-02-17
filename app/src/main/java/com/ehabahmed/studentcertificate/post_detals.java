package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class post_detals extends AppCompatActivity implements View.OnClickListener {
    CollapsingToolbarLayout collapsetitle;
    ImageView iv_collapse;
    TextView tv_detals;
    String gpost_detals,gpost_pvf_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detals);
        collapsetitle=findViewById(R.id.collapseing);
        iv_collapse=findViewById(R.id.iv_collapseing);
        tv_detals=findViewById(R.id.detals);
        gpost_detals=getIntent().getExtras().getString("gpost_detals");
        gpost_pvf_name=getIntent().getExtras().getString("gpost_pvf_name", String.valueOf(-1));


        tv_detals.setText(gpost_detals);
        if(!gpost_pvf_name.equals("-1")) {


            Glide.with(getApplicationContext()).load(gpost_pvf_name).into(iv_collapse);

        } else{
            iv_collapse.setImageResource(R.drawable.logo);
            gpost_pvf_name="nophoto";

        }
        iv_collapse.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Show_image.class);
        intent.putExtra("news_ivname",gpost_pvf_name);
        startActivity(intent);
    }
}
