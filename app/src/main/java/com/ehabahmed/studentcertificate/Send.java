package com.ehabahmed.studentcertificate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Send extends AppCompatActivity {
    private TextView receiver_name, receiver_id;
    private ImageView receiver_image;
    private EditText title, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        receiver_id = findViewById(R.id.receiver_id);
        receiver_name = findViewById(R.id.receiver_name);
        receiver_image = findViewById(R.id.receiver_imageView);
        Intent intent = getIntent();
        String id = intent.getStringExtra("receiver_id");
        receiver_id.setText("" + id);
        receiver_name.setText("" + intent.getStringExtra("receiver_name"));
        String Photo = intent.getStringExtra("receiver_image");
        title = findViewById(R.id.editText_title);
        message = findViewById(R.id.editText_message);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load("https://ehab01998.com/images_profile/" + Photo)
                    .into(receiver_image);
        } else {
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load("http://ehab01998.com/images_profile/" + Photo)
                    .into(receiver_image);
        }

        final String sender_id = intent.getStringExtra("sender_id");
        final String sender_name = intent.getStringExtra("sender_name");
        final String sender_image = intent.getStringExtra("sender_image");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_v = title.getText().toString().trim();
                String message_v = message.getText().toString().trim();
                if (title_v.isEmpty() || message_v.isEmpty()) {
                    Snackbar.make(view, "please put title and message", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    databaseReference.push().setValue(new SendForm(sender_name, sender_image, sender_id, message_v, title_v));
                    Toast.makeText(Send.this, " send message  success ", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }

}
