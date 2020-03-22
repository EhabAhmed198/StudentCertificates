package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comments extends AppCompatActivity implements View.OnClickListener {
EditText commentText;
Button send;
Info info;
String code,username,comment,postid,url;
RequestQueue requestQueue;
ArrayList<comment> listitems;
RecyclerView recyclerView;
String type;
CommentAdapter adapter;
int commentsTotal;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
String fav;
ProgressBar progressBar;
TextView nocomment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
progressBar=findViewById(R.id.pb_comment);
nocomment=findViewById(R.id.tv_comment);
progressBar.setVisibility(View.INVISIBLE);
nocomment.setVisibility(View.INVISIBLE);
recyclerView=findViewById(R.id.showcomments);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue=Volley.newRequestQueue(this);
        commentText=findViewById(R.id.textcomment);
        info=(Info)getApplicationContext();
        postid=getIntent().getExtras().getString("postid");
        type=getIntent().getExtras().getString("type");

        try{
            fav=getIntent().getExtras().getString("fav","no");
            if(fav.equals("fav")) {
                if (type.equals("student")) {
                    info.setName(getIntent().getExtras().getString("SName"));
                    info.setId(getIntent().getExtras().getString("SId"));
                } else if (type.equals("doctor")) {
                    info.setDoctor_name(getIntent().getExtras().getString("DName"));
                    info.setDoctor_id(getIntent().getExtras().getString("DId"));
                }
            }
        }catch (Exception e){}
        send=findViewById(R.id.send);
        send.setOnClickListener(this);

        showcomments();
    }

    private void showcomments() {
        progressBar.setVisibility(View.VISIBLE);
        listitems=new ArrayList<>();

        url="http://www.ehab01998.com/showComments.php?post_id="+postid;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Comments");
                    commentsTotal=array.length();
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String comment_user=current.getString("comment_user");
                        String comment_text=current.getString("comment_text");
                        String code=current.getString("code");
                        String photo=current.getString("photo");
                        String data_time=current.getString("data_time");
                        if(type.equals("doctor")) code="";
                        listitems.add(new comment(comment_user,comment_text,code,photo,data_time));

                    }
                    adapter=new CommentAdapter(Comments.this,listitems);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nocomment.setVisibility(View.VISIBLE);
                    else
                        nocomment.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                nocomment.setVisibility(View.VISIBLE);
                
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {


        if(type.equals("student")) {
            username=info.getName();
            code = info.getId();
        }else if(type.equals("doctor")){
            username=info.getDoctor_name();
            code=info.getDoctor_id();
        }

        comment=commentText.getText().toString().trim();
        if(comment.isEmpty()){
            commentText.setError(getResources().getString(R.string.addcommenttext));
        }else {
              commentText.setError(null);
            url = "http://www.ehab01998.com/comments.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(info, getResources().getString(R.string.addcomment), Toast.LENGTH_SHORT).show();
                    sharedPreferences=getSharedPreferences(postid+"total", Context.MODE_PRIVATE);
                    editor=sharedPreferences.edit();
                    editor.putString(postid+"total",String.valueOf(commentsTotal+1));
                    editor.apply();
                    Intent intent = new Intent(Comments.this, Comments.class);
                    intent.putExtra("postid", postid);
                    intent.putExtra("type",type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(info, getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("comment_user", username);
                    params.put("comment_text", comment);
                    params.put("code", code);
                    params.put("post_id", postid);
                    return params;
                }
            };

            requestQueue.add(request);
        }
    }
}
