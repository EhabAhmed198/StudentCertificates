package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

public class Show_video extends AppCompatActivity {
VideoView videoView;
MediaController mediaController;
ProgressBar ProVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        ProVideo=findViewById(R.id.ProVideo);
        videoView=findViewById(R.id.vv_show);
        videoView.setVideoURI(Uri.parse(getIntent().getExtras().get("video").toString()));
        videoView.requestFocus();
        mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ProVideo.setVisibility(View.INVISIBLE);
                videoView.start();
            }
        });

    }
}
