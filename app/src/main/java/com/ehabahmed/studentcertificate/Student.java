package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class Student extends AppCompatActivity  {
    TabLayout tabLayout;
    Info studuentInfo;
    FragmentManager fragmentManager;
    Bundle bundle;
    ViewPager pager;
    Handler handler;
    SharedPreferences sharedPreferences,sharedPreferences2;
    SharedPreferences.Editor editor,editor2;
RequestQueue requestQueue;
    String fragment_id;
String url;
    PagerAdapter adapter;
    String Notificationsplish;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studuentInfo = (Info) getApplicationContext();
        pager = (ViewPager) findViewById(R.id.container);
     fragment_id=getIntent().getExtras().getString("fragment_id","-1");
        requestQueue= Volley.newRequestQueue(this);
        fragmentManager = getSupportFragmentManager();
        Notificationsplish=getIntent().getExtras().getString("Notificationsplish","no");
        studuentInfo.setId(getIntent().getExtras().getString("id"));
        studuentInfo.setName(getIntent().getExtras().getString("name"));
        studuentInfo.setPass(getIntent().getExtras().getString("pass"));
        studuentInfo.setPhoto(getIntent().getExtras().getString("photo"));
        studuentInfo.setDepartment(getIntent().getExtras().getString("department"));
        studuentInfo.setLevel(getIntent().getExtras().getString("level"));
        handler=new Handler();
        tabLayout = findViewById(R.id.tlHome);
  adapter=new PagerAdapter(getSupportFragmentManager(),1);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);


        tabLayout.getTabAt(0).setIcon(R.drawable.news);
        tabLayout.getTabAt(1).setIcon(R.drawable.lectures);
        tabLayout.getTabAt(2).setIcon(R.drawable.guide);
        tabLayout.getTabAt(3).setIcon(R.drawable.setting);

        if(fragment_id.equals("1")){
            pager.setCurrentItem(1,false);

        }
        sharedPreferences2 = getSharedPreferences("Notification", Context.MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();
        if(sharedPreferences2.getString("type","-1").equals("-1")) {
            editor2.putString("type", "0");
            editor2.apply();
            getNodataRowNotification();

        }


    }



    private void getNodataRowNotification() {
        if(!Notificationsplish.equals("ok")) {
   new Thread(new Runnable() {
       @Override
       public void run() {
           getNumsNews();
           getNumsgroupsAndPost();
           getNumsCourse();
           getNumsBookLibrary();
           getNumsExams();
           getNumsCompition();
           getNumsProgrems();
       }
   }).start();


        }

    }

    private void getNumsCompition() {
     SharedPreferences   sharedPreferences=getSharedPreferences("compition",Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor=sharedPreferences.edit();
       final ArrayList<Compition_Object>    listitems=new ArrayList<Compition_Object>();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/compition.php?department="+studuentInfo.getDepartment()+"&page="+0;
        }else{
            url="http://ehab01998.com/compition.php?department="+studuentInfo.getDepartment()+"&page="+0;
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Compition");

                    editor.putString("Ncompition",String.valueOf(array.length()+1));
                    editor.apply();
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

    private void getNumsExams() {
      SharedPreferences  sharedPreferences=getSharedPreferences("exams", Context.MODE_PRIVATE);
      final SharedPreferences.Editor  editor=sharedPreferences.edit();
      final ArrayList<Exama_Object>  listitems=new ArrayList<Exama_Object>();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/exams.php?department="+studuentInfo.getDepartment()+"&level="+studuentInfo.getLevel()+"&page="+0;
        }else{
            url="http://ehab01998.com/exams.php?department="+studuentInfo.getDepartment()+"&level="+studuentInfo.getLevel()+"&page="+0;
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Exams");

                    editor.putString("Nexams",String.valueOf(array.length()));
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(studuentInfo, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }

    private void getNumsBookLibrary() {
        sharedPreferences=getSharedPreferences("library",Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor=sharedPreferences.edit();
        final ArrayList<Library_object> listitems=new ArrayList<Library_object>();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/library.php?department_id="+studuentInfo.getDepartment()+"&page="+0;
        }else{
            url="http://ehab01998.com/library.php?department_id="+studuentInfo.getDepartment()+"&page="+0;
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Books");

                    editor.putString("Nbook",String.valueOf(array.length()));
                    editor.apply();

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

    private void getNumsCourse() {
        SharedPreferences sharedPreferences =getSharedPreferences("course",Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor=sharedPreferences.edit();
        final ArrayList<Courses_object>  listitems=new ArrayList<Courses_object>();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/courses.php?department_id="+studuentInfo.getDepartment()+"&page="+0;
        }else{
            url="http://ehab01998.com/courses.php?department_id="+studuentInfo.getDepartment()+"&page="+0;
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Courses");

                    editor.putString("Ncourse",String.valueOf(array.length()));
                    editor.apply();


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

    private void getNumsgroupsAndPost() {
      SharedPreferences  sharedPreferences = getSharedPreferences("Groups", Context.MODE_PRIVATE);
    SharedPreferences sharedPreferences1 = getSharedPreferences("NameGroups", Context.MODE_PRIVATE);

        final SharedPreferences.Editor   editor1 = sharedPreferences1.edit();
        final SharedPreferences.Editor    editor = sharedPreferences.edit();

     final ArrayList<Ngroups>  listitems = new ArrayList<Ngroups>();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url = "https://ehab01998.com/groups.php?department_id=" + studuentInfo.getDepartment() + "&groups_level=" + studuentInfo.getLevel();
        }else{
            url = "http://ehab01998.com/groups.php?department_id=" + studuentInfo.getDepartment() + "&groups_level=" + studuentInfo.getLevel();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("groups");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String id_g = object.getString("groups_id");
                        String id_d = object.getString("doctor_id");
                        String name = object.getString("groups_name");
                        editor1.putString("Ngroup" + i, id_g);
                        listitems.add(new Ngroups(id_g, id_d, name));
                    }
                    editor1.apply();
                    for (int i=0;i<listitems.size();i++) {
                        getNumsGroupPost(i,listitems);
                    }
                    editor.putInt("NumsGroups", listitems.size());
                    editor.apply();

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

    private void getNumsGroupPost(final int n, ArrayList<Ngroups> listitem) {

        SharedPreferences sharedPreferences2 = getSharedPreferences("Numberpost", Context.MODE_PRIVATE);
        final SharedPreferences.Editor   editor2 = sharedPreferences2.edit();
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url = "https://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + listitem.get(n).idgroup;
        }else{
            url = "http://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + listitem.get(n).idgroup;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                editor2.putString("NPost"+n,response);
                editor2.apply();

            }
        }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        requestQueue.add(stringRequest);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {


        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:

                    return    new Student_News();
                case 1:

                    return  new Lectures();
                case 2:

                    return new Sudentguide();
                case 3:

                    return  new StudentSettings();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            switch (position){
                case 0:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.news));
                        }
                    }, 1);

                    break;

                case 1:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.lectures));
                        }
                    }, 1);

                    break;

                case 2:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.guide));
                        }
                    }, 1);

                    break;

                case 3:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.settings));
                        }
                    }, 1);

                    break;
            }
        }

    }

    private void getNumsNews() {
        SharedPreferences  sharedPreferences=getSharedPreferences("number_news", Context.MODE_PRIVATE);
        final SharedPreferences.Editor  editor=sharedPreferences.edit();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url = "https://ehab01998.com/ShowNewsArray.php?page="+0;
        }else{
            url = "http://ehab01998.com/ShowNewsArray.php?page="+0;
        }

        final ArrayList<object_News> listitems = new ArrayList<object_News>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayRequest = response.getJSONArray("News");


                    editor.putInt("News", arrayRequest.length());
                    editor.apply();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    private void getNumsProgrems() {
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/progrems.php?page="+0;
        }else{
            url="http://ehab01998.com/progrems.php?page="+0;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("number_programs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Programs");

                    editor.putString("programs",String.valueOf(array.length()));
                    editor.apply();

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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menustart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.shair_app:
                shair();

                break;
            case R.id.sendSuggestions:
                sendSuggestions();
                break;

            case R.id.rate_app:
                rate_app();
                break;
            case R.id.exit_app:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void rate_app() {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse("market://details?id=" + getPackageName()));
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(intent);
        }
    }

    private void sendSuggestions() {
        intent=new Intent(Intent.ACTION_SEND);
        String into="السلام علكم ورحمتة الله وبركاته"+"\n"+"اقتراحي عن التطبيق هو : ";
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"ehabahmed01998@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Student Certificate");
        intent.putExtra(Intent.EXTRA_TEXT,into);
        try {
            startActivity(Intent.createChooser(intent,"Send Emial"));
        }catch (Exception e){
            Toast.makeText(this, getResources().getString(R.string.nosendprogrem), Toast.LENGTH_SHORT).show();
        }


    }

    private void shair() {

        String app_address="Student Certificate";
        String app_link;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            app_link="https://play.google.com/store/apps/details?id=com.ehabahmed.studentcertificate";

        }else{
            app_link="http://play.google.com/store/apps/details?id=com.ehabahmed.studentcertificate";

        }

        intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,app_address+"\n"+app_link);
        startActivity(intent);
    }
}