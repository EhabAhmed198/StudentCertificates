package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Build;
import android.os.Bundle;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import android.widget.AbsListView;

import android.widget.ProgressBar;
import android.widget.TextView;


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

public class Exams extends AppCompatActivity implements SearchView.OnQueryTextListener{
RecyclerView recyclerView;
ArrayList<Exama_Object> listitems;
RequestQueue requestQueue;
Info studuentInfo;
    ExamsAdapter adapter;
    int curr,out,total;
    boolean inscrolling=false;
    GridLayoutManager gridLayoutManager;
    int userpage=1;
    ProgressBar progressBar;
    TextView noexms;
    String protocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        recyclerView=findViewById(R.id.rv_exams);
        setTitle(getResources().getString(R.string.examp));
        studuentInfo=(Info)getApplicationContext();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        noexms=findViewById(R.id.no_exams);
        noexms.setVisibility(View.INVISIBLE);

        try{
            if(getIntent().getExtras().getString("type","-1").equals("1")){
                studuentInfo.setDepartment(getIntent().getExtras().getString("department"));
                studuentInfo.setLevel(getIntent().getExtras().getString("level"));
            }
        }catch (Exception e){}
        listitems=new ArrayList<Exama_Object>();
        requestQueue= Volley.newRequestQueue(this);
        gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);



        try{
            if(getIntent().getExtras().getString("fav","NO").equals("exams")){
                setTitle(getResources().getString(R.string.favexams));

                getfavdata();

            }else {
                setTitle(getResources().getString(R.string.examp));
                adapter=new ExamsAdapter(getApplicationContext(),listitems,1,'s');
                recyclerView.setAdapter(adapter);
                getdata(userpage);
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
                        curr=gridLayoutManager.getChildCount();
                        out=gridLayoutManager.findFirstVisibleItemPosition();
                        total=gridLayoutManager.getItemCount();
                        if(inscrolling && (curr+out)==total){
                            inscrolling=false;
                            userpage++;

                            getdata(userpage);
                        }
                    }
                });

            }}catch (Exception e){
            setTitle(getResources().getString(R.string.examp));
          adapter=new ExamsAdapter(getApplicationContext(),listitems,1,'s');
            recyclerView.setAdapter(adapter);
            getdata(userpage);
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
                    curr=gridLayoutManager.getChildCount();
                    out=gridLayoutManager.findFirstVisibleItemPosition();
                    total=gridLayoutManager.getItemCount();
                    if(inscrolling && (curr+out)==total){
                        inscrolling=false;
                        userpage++;
                        getdata(userpage);
                    }
                }
            });
        }

    }

    private void getfavdata() {
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/favExamsGet.php?type="+0+"&code="+studuentInfo.getId();

        }else{
            url="http://ehab01998.com/favExamsGet.php?type="+0+"&code="+studuentInfo.getId();

        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Exams");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("id");
                        String name=current.getString("name");
                        String image=current.getString("image");
                        String term=current.getString("term");

                        listitems.add(new Exama_Object(id,name,image,term));
                    }

                    adapter=new ExamsAdapter(getApplicationContext(),listitems,0,'s');
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        noexms.setVisibility(View.VISIBLE);
                    else
                        noexms.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    noexms.setVisibility(View.VISIBLE);
                else
                    noexms.setVisibility(View.INVISIBLE);

            }
        });
        requestQueue.add(request);
    }

    private void getdata(int userpage) {
        progressBar.setVisibility(View.VISIBLE);
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/exams.php?department="+studuentInfo.getDepartment()+"&level="+studuentInfo.getLevel()+"&page="+userpage;
            protocal="https://";
        }else{
            url="http://ehab01998.com/exams.php?department="+studuentInfo.getDepartment()+"&level="+studuentInfo.getLevel()+"&page="+userpage;

            protocal="http://";
        }
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Exams");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("id");
                        String name=current.getString("name");
                        String image=current.getString("image");
                        String term=current.getString("term");
                        String Nimage=protocal+image;
                        listitems.add(new Exama_Object(id,name,Nimage,term));
                    }

               adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        noexms.setVisibility(View.VISIBLE);
                    else
                        noexms.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    noexms.setVisibility(View.VISIBLE);
                else
                    noexms.setVisibility(View.INVISIBLE);

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
        String userInput=newText.toLowerCase();
        ArrayList<Exama_Object> newlist=new ArrayList<>();
        for(Exama_Object item:listitems){
            if(item.getName().toLowerCase().contains(userInput)){
                newlist.add(item);
            }
        }
        try{
        adapter.updateList(newlist);
    }catch (Exception e){}
        return true;
    }
}
