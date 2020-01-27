package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Show_video extends AppCompatActivity {
VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        videoView=findViewById(R.id.vv_show);
        videoView.setVideoURI(Uri.parse(getIntent().getExtras().get("video").toString()));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }
}
