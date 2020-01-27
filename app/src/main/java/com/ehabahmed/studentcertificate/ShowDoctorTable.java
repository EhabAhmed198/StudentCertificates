package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ShowDoctorTable extends AppCompatActivity {
PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_table);
        imageView=findViewById(R.id.image);

        String link=getIntent().getExtras().getString("linkphoto","none");

            Glide.with(this).load(link).into(imageView);

    }
}
