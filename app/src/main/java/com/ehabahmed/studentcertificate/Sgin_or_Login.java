package com.ehabahmed.studentcertificate;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;


import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ScrollView;

import android.widget.Toast;
import android.widget.VideoView;


import com.android.volley.AuthFailureError;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Sgin_or_Login extends Fragment implements View.OnClickListener {

ConstraintLayout page_login;
Button login1,sign_up,login2;
EditText username,password;
    String user,pass;
    String url;
SharedPreferences sharedPreferences,sharedPreferences1,sharedPreferences2;
    SharedPreferences.Editor editor,editor1,editor2;
    RequestQueue requestQueue;
    ScrollView.LayoutParams params;
    ConstraintLayout makecenter;
    Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sgin_or__login, container, false);





        page_login=view.findViewById(R.id.page_login);
       login1=view.findViewById(R.id.login1);
       username=view.findViewById(R.id.enterusername);
       password=view.findViewById(R.id.enterpassword);
       sign_up=view.findViewById(R.id.sign_up);
       login2=view.findViewById(R.id.login2);
        makecenter=view.findViewById(R.id.makecenter);
        sharedPreferences=getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        sharedPreferences1 =getContext().getSharedPreferences("student", Context.MODE_PRIVATE);
        sharedPreferences2 = getContext().getSharedPreferences("doctor", Context.MODE_PRIVATE);



       requestQueue=Volley.newRequestQueue(getContext());
       login2.setOnClickListener(this);
       sign_up.setOnClickListener(this);
       login1.setOnClickListener(this);
      params = new ScrollView.LayoutParams(
              ScrollView.LayoutParams.MATCH_PARENT,
              ScrollView.LayoutParams.WRAP_CONTENT);
      params.setMargins(0,0,0,0);
       return view;
    }


    @Override
    public void onClick(View v) {
switch(v.getId()){

    case R.id.login1:
        makecenter.setLayoutParams(params);
        page_login.setVisibility(View.VISIBLE); break;
    case R.id.sign_up:
        startActivity(new Intent(getContext(),Sgin_up.class));break;
    case R.id.login2:

        senddata();

        break;
}
    }

    private void senddata() {
        user=username.getText().toString().trim();
        pass=password.getText().toString().trim();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/ulogin.php";
        }else{
            url="http://ehab01998.com/ulogin.php";

        }


        StringRequest  request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SharedPreferences   sharedPreferences2 = getContext().getSharedPreferences("Notification", Context.MODE_PRIVATE);
                SharedPreferences.Editor   editor2 = sharedPreferences2.edit();
                editor2.putString("type", "-1");
                editor2.apply();
                switch (response){
                    case "student":

                       getDataStudnt();
                        break;
                    case "doctor":

                        getDataDoctor();
                        break;
                    case "certificate":
                          getDataCertificate();
                        break;
                    case "No found":
                      password.setError(getString(R.string.checkuserandpass));
                        break;
                }



            }  }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("student_info_name",user);
                params.put("student_info_password",pass);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }

    private void getDataCertificate() {
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("student_info_name", user);
        params.put("student_info_password", pass);



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/ustudentInfo.php";
        }else{
            url="http://ehab01998.com/ustudentInfo.php";

        }
        CustomRequest request=new CustomRequest(Request.Method.POST, url,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray array=response.getJSONArray("student");
                    JSONObject current=array.getJSONObject(0);
                    String id=current.getString("student_info_id");
                    String name=current.getString("student_info_name");
                    String pass=current.getString("student_info_password");
                    String photo=current.getString("student_info_photo");
                    String level=current.getString("student_info_level");
                    String department=current.getString("department_id");
                    intent = new Intent(getContext(), Certificate.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("pass",pass);
                    intent.putExtra("photo",photo);
                    intent.putExtra("department",department);
                    intent.putExtra("level",level);
                    startActivity(intent);
                    Toast.makeText(getContext(), R.string.successlogin, Toast.LENGTH_SHORT).show();
                    editor=sharedPreferences.edit();
                    editor.putString("username",user);
                    editor.putString("password",pass);
                    editor.putString("type","certificate");
                    editor.apply();

                    getActivity().finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(request);

    }


    private void getDataDoctor(){
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("doctor_name", user);
        params.put("doctor_password", pass);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/udoctorInfo.php";
        }else{
            url="http://ehab01998.com/udoctorInfo.php";

        }

        CustomRequest request=new CustomRequest(Request.Method.POST, url,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Doctor");
                    JSONObject current=array.getJSONObject(0);
                    String doctor_id=current.getString("doctor_id");
                    String doctor_name=current.getString("doctor_name");
                    String doctor_photo=current.getString("doctor_photo");
                    String doctor_password=current.getString("doctor_password");
                    String mobile=current.getString("mobile");
                    String email=current.getString("email");
                    intent=new Intent(getContext(),Doctor.class);
                    intent.putExtra("doctor_id",doctor_id);
                    intent.putExtra("doctor_name",doctor_name);
                    intent.putExtra("doctor_photo",doctor_photo);
                    intent.putExtra("doctor_password",doctor_password);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    Toast.makeText(getContext(), R.string.successlogin, Toast.LENGTH_SHORT).show();
                    editor=sharedPreferences.edit();
                    editor.putString("username",user);
                    editor.putString("password",pass);
                    editor.putString("type","doctor");
                    editor.apply();


                    editor2=sharedPreferences2.edit();
                    editor2.putString("id",doctor_id);
                    editor2.putString("name",doctor_name);
                    editor2.putString("pass",doctor_password);
                    editor2.putString("photo",doctor_photo);
                    editor2.putString("mobile",mobile);
                    editor2.putString("email",email);
                    editor2.apply();
                    getActivity().finish();

                } catch (JSONException e) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }



    private void getDataStudnt() {

        HashMap<String,String> params=new HashMap<String, String>();
            params.put("student_info_name", user);
            params.put("student_info_password", pass);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/ustudentInfo.php";
        }else{
            url="http://ehab01998.com/ustudentInfo.php";

        }



        CustomRequest request=new CustomRequest(Request.Method.POST, url,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray array=response.getJSONArray("student");
                    JSONObject current=array.getJSONObject(0);
                    String id=current.getString("student_info_id");
                    String name=current.getString("student_info_name");
                    String pass=current.getString("student_info_password");
                    String photo=current.getString("student_info_photo");
                    String level=current.getString("student_info_level");
                    String department=current.getString("department_id");
                    intent = new Intent(getContext(), Student.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("pass",pass);
                    intent.putExtra("photo",photo);
                    intent.putExtra("department",department);
                    intent.putExtra("level",level);
                    startActivity(intent);
                    Toast.makeText(getContext(), R.string.successlogin, Toast.LENGTH_SHORT).show();
                    editor=sharedPreferences.edit();
                    editor.putString("username",user);
                    editor.putString("password",pass);
                    editor.putString("type","student");
                    editor.apply();

                    editor1=sharedPreferences1.edit();
                    editor1.putString("id",id);
                    editor1.putString("name",name);
                    editor1.putString("pass",pass);
                    editor1.putString("photo",photo);
                    editor1.putString("department",department);
                    editor1.putString("level",level);
                    editor1.apply();
                    getActivity().finish();


                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),R.string.no_communcationInternet, Toast.LENGTH_SHORT).show();

            }
        });
     requestQueue.add(request);
    }

}
