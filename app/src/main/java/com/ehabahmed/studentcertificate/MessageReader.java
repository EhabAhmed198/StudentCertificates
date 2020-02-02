package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MessageReader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_reader);
        TextView message=findViewById(R.id.reader_message);
        TextView id=findViewById(R.id.reader_id);
        TextView title=findViewById(R.id.reader_title);
        TextView name=findViewById(R.id.reader_name);
        ImageView image=findViewById(R.id.reader_image);
        Intent intent=getIntent();
        message.setText("message :"+"\t"+intent.getStringExtra("message"));
        id.setText("id :"+"\t"+intent.getStringExtra("id"));
        title.setText("title :"+"\t"+intent.getStringExtra("title"));
        name.setText("name :"+"\t"+intent.getStringExtra("name"));
        String Photo = intent.getStringExtra("image");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load("https://ehab01998.com/images_profile/" + Photo)
                    .into(image);
        } else {
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load("http://ehab01998.com/images_profile/" + Photo)
                    .into(image);
        }
    }
}
