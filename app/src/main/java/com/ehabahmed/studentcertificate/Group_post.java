package com.ehabahmed.studentcertificate;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Group_post extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<object_post_group> listitems;
    String group_id,doctor_id;
    RequestQueue requestQueue;
    Context context;
    Info  info;
    StringRequest stringRequest;
    String protocal;
    TextView nopostgroup;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_post);
        nopostgroup=findViewById(R.id.no_postgroup);
        progressBar=findViewById(R.id.progressbar);
        nopostgroup.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
         info=(Info)getApplicationContext();
          doctor_id=getIntent().getExtras().getString("doctor_id");
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  getSupportActionBar().setTitle(info.getGroup_name());
              }
          },1);
         getdocorname();
        recyclerView=findViewById(R.id.recycleview);
        context=this;
        requestQueue=Volley.newRequestQueue(this);




    }

    private void getdocorname() {
        progressBar.setVisibility(View.VISIBLE);
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/getdoctorname.php?doctor_id="+doctor_id;
        }else{
            url="http://ehab01998.com/getdoctorname.php?doctor_id="+doctor_id;

        }

       stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                info.setDoctor_name(response);
                getdata();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);

                    nopostgroup.setVisibility(View.VISIBLE);


            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestQueue.add(stringRequest);
            }
        },1);


    }

    private void getdata() {
        group_id =getIntent().getExtras().getString("group_id");
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/groupandpost.php?group_id="+group_id;
            protocal="https://";
        }else{
            url="http://ehab01998.com/groupandpost.php?group_id="+group_id;
            protocal="http://";

        }


        listitems=new ArrayList<object_post_group>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayRequest=response.getJSONArray("posts");
                    for (int i=0;i<arrayRequest.length();i++){
                        JSONObject object=arrayRequest.getJSONObject(i);
                        String id=object.getString("id");
                        String text=object.getString("text");
                        String pvf_name=object.getString("pvf_name");
                        String type=object.getString("type");
                        String group_id=object.getString("group_id");
                        String data_time=object.getString("data_time");
                        String link=protocal+pvf_name;
                        listitems.add(new object_post_group(id,text,link,type,group_id,data_time));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                   GroupsAdapter adapter=new GroupsAdapter(listitems,context,"student");
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nopostgroup.setVisibility(View.VISIBLE);
                    else
                        nopostgroup.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    nopostgroup.setVisibility(View.VISIBLE);
                else
                    nopostgroup.setVisibility(View.INVISIBLE);
            }
        });
       requestQueue.add(jsonObjectRequest);



    }



}
