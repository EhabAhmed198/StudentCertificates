package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.github.chrisbanes.photoview.PhotoView;



public class Doctor_Table extends AppCompatActivity implements View.OnClickListener {
Spinner tabeldepartment,tableland;
String[] departments,levels;
ArrayAdapter<String> adapter1,adapter2;
TextView notable;
ProgressBar progressBar;
PhotoView iv_table;
RequestQueue requestQueue;
Button show_table;
int id_department,id_level;
String link1,link2;
    Button group1,group2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__table);
        departments=getResources().getStringArray(R.array.departments);
        levels=getResources().getStringArray(R.array.level);
        tabeldepartment=findViewById(R.id.department_table);
        show_table=findViewById(R.id.showTable);
        tableland=findViewById(R.id.land_table);
        iv_table=findViewById(R.id.iv_table);
        group1=findViewById(R.id.group1);
        group2=findViewById(R.id.group2);
        group1.setVisibility(View.INVISIBLE);
        group2.setVisibility(View.INVISIBLE);
        iv_table.setVisibility(View.INVISIBLE);
        requestQueue= Volley.newRequestQueue(this);
        adapter1=new ArrayAdapter<>(this,R.layout.spinner,R.id.text_spinner,departments);
        adapter2=new ArrayAdapter<>(this,R.layout.spinner,R.id.text_spinner,levels);
       progressBar=findViewById(R.id.progressbar);
       progressBar.setVisibility(View.INVISIBLE);
        notable=findViewById(R.id.tv_table);
        notable.setVisibility(View.INVISIBLE);
      tabeldepartment.setAdapter(adapter1);
      tableland.setAdapter(adapter2);
gettable(0,1,4);
show_table.setOnClickListener(this);
iv_table.setOnClickListener(this);
    }

    private void gettable(final int type, int department, int level) {
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/table.php?department="+department+"&level="+level;
        }else{
            url="http://ehab01998.com/table.php?department="+department+"&level="+level;

        }

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")) {
                    if(!response.contains(",")){
                    notable.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    iv_table.setVisibility(View.VISIBLE);

                    Glide.with(Doctor_Table.this).load(response)
                            .into(iv_table);
                    if(type!=0) {
                        Intent intent = new Intent(Doctor_Table.this, ShowDoctorTable.class);
                        intent.putExtra("linkphoto", response);
                        startActivity(intent);
                    }}else if(response.contains(",")){
                    link1=response.substring(0,response.indexOf(","));
                    link2=response.substring(response.indexOf(",")+1);
                        group2.setVisibility(View.VISIBLE);
                        group1.setVisibility(View.VISIBLE);
                        notable.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        iv_table.setVisibility(View.INVISIBLE);
                        group1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Doctor_Table.this, ShowDoctorTable.class);
                                intent.putExtra("linkphoto", link1);
                                startActivity(intent);



                            }
                        });
                        group2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(Doctor_Table.this, ShowDoctorTable.class);
                                intent.putExtra("linkphoto", link2);
                                startActivity(intent);

                            }
                        });


                    }

                }else{
                    iv_table.setVisibility(View.INVISIBLE);
                    notable.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notable.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                iv_table.setVisibility(View.INVISIBLE);




            }
        });
        requestQueue.add(request);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.showTable) {
            getConvertSpinnerData();
            group1.setVisibility(View.INVISIBLE);
            group2.setVisibility(View.INVISIBLE);
            gettable(1,id_department, id_level);

        }

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
}
