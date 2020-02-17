package com.ehabahmed.studentcertificate;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Result_Student extends AppCompatActivity implements View.OnClickListener {
EditText username,password;
RequestQueue requestQueue;
Button senddata;
Info info;
    String pass,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__student);
requestQueue= Volley.newRequestQueue(Result_Student.this);
username=findViewById(R.id.enterusername);
password=findViewById(R.id.enterpass);
senddata=findViewById(R.id.login2);
senddata.setOnClickListener(this);
info=(Info) getApplicationContext();
        setTitle(getResources().getString(R.string.result));
    }

    @Override
    public void onClick(View view) {
 pass=password.getText().toString().trim();
       user=username.getText().toString().trim();
        if(user.isEmpty()){
            username.setError(getResources().getString(R.string.enterusername));
        }
        else if(pass.isEmpty()){
            password.setError(getResources().getString(R.string.enterpassword));

        }else{
            senddata();
        }

    }

    private void senddata() {
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
           url="https://ehab01998.com/Result.php";
        }else{
            url="http://ehab01998.com/Result.php";

        }

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("no")){
                    password.setError(null);
                    Intent intent = new Intent(Result_Student.this, Show_Result.class);
                    intent.putExtra("result", response);
                    startActivity(intent);
                }else {
                    password.setError(getString(R.string.checkuserandpass));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                password.setError(null);
                Toast.makeText(info, getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("email",user);
                params.put("pass",pass);
                params.put("department",info.getDepartment());
                return params;
            }
        };
        requestQueue.add(request);
    }
}
