package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Articles extends AppCompatActivity implements SearchView.OnQueryTextListener {
private RecyclerView recyclerView;
private  ArrayList<Articles_Object> listitems;
private  LinearLayoutManager layoutManager;
private  ArticlesAdapter adapter;
private TextView no_articles;
  private   int userpage=1,curr,out,total;
   private boolean inscrolling=false;
 private    ProgressBar progressBar;
private Info info;
String protocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        setTitle(getResources().getString(R.string.Articles));
        recyclerView=findViewById(R.id.rv_articles);
        no_articles=findViewById(R.id.no_articles);
        no_articles.setVisibility(View.INVISIBLE);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        info=(Info)getApplicationContext();
        layoutManager=new LinearLayoutManager(Articles.this);
        recyclerView.setLayoutManager(layoutManager);
        listitems=new ArrayList<>();
    adapter=new ArticlesAdapter(Articles.this,listitems);
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
                curr=layoutManager.getChildCount();
                out=layoutManager.findFirstVisibleItemPosition();
                total=layoutManager.getItemCount();
                if(inscrolling & (out+curr)==total){
                    inscrolling=false;
                    userpage++;
                    getdata(userpage);
                }
            }
        });


    }

    private void getdata(int userpage) {
        String url;
        progressBar.setVisibility(View.VISIBLE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/Articles.php?page="+userpage+"&department_id="+info.getDepartment();
            protocal="https://";
        }else{
            url="http://ehab01998.com/Articles.php?page="+userpage+"&department_id="+info.getDepartment();
            protocal="http://";

        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Articles");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("articles_id");
                        String title=current.getString("articles_title");
                        String photo=current.getString("articles_photo");
                        String detals=current.getString("articles_details");
                        String date=current.getString("articles_date");
                        String Nphoto=protocal+photo;
                        listitems.add(new Articles_Object(id,title,Nphoto,detals,date));
                    }

                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        no_articles.setVisibility(View.VISIBLE);
                    else
                        no_articles.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    no_articles.setVisibility(View.VISIBLE);
                else
                    no_articles.setVisibility(View.INVISIBLE);

            }
        });
        Volley.newRequestQueue(Articles.this).add(request);

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
        if(userpage==1){
            userpage++;
            getdata(userpage);
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
        ArrayList<Articles_Object> newlist=new ArrayList<>();
        for(Articles_Object item:listitems){
            if(item.getName().toLowerCase().contains(userInput)){
                newlist.add(item);
            }
        }
        try{
        adapter.updateList(newlist);
        }catch (Exception e){}
        return false;
    }

}
