package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

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

public class Compition extends AppCompatActivity implements SearchView.OnQueryTextListener {
RecyclerView recyclerView;
ArrayList<Compition_Object> listitems;
RequestQueue requestQueue;
Info studuentInfo;
Context context;
    CompitionAdapter adapter;
    int curr,out,total;
    int userpage=1;
    boolean inscrolling=false;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    TextView nocompition;
    String protocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compition);

        recyclerView=findViewById(R.id.rv_compition);
        setTitle(getResources().getString(R.string.compition));
        listitems=new ArrayList<>();

        context=this;
        studuentInfo=(Info)getApplicationContext();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        nocompition=findViewById(R.id.no_compition);
        nocompition.setVisibility(View.INVISIBLE);


        try{
            if(getIntent().getExtras().getString("type","-1").equals("1")){
                studuentInfo.setDepartment(getIntent().getExtras().getString("department"));
            }
        }catch (Exception e){}
        requestQueue= Volley.newRequestQueue(this);
gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        try{
            if(getIntent().getExtras().getString("fav","NO").equals("comptition")){
                setTitle(getResources().getString(R.string.favcompitions));
                getfavdata();

            }else {
                setTitle(getResources().getString(R.string.compition));
          adapter=new CompitionAdapter(context,listitems,0,'s');
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
                        if(inscrolling & (curr+out)==total){
                            inscrolling=false;
                            userpage++;
                            getdata(userpage);

                        }
                    }
                });

            }}catch (Exception e){
            getdata(userpage);
            adapter=new CompitionAdapter(context,listitems,0,'s');
            recyclerView.setAdapter(adapter);
            setTitle(getResources().getString(R.string.compition));
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
                    if(inscrolling & (curr+out)==total){
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
            url="https://ehab01998.com/favcomptitionGet.php?type=0&code="+studuentInfo.getId();

        }else{
            url="http://ehab01998.com/favcomptitionGet.php?type=0&code="+studuentInfo.getId();


        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("comptition");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("id");
                        String name=current.getString("name");
                        String image=current.getString("image");
                        String details=current.getString("details");

                        listitems.add(new Compition_Object(id,name,image,details));
                    }

                    CompitionAdapter adapter=new CompitionAdapter(context,listitems,1,'s');
                    recyclerView.setAdapter(adapter);
                    if(listitems.size()<=0)
                        nocompition.setVisibility(View.VISIBLE);
                    else
                        nocompition.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    nocompition.setVisibility(View.VISIBLE);
                else
                    nocompition.setVisibility(View.INVISIBLE);

            }
        });
        requestQueue.add(request);
    }

    private void getdata(int userpage) {
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/compition.php?type=0&department="+studuentInfo.getDepartment()+"&page="+userpage;
            protocal="https://";
        }else{
            url="http://ehab01998.com/compition.php?type=0&department="+studuentInfo.getDepartment()+"&page="+userpage;
            protocal="http://";
        }


        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Compition");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("id");
                        String name=current.getString("name");
                        String image=current.getString("image");
                        String details=current.getString("details");
                        String Nimage=protocal+image;
                        listitems.add(new Compition_Object(id,name,Nimage,details));
                    }

                   adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nocompition.setVisibility(View.VISIBLE);
                    else
                        nocompition.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    nocompition.setVisibility(View.VISIBLE);
                else
                    nocompition.setVisibility(View.INVISIBLE);

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
        ArrayList<Compition_Object> newlist=new ArrayList<>();
        for(Compition_Object item:listitems){
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
