package com.ehabahmed.studentcertificate;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Build;
import android.os.Bundle;

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

public class ManyCourceOne extends AppCompatActivity {
RecyclerView recyclerView;
RequestQueue requestQueue;
JSONObject current;
String id,name,link;
String type_id;
ArrayList<ManyCoursesOne_object> listitems;
JsonObjectRequest request;
    String protocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_cource_one);

         type_id=getIntent().getExtras().getString("id");
        recyclerView=findViewById(R.id.recycle_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue= Volley.newRequestQueue(this);
listitems=new ArrayList<ManyCoursesOne_object>();
        getdata();

    }

    private void getdata() {
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/special_course.php?coursesTypeId="+type_id;
            protocal="https://";
        }else{
            url="http://ehab01998.com/special_course.php?coursesTypeId="+type_id;
            protocal="http://";
        }

        request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Courses");
                    for (int i=0;i<array.length();i++){
                        current=array.getJSONObject(i);
                      String id=current.getString("courses_id");
                      String name=current.getString("courses_name");
                      String link=current.getString("link");
                     String Nlinkk=protocal+link;
                      listitems.add(new ManyCoursesOne_object(id,name,Nlinkk));
                    }

                    ManyCoursesAdapter adapter=new ManyCoursesAdapter(listitems,getApplicationContext());
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
        requestQueue.add(request);
    }


}
