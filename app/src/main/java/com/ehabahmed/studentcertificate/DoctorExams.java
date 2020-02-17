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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class DoctorExams extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    Spinner tabeldepartment,tableland;
    String[] departments,levels;
    ArrayAdapter<String> adapter1,adapter2;
    TextView no_exams;
    ProgressBar progressBar,progressBar2;
    RequestQueue requestQueue;
    Button show_exams;
    RecyclerView recyclerView;
    ArrayList<Exama_Object> listitems;
    Info studuentInfo;
    ExamsAdapter adapter;
    int curr,out,total;
    boolean inscrolling=false;
    GridLayoutManager gridLayoutManager;
    int userpage=1;
    int id_department,id_level;
    String protocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_exams);
        recyclerView=findViewById(R.id.rv_exams);
        setTitle(getResources().getString(R.string.examp));
        studuentInfo=(Info)getApplicationContext();
        departments=getResources().getStringArray(R.array.departments);
        levels=getResources().getStringArray(R.array.level);
        tabeldepartment=findViewById(R.id.department_exams);
        show_exams=findViewById(R.id.showExams);
        tableland=findViewById(R.id.land_exams);
        requestQueue= Volley.newRequestQueue(this);
        adapter1=new ArrayAdapter<String>(this,R.layout.spinner,R.id.text_spinner,departments);
        adapter2=new ArrayAdapter<String>(this,R.layout.spinner,R.id.text_spinner,levels);
        progressBar=findViewById(R.id.progressbar1);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar2=findViewById(R.id.progressbar2);
        progressBar2.setVisibility(View.INVISIBLE);
        no_exams=findViewById(R.id.no_exams);
        no_exams.setVisibility(View.INVISIBLE);
        tabeldepartment.setAdapter(adapter1);
        tableland.setAdapter(adapter2);

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
                tabeldepartment.setVisibility(View.INVISIBLE);
                tableland.setVisibility(View.INVISIBLE);
                show_exams.setVisibility(View.INVISIBLE);
                getfavdata();

            }else {
                setTitle(getResources().getString(R.string.examp));
                adapter=new ExamsAdapter(getApplicationContext(),listitems,1,'d');
                recyclerView.setAdapter(adapter);
                getConvertSpinnerData();
                getdata(userpage,id_department,id_level);
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

                            getConvertSpinnerData();
                            getdata(userpage,id_department,id_level);
                        }
                    }
                });

            }}catch (Exception e){
            setTitle(getResources().getString(R.string.examp));
            adapter=new ExamsAdapter(getApplicationContext(),listitems,1,'d');
            recyclerView.setAdapter(adapter);
            getConvertSpinnerData();
            getdata(userpage,id_department,id_level);
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
                        getConvertSpinnerData();
                        getdata(userpage,id_department,id_level);
                    }
                }
            });
        }
        show_exams.setOnClickListener(this);

    }

    private void getfavdata() {

        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/favExamsGet.php?type=1&doctor_id="+studuentInfo.getDoctor_id();
        }else{
            url="http://ehab01998.com/favExamsGet.php?type=1&doctor_id="+studuentInfo.getDoctor_id();

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

                    adapter=new ExamsAdapter(getApplicationContext(),listitems,0,'d');
                    recyclerView.setAdapter(adapter);

                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        no_exams.setVisibility(View.VISIBLE);
                    else
                        no_exams.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    no_exams.setVisibility(View.VISIBLE);
                else
                    no_exams.setVisibility(View.INVISIBLE);

            }
        });
        requestQueue.add(request);
    }

    private void getdata(int userpage,int department,int level) {
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/exams.php?department="+department+"&level="+level+"&page="+userpage;
            protocal="https://";
        }else{
            url="http://ehab01998.com/exams.php?department="+department+"&level="+level+"&page="+userpage;
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
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar2.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0) {
                        no_exams.setVisibility(View.VISIBLE);
                    }else {
                        no_exams.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar2.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    no_exams.setVisibility(View.VISIBLE);
                else
                    no_exams.setVisibility(View.INVISIBLE);

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
                getConvertSpinnerData();
                getdata(userpage,id_department,id_level);
            }
            if(userpage<3){
                userpage++;
                getConvertSpinnerData();
                getdata(userpage,id_department,id_level);
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
    public void getConvertSpinnerData(){
        if(tabeldepartment.getSelectedItem().toString().equals(departments[0])){
            id_department=1;
        }
        else   if(tabeldepartment.getSelectedItem().toString().equals(departments[1])){
            id_department=2;
        }
        else   if(tabeldepartment.getSelectedItem().toString().equals(departments[2])){
            id_department=3;
        }
        else   if(tabeldepartment.getSelectedItem().toString().equals(departments[3])){

            id_department=4;
        }

        if(tableland.getSelectedItem().toString().equals(levels[0])){
            id_level=1;
        }
        else  if(tableland.getSelectedItem().toString().equals(levels[1])){
            id_level=2;
        }
        else  if(tableland.getSelectedItem().toString().equals(levels[2])){
            id_level=3;
        }
        else  if(tableland.getSelectedItem().toString().equals(levels[3])){
            id_level=4;
        }



    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.showExams){
            userpage=1;
            listitems.clear();
            getConvertSpinnerData();
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar2.setVisibility(View.VISIBLE);

            getdata(userpage,id_department,id_level);
        }
    }
}
