package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class Courses extends AppCompatActivity implements SearchView.OnQueryTextListener {
RecyclerView recyclerView;
RequestQueue requestQueue;
ArrayList<Courses_object> listitems;
String id,name,image;
JSONObject current;
CoursesAdapter adapter;
Info info;
String department;
Context context;
int curr,out,total;
boolean inscrolling=false;
ProgressBar progressBar;
LinearLayoutManager linearLayoutManager;
public int userpage=1;
TextView nocourse;
String protocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        recyclerView=findViewById(R.id.recycle);
        info=(Info)getApplicationContext();
progressBar=findViewById(R.id.progressbar);
        listitems=new ArrayList<>();
linearLayoutManager=new LinearLayoutManager(Courses.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        department=info.getDepartment();
        progressBar.setVisibility(View.INVISIBLE);
        nocourse=findViewById(R.id.no_course);
        nocourse.setVisibility(View.INVISIBLE);
        try{
            String type=getIntent().getExtras().getString("type","-1");
            if(type.equals("1")) {
                department = getIntent().getExtras().getString("department");
            }
        }catch (Exception e){}
        context=this;
        requestQueue= Volley.newRequestQueue(this);

        try{
        if(getIntent().getExtras().getString("fav","NO").equals("course")){
            setTitle(getResources().getString(R.string.favcourses));
            getfavdata(userpage);

        }else {
            setTitle(getResources().getString(R.string.courses));
            adapter=new CoursesAdapter(listitems,context,1,'s');
            recyclerView.setAdapter(adapter);
            getdata(userpage);
            setTitle(getResources().getString(R.string.courses));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                        inscrolling=true;
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    curr=linearLayoutManager.getChildCount();
                    out=linearLayoutManager.findFirstVisibleItemPosition();
                    total=linearLayoutManager.getItemCount();
                    if(inscrolling && (curr+out)==total){
                        inscrolling=false;
                        userpage++;
                        getdata(userpage);
                    }
                }
            });

        }}catch (Exception e){
            adapter=new CoursesAdapter(listitems,context,1,'s');
            recyclerView.setAdapter(adapter);
            getdata(userpage);
            setTitle(getResources().getString(R.string.courses));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                        inscrolling=true;

                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    curr=linearLayoutManager.getChildCount();
                    out=linearLayoutManager.findFirstVisibleItemPosition();
                    total=linearLayoutManager.getItemCount();
                    if(inscrolling && (curr+out)==total){
                        inscrolling=false;
                        userpage++;
                        getdata(userpage);
                    }
                }
            });

        }



    }

    private void getfavdata(final int userpage) {

        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/favcourseget.php?department_id="+info.department+"&code="+info.getId()+"&page="+userpage;
        }else{
            url="http://ehab01998.com/favcourseget.php?department_id="+info.department+"&code="+info.getId()+"&page="+userpage;

        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Courses");
                    for (int i=0;i<array.length();i++){
                        current=array.getJSONObject(i);
                        id=current.getString("courses_type_id");
                        name=current.getString("courses_type_name");
                        image=current.getString("course_image");
                        listitems.add(new Courses_object(id,name,image));
                    }

                    adapter=new CoursesAdapter(listitems,context,0,'s');
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nocourse.setVisibility(View.VISIBLE);
                    else
                        nocourse.setVisibility(View.INVISIBLE);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    nocourse.setVisibility(View.VISIBLE);
                else
                    nocourse.setVisibility(View.INVISIBLE);

            }
        });
        requestQueue.add(request);
    }

    private void getdata(int userpage) {
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/courses.php?department_id="+department+"&page="+userpage;
            protocal="https://";
        }else{
            url="http://ehab01998.com/courses.php?department_id="+department+"&page="+userpage;
            protocal="http://";

        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Courses");
                    for (int i=0;i<array.length();i++){
                        current=array.getJSONObject(i);
                        id=current.getString("courses_type_id");
                        name=current.getString("courses_type_name");
                        image=current.getString("course_image");
                        String Nimage=protocal+image;
                        listitems.add(new Courses_object(id,name,Nimage));
                    }


                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nocourse.setVisibility(View.VISIBLE);
                    else
                        nocourse.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    nocourse.setVisibility(View.VISIBLE);
                else
                    nocourse.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(request);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_search){
            if(userpage<3){
                userpage++;
                getdata(userpage);
            }
            if(userpage<3){
                userpage++;
                getdata(userpage);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        ArrayList<Courses_object> newlist = new ArrayList<>();
        for (Courses_object item : listitems) {
            if (item.getName().toLowerCase().contains(userInput)) {
                newlist.add(item);
            }
        }
        try{
        adapter.updateList(newlist);
    }catch (Exception e){}
        return true;
    }


}
