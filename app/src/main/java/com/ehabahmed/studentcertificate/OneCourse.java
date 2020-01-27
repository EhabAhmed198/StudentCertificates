package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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

public class OneCourse extends AppCompatActivity {
    String type_id;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    JSONObject current;
    ArrayList<OneCourseObject> listitems;
SharedPreferences SPreferences;
Info info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_course);
        try {
            type_id = getIntent().getExtras().getString("id");
            SPreferences=getSharedPreferences("type_id", Context.MODE_PRIVATE);
            SharedPreferences.Editor DEditor =SPreferences.edit();
            DEditor.putString("id",type_id);
            DEditor.apply();
        }catch (Exception e){
            SPreferences=getSharedPreferences("type_id", Context.MODE_PRIVATE);
            type_id=SPreferences.getString("id","No_Data");

        }
        recyclerView=findViewById(R.id.recycle_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue= Volley.newRequestQueue(this);
getdata();

    }

    private void getdata() {
        listitems=new ArrayList<OneCourseObject>();
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/OneCourse.php?type="+type_id;
        }else{
            url="http://ehab01998.com/OneCourse.php?type="+type_id;

        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Courses");
                    for (int i=0;i<array.length();i++){
                        current=array.getJSONObject(i);
                        String id=current.getString("id");
                        String name=current.getString("name");
                        String type=current.getString("type");
                    listitems.add(new OneCourseObject(id,name,type));
                    }
                    OneCourseAdapter adapter=new OneCourseAdapter(OneCourse.this,listitems);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        info=(Info)getApplicationContext();
        if(info.getType().equals("student")){

        }
        else if(info.getType().equals("doctor"))
        {
            Intent intent=new Intent(OneCourse.this,DoctorCourses.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;

        }else if(info.getType().equals("certificate")){

        }

        return super.onOptionsItemSelected(item);


    }
}
