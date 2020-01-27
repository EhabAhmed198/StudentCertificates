package com.ehabahmed.studentcertificate;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.github.chrisbanes.photoview.PhotoView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class Table_student extends AppCompatActivity{
   PhotoView iv_show;

    TextView tv_table;
    RequestQueue requestQueue;
    String url;
    Info studuentInfo;
    Button group1,group2;
    String link1,link2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_student);
        sharedPreferences=getSharedPreferences("table", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        iv_show = findViewById(R.id.iv_table);
        studuentInfo=(Info)getApplicationContext();
        group1=findViewById(R.id.group1);
        group2=findViewById(R.id.group2);

group1.setVisibility(View.INVISIBLE);
group2.setVisibility(View.INVISIBLE);
        try {
            String num = getIntent().getExtras().getString("notiftable", "-1");
            if (num.equals("1")) {
                studuentInfo.setDepartment(getIntent().getExtras().getString("department"));
                studuentInfo.setLevel(getIntent().getExtras().getString("level"));
            }
        }catch (Exception e){}
        tv_table=findViewById(R.id.tv_table);
        requestQueue= Volley.newRequestQueue(this);

         setTitle(getResources().getString(R.string.table));
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/table.php?department="+studuentInfo.getDepartment()+"&level="+studuentInfo.getLevel();
        }else {
            url="http://ehab01998.com/table.php?department="+studuentInfo.getDepartment()+"&level="+studuentInfo.getLevel();
        }

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             if(!response.equals("null")) {
             if(!response.contains(",")) {
                 tv_table.setVisibility(View.INVISIBLE);
                 iv_show.setVisibility(View.VISIBLE);
                 show(response);
                 FileLoader.with(Table_student.this).load(response).
                         fromDirectory("StudentCertificate/image/table", FileLoader.DIR_EXTERNAL_PUBLIC)
                                 .asFile(new FileRequestListener<File>() {
                                     @Override
                                     public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                         File file=response.getBody();
                                         editor.putString("totalTable","one");
                                         editor.putString("table",file.getAbsolutePath());
                                         editor.apply();
                                     }

                                     @Override
                                     public void onError(FileLoadRequest request, Throwable t) {

                                     }
                                 });



             }else if(response.contains(",")){
                 link1=response.substring(0,response.indexOf(","));
                 link2=response.substring(response.indexOf(",")+1);
                 group1.setVisibility(View.VISIBLE);
                 group2.setVisibility(View.VISIBLE);
                 iv_show.setVisibility(View.INVISIBLE);
                 tv_table.setVisibility(View.INVISIBLE);
                 group1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         group1.setVisibility(View.INVISIBLE);
                         group2.setVisibility(View.INVISIBLE);
                         iv_show.setVisibility(View.VISIBLE);
                         show(link1);

                     }
                 });
                 group2.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         group1.setVisibility(View.INVISIBLE);
                         group2.setVisibility(View.INVISIBLE);
                         iv_show.setVisibility(View.VISIBLE);
                         show(link2);
                     }
                 });

                 editor.putString("totalTable","two");



                 FileLoader.with(Table_student.this).load(link1).
                         fromDirectory("StudentCertificate/image/table", FileLoader.DIR_EXTERNAL_PUBLIC)
                         .asFile(new FileRequestListener<File>() {
                             @Override
                             public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                 File file=response.getBody();

                                 editor.putString("table1",file.getAbsolutePath());
                                 editor.apply();
                             }

                             @Override
                             public void onError(FileLoadRequest request, Throwable t) {

                             }
                         });
                 FileLoader.with(Table_student.this).load(link2).
                         fromDirectory("StudentCertificate/image/table", FileLoader.DIR_EXTERNAL_PUBLIC)
                         .asFile(new FileRequestListener<File>() {
                             @Override
                             public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                 File file=response.getBody();

                                 editor.putString("table2",file.getAbsolutePath());
                                 editor.apply();
                             }

                             @Override
                             public void onError(FileLoadRequest request, Throwable t) {

                             }
                         });







             }



             }else{
                 String total=sharedPreferences.getString("totalTable","no");
                 if(total.equals("one")){
                     tv_table.setVisibility(View.INVISIBLE);
                     iv_show.setVisibility(View.VISIBLE);
                     String link=sharedPreferences.getString("table","notable");
                     show(link);

                 }else if(total.equals("two")){
                     final String link1=sharedPreferences.getString("table1","notable");
                     final String link2=sharedPreferences.getString("table2","notable");
                     group1.setVisibility(View.VISIBLE);
                     group2.setVisibility(View.VISIBLE);
                     iv_show.setVisibility(View.INVISIBLE);
                     tv_table.setVisibility(View.INVISIBLE);
                     group1.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             group1.setVisibility(View.INVISIBLE);
                             group2.setVisibility(View.INVISIBLE);
                             iv_show.setVisibility(View.VISIBLE);
                             show(link1);

                         }
                     });
                     group2.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             group1.setVisibility(View.INVISIBLE);
                             group2.setVisibility(View.INVISIBLE);
                             iv_show.setVisibility(View.VISIBLE);
                             show(link2);
                         }
                     });



                 }else{
                     iv_show.setVisibility(View.INVISIBLE);
                     tv_table.setVisibility(View.VISIBLE);}


             }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String total=sharedPreferences.getString("totalTable","no");
                if(total.equals("one")){
                    tv_table.setVisibility(View.INVISIBLE);
                    iv_show.setVisibility(View.VISIBLE);
                    String link=sharedPreferences.getString("table","notable");
                    show(link);

                }else if(total.equals("two")){
                    final String link1=sharedPreferences.getString("table1","notable");
                    final String link2=sharedPreferences.getString("table2","notable");
                    group1.setVisibility(View.VISIBLE);
                    group2.setVisibility(View.VISIBLE);
                    iv_show.setVisibility(View.INVISIBLE);
                    tv_table.setVisibility(View.INVISIBLE);
                    group1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group1.setVisibility(View.INVISIBLE);
                            group2.setVisibility(View.INVISIBLE);
                            iv_show.setVisibility(View.VISIBLE);
                            show(link1);

                        }
                    });
                    group2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group1.setVisibility(View.INVISIBLE);
                            group2.setVisibility(View.INVISIBLE);
                            iv_show.setVisibility(View.VISIBLE);
                            show(link2);
                        }
                    });



                }else{
                iv_show.setVisibility(View.INVISIBLE);
                tv_table.setVisibility(View.VISIBLE);}
            }
        });
        requestQueue.add(request);


    }

    public void show(String link){
        Glide.with(Table_student.this).load(link).into(iv_show);
    }


}
