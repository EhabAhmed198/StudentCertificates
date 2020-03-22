package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class Show_Result extends AppCompatActivity {
    PhotoView iv_show;
    TextView textView;
    ProgressBar progressBar;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__result);

        iv_show = findViewById(R.id.show_photo);
        progressBar=findViewById(R.id.progressbar);
        frameLayout=findViewById(R.id.continner);
        textView=findViewById(R.id.wait);
        String result=getIntent().getExtras().getString("result");
        if(result.substring(0,7).equals("nothing")){
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            iv_show.setVisibility(View.INVISIBLE);
            textView.setText(result.substring(7).toString());
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
               frameLayout.setBackgroundColor(Color.WHITE);
            iv_show.setVisibility(View.VISIBLE);

        Glide.with(this).load(result).into(iv_show);
    }}
}
