package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DGroup_Post extends AppCompatActivity  {

RecyclerView recyclerView;
ArrayList<object_post_group> listitems;
RequestQueue requestQueue;
    String group_id,name;
    Context context;
    ProgressBar progressBar;
    TextView noposts;
    String protocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dgroup__post);
       name=getIntent().getExtras().getString("name");

        recyclerView=findViewById(R.id.group_post);
        requestQueue= Volley.newRequestQueue(this);
        context=this;
progressBar=findViewById(R.id.progressbar);
noposts=findViewById(R.id.noposts);

progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               getSupportActionBar().setTitle(name);

            }
        },1);
       getdata();

    }

    private void getdata() {
        progressBar.setVisibility(View.VISIBLE);
       group_id =getIntent().getExtras().getString("group_id");
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/groupandpost.php?group_id="+group_id;
            protocal="https://";
        }else{
            url="http://ehab01998.com/groupandpost.php?group_id="+group_id;
            protocal="http://";

        }

        listitems=new ArrayList<>();
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

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    GroupsAdapter adapter=new GroupsAdapter(listitems,context,"doctor");
                    recyclerView.setAdapter(adapter);
                    if(listitems.size()>0) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        noposts.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                noposts.setVisibility(View.VISIBLE);

            }
        });
        requestQueue.add(jsonObjectRequest);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addpost,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.add){
            Intent intent=new Intent(DGroup_Post.this, CreatePostLecture.class);
            intent.putExtra("group_id",group_id);
            intent.putExtra("name",name);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
