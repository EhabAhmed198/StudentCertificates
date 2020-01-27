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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Programs extends AppCompatActivity implements SearchView.OnQueryTextListener {
RecyclerView recyclerView;
ProgressBar progressBar;
TextView no_programs;
    programAdapter programAdapter;
ArrayList<Library_object> listitems;  //Library_object==Programs_object
    GridLayoutManager gridLayoutManager;
    int userpage=1,curr,out,total;
    boolean inscrolling=false;
    String protocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);
        setTitle(getResources().getString(R.string.programs));
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        no_programs=findViewById(R.id.no_program);
        no_programs.setVisibility(View.INVISIBLE);
        listitems=new ArrayList<Library_object>();
        recyclerView=findViewById(R.id.recycleview);
        gridLayoutManager=new GridLayoutManager(Programs.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
      programAdapter=new programAdapter(Programs.this,listitems);
      recyclerView.setAdapter(programAdapter);
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

    }

    private void getdata(int userpage) {
        progressBar.setVisibility(View.VISIBLE);
        final String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/progrems.php?page="+userpage;
            protocal="https://";
        }else{
            url="http://ehab01998.com/progrems.php?page="+userpage;
            protocal="http://";
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Programs");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("programs_id");
                        String name=current.getString("programs_name");
                        String photo=current.getString("programs_photo");
                        String detals=current.getString("programs_details");
                        String link=current.getString("programs_link");
                        String urlphoto=protocal+photo;
                       String url=protocal+link;
                        listitems.add(new Library_object(id,name,urlphoto,detals,url));
                    }

                    programAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        no_programs.setVisibility(View.VISIBLE);
                    else
                        no_programs.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    no_programs.setVisibility(View.VISIBLE);
                else
                    no_programs.setVisibility(View.INVISIBLE);

            }
        });
        Volley.newRequestQueue(Programs.this).add(request);


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
        ArrayList<Library_object> newlist=new ArrayList<>();
        for(Library_object item:listitems){
            if(item.getName().toLowerCase().contains(userInput)){
                newlist.add(item);
            }
        }
        programAdapter.updateList(newlist);
        return false;
    }
}
