package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class Show_image2 extends AppCompatActivity {
    PhotoView iv_show;
ProgressBar progressBar;
TextView  noavalible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image2);
        progressBar=findViewById(R.id.pb_showimage2);
        noavalible=findViewById(R.id.noavalible);
        progressBar.setVisibility(View.VISIBLE);
        noavalible.setVisibility(View.INVISIBLE);
        Info studuentInfo=(Info)getApplicationContext();
        iv_show = findViewById(R.id.iv_show);
        String part=getIntent().getExtras().getString("part");
        String level=studuentInfo.getGhange_level();
        String department=studuentInfo.getDepartment();
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/studentguide.php?department_id="+department+"&level="+level+"&part="+part;
        }else{
            url="http://ehab01998.com/studentguide.php?department_id="+department+"&level="+level+"&part="+part;

        }

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Glide.with(getApplicationContext()).load(response).into(iv_show);
                progressBar.setVisibility(View.INVISIBLE);
                noavalible.setVisibility(View.INVISIBLE);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                noavalible.setVisibility(View.VISIBLE);

            }
        });
        Volley.newRequestQueue(this).add(request);


    }
}
