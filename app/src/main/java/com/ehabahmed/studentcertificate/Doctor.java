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
import android.widget.TimePicker;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Doctor extends AppCompatActivity  {
    Info info;
    TabLayout tabLayout;
    Info studuentInfo;
    FragmentManager fragmentManager;
    String check="0";
    ViewPager pager;
    Handler handler;
    PagerAdapter adapter;
    RequestQueue requestQueue;
    String url;
    SharedPreferences sharedPreferences2, sharedPreferences;
    SharedPreferences.Editor  editor2,editor;
    Intent intent;
    boolean StateDeletePage=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        info=(Info)getApplicationContext();
        pager=findViewById(R.id.container);
        tabLayout=findViewById(R.id.tlHome);
        fragmentManager=getSupportFragmentManager();
        handler=new Handler();
        requestQueue= Volley.newRequestQueue(this);
        if(!check.equals(getIntent().getExtras().getString("check"))) {
            info.setDoctor_id(getIntent().getExtras().getString("doctor_id"));
            info.setDoctor_name(getIntent().getExtras().getString("doctor_name"));
            info.setDoctor_photo(getIntent().getExtras().getString("doctor_photo"));
            info.setDoctor_password(getIntent().getExtras().getString("doctor_password"));
            info.setDoctor_mobile(getIntent().getExtras().getString("mobile"));
            info.setDoctor_email(getIntent().getExtras().getString("email"));

        }
     adapter=new PagerAdapter(fragmentManager,1);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.news);
        tabLayout.getTabAt(1).setIcon(R.drawable.lectures);
        tabLayout.getTabAt(2).setIcon(R.drawable.setting);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position==1){
                    if(StateDeletePage==false) {

                        pager.removeViewAt(0);
                        StateDeletePage=true;
                    }


                }else if(position==0){
                    if(StateDeletePage==true) {
                        pager.setAdapter(adapter);
                        tabLayout.getTabAt(0).setIcon(R.drawable.news);
                        tabLayout.getTabAt(1).setIcon(R.drawable.lectures);
                        tabLayout.getTabAt(2).setIcon(R.drawable.setting);
                        StateDeletePage=false;
                    }

                }else if(position==2){
                    if(StateDeletePage==false) {
                        pager.removeViewAt(0);
                        StateDeletePage=true;
                    }

                }else if(position==3){
                    if(StateDeletePage==false) {

                        pager.removeViewAt(0);
                        StateDeletePage=true;
                    }
                }
            }
        });
        sharedPreferences2 = getSharedPreferences("Notification", Context.MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();
        if(sharedPreferences2.getString("type","-1").equals("-1")) {
            editor2.putString("type", "0");
            editor2.apply();
            getNodataRowNotification();

        }


    }
    private void getNodataRowNotification() {
        getNumsNews();

    }
    private void getNumsNews() {
         sharedPreferences=getSharedPreferences("number_news", Context.MODE_PRIVATE);
         editor=sharedPreferences.edit();
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url = "https://ehab01998.com/ShowNewsArray.php";
        }else{
            url = "http://ehab01998.com/ShowNewsArray.php";

        }

        final ArrayList<object_News> listitems = new ArrayList<>();
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

                    editor.putInt("News", listitems.size());
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


    public class PagerAdapter extends FragmentStatePagerAdapter  {

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:


                    return new Doctor_News();
                case 1:


                    return  new Doctor_Lectures();
                case 2:

                    return new DoctorSetting();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
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
                            setTitle(getResources().getString(R.string.groups));
                        }
                    }, 1);

                    break;

                case 2:
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


