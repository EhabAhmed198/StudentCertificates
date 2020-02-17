package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
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


public class FavNews extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<object_News> listitems;
RequestQueue requestQueue;
Info info;
    TextView nonews;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_news);
        setTitle(getResources().getString(R.string.favnews));
        recyclerView=findViewById(R.id.recycle_news);
        requestQueue= Volley.newRequestQueue(this);
        info= (Info) getApplicationContext();
        listitems = new ArrayList<object_News>();
        nonews=findViewById(R.id.nonew);

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        nonews.setVisibility(View.INVISIBLE);
        getfavdata();
    }

    private void getfavdata() {
        progressBar.setVisibility(View.VISIBLE);
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url = "https://ehab01998.com/favNewsGet.php?type=0&code="+info.getId();
        }else{
            url = "http://ehab01998.com/favNewsGet.php?type=0&code="+info.getId();

        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayRequest = response.getJSONArray("News");
                    for (int i = 0; i < arrayRequest.length(); i++) {
                        JSONObject object = arrayRequest.getJSONObject(i);
                        int news_id = object.getInt("news_id");
                        String news_text = object.getString("news_text");
                        String news_detals = object.getString("news_detals");
                        String news_ivname = object.getString("news_ivname");
                        String news_type = object.getString("news_type");
                        String data_time = object.getString("data_time");

                        listitems.add(new object_News(news_id, news_text, news_detals, news_ivname, news_type, data_time));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(FavNews.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    NewsAdapter adapter = new NewsAdapter(FavNews.this, listitems, 1,1,true);
                    recyclerView.setAdapter(adapter);
                    if(listitems.size()<=0)
                        nonews.setVisibility(View.VISIBLE);
                    else
                        nonews.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(listitems.size()<=0)
                    nonews.setVisibility(View.VISIBLE);
                else
                    nonews.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}
