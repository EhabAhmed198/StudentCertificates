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

public class Library extends AppCompatActivity implements SearchView.OnQueryTextListener {
RecyclerView recyclerView;
ArrayList<Library_object> listitems;
RequestQueue requestQueue;
Info studuentInfo;
Context context;
int userpage=1,curr,out,total;
boolean inscrolling=false;
GridLayoutManager gridLayoutManager;
ProgressBar progressBar;
    LibraryAdapter adapter;
    TextView nobook;
    String protocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        recyclerView=findViewById(R.id.rv_library);
        listitems=new ArrayList<>();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        setTitle(getResources().getString(R.string.library));
        studuentInfo=(Info)getApplicationContext();
        nobook=findViewById(R.id.no_library);
nobook.setVisibility(View.INVISIBLE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            protocal="https://";
        }else{
           protocal="http://";

        }
        listitems=new ArrayList<Library_object>();
        try{
            if(getIntent().getExtras().getString("type","-1").equals("1")){
                studuentInfo.setDepartment(getIntent().getExtras().getString("department"));
            }
        }catch(Exception e){}

        context=this;
        requestQueue= Volley.newRequestQueue(this);
        gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        try{
            if(getIntent().getExtras().getString("fav","NO").equals("library")){
                setTitle(getResources().getString(R.string.favbook));
                getfavdata();

            }else {
                setTitle(getResources().getString(R.string.library));
              adapter=new LibraryAdapter(listitems,context,1,'s');
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
                        if(inscrolling & (out+curr)==total){
                            inscrolling=false;
                            userpage++;
                            getdata(userpage);
                        }
                    }
                });
            }}catch (Exception e){
            adapter=new LibraryAdapter(listitems,context,1,'s');
            recyclerView.setAdapter(adapter);
            getdata(userpage);
            setTitle(getResources().getString(R.string.library));
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
                    if(inscrolling & (out+curr)==total){
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
            url="https://ehab01998.com/favBookget.php?type=0&code="+studuentInfo.getId();
        }else{
            url="http://ehab01998.com/favBookget.php?type=0&code="+studuentInfo.getId();

        }


       JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try {
                   JSONArray array=response.getJSONArray("book");
                   for(int i=0;i<array.length();i++){
                       JSONObject current=array.getJSONObject(i);
                       String id=current.getString("books_id");
                       String name=current.getString("books_name");
                       String photo=current.getString("books_photo");
                       String detals=current.getString("book_details");
                       String link=current.getString("books_link");

                       listitems.add(new Library_object(id,name,photo,detals,link));
                   }
                   adapter=new LibraryAdapter(listitems,context,0,'s');
                   recyclerView.setAdapter(adapter);
                   progressBar.setVisibility(View.INVISIBLE);
                   if(listitems.size()<=0)
                       nobook.setVisibility(View.VISIBLE);
                   else
                       nobook.setVisibility(View.INVISIBLE);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               progressBar.setVisibility(View.INVISIBLE);
               if(listitems.size()<=0)
                   nobook.setVisibility(View.VISIBLE);
               else
                   nobook.setVisibility(View.INVISIBLE);
           }
       });
       requestQueue.add(request);
    }

    private void getdata(int userpage) {
        progressBar.setVisibility(View.VISIBLE);
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/library.php?type=0&page="+userpage+"&department_id="+studuentInfo.getDepartment();
        }else{
            url="http://ehab01998.com/library.php?type=0&page="+userpage+"&department_id="+studuentInfo.getDepartment();



        }


        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Books");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("books_id");
                        String name=current.getString("books_name");
                        String photo=current.getString("books_photo");
                        String detals=current.getString("book_details");
                        String link=current.getString("books_link");
                     String  plink=protocal+link;
                     String Nphoto=protocal+photo;
                        listitems.add(new Library_object(id,name,Nphoto,detals,plink));
                    }

                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nobook.setVisibility(View.VISIBLE);
                    else
                        nobook.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    nobook.setVisibility(View.VISIBLE);
                else
                    nobook.setVisibility(View.INVISIBLE);
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
        ArrayList<Library_object> newlist=new ArrayList<>();
        for(Library_object item:listitems){
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
