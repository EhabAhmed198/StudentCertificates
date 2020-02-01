package com.ehabahmed.studentcertificate;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class profile_infoStudent extends Fragment  {

TextView code,department,land,mobile,entermobile,email,enteremail;
Info studuentInfo;

RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
studuentInfo=(Info)getContext().getApplicationContext();
     View view=    inflater.inflate(R.layout.fragment_profile_info_student, container, false);
     code=view.findViewById(R.id.entercode);
        requestQueue=Volley.newRequestQueue(getContext());

     department=view.findViewById(R.id.enterdepartment);
     land=view.findViewById(R.id.enterland);
     mobile=view.findViewById(R.id.mobile);
        entermobile=view.findViewById(R.id.entermobile);
        email=view.findViewById(R.id.email);
        enteremail=view.findViewById(R.id.enteremail);

statInfromation();
     getCpi();
     return view;
    }

    private void getCpi() {
  // Get String with retrofit

           GsonBuilder gsonBuilder=new GsonBuilder();
           gsonBuilder.setLenient();
            Gson gson=gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiConfig apiConfig=retrofit.create(ApiConfig.class);
                apiConfig.getdata(studuentInfo.getId()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(!response.body().equals("NF")) {
                            boolean phone=response.body().startsWith("NF");
                            boolean email=response.body().endsWith("NF");
                            if(phone==false && email==false) {
                                entermobile.setText(response.body().substring(0, 11));
                                enteremail.setText(response.body().substring(11));
                                showdata();
                                studuentInfo.setSMobile(response.body().substring(0, 11));
                                studuentInfo.setSEmail(response.body().substring(11));
                            }else if(phone==false && email==true){
                                entermobile.setText(response.body().substring(0, 11));
                                enteremail.setText(R.string.nofound);
                                     showdata();
                                studuentInfo.setSMobile(response.body().substring(0, 11));
                                studuentInfo.setSEmail("NF");



                            }else if(phone==true && email==false){
                                enteremail.setText(response.body().substring(2));
                                entermobile.setText(R.string.nofound);
                                showdata();
                                studuentInfo.setSEmail(response.body().substring(2));
                                studuentInfo.setSMobile("NF");
                            }
                }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    void statInfromation(){
        code.setText(studuentInfo.getId());
        switch (studuentInfo.getDepartment()){
            case "1":
                department.setText(getResources().getString(R.string.Sc));
                break;
            case "2":
                department.setText(getResources().getString(R.string.IS));
                break;
            case "3":
                department.setText("ادارة عربي");
                break;
            case "4":
                department.setText("ادارة انجليزي");
                break;
        }
        switch (studuentInfo.getLevel()){
            case "1":
                land.setText(getResources().getString(R.string.one));
                break;
            case "2":
                land.setText(getResources().getString(R.string.two));
                break;
            case "3":
                land.setText(getResources().getString(R.string.thired));
                break;

            case "4":
                land.setText(getResources().getString(R.string.foure));
                break;
            case "5":
                land.setText(getResources().getString(R.string.graduate));
        }

    }
void showdata(){
        mobile.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        entermobile.setVisibility(View.VISIBLE);
        enteremail.setVisibility(View.VISIBLE);
}


}