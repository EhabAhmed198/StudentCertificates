package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class Show_image extends AppCompatActivity {
PhotoView iv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        iv_show = findViewById(R.id.iv_show);
     String link=getIntent().getExtras().getString("news_ivname");
     if(link.equals("nophoto")){
         iv_show.setImageResource(R.drawable.logo);
     }{

         Glide.with(this).load(link).into(iv_show);
     }




}}
