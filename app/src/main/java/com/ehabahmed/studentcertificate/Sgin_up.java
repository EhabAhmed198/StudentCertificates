package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.text.Selection;

import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Sgin_up extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
ImageView iv_profile;
EditText username,code,password,configpassword;
Spinner department,band;
Bitmap  bitmap;
String url;
String encoding;
Button sign_up;
int student_info_level;
int department_id;
String[] data_department,data_band;
String name,id,pass1,pass2,imagename;
    Bitmap   relized;
    Intent intent;
    ArrayAdapter<String> adapter1,adapter2;
    RadioGroup radioGroup;
    ConstraintLayout group1,group2;
String type;
ImageView div_profile;
EditText dusername,demail,dphone,dpassword,dconfigpassword;
String email,phone;
RequestQueue requestQueue;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgin_up);
        type="student";
        requestQueue=Volley.newRequestQueue(this);
        group1=findViewById(R.id.group1);
        group2=findViewById(R.id.group2);
        div_profile=findViewById(R.id.div_profile);
        dusername=findViewById(R.id.denterusername);
        demail=findViewById(R.id.denteremail);
        dphone=findViewById(R.id.denterphone);
        dpassword=findViewById(R.id.denterpassword);
        dconfigpassword=findViewById(R.id.dconfermpassword);
        iv_profile=findViewById(R.id.iv_profile);
        username=findViewById(R.id.enterusername);
        code=findViewById(R.id.entercode);
        radioGroup=(RadioGroup)findViewById(R.id.groups);
        radioGroup.setOnCheckedChangeListener(this);

        password=findViewById(R.id.enterpassword);
        configpassword=findViewById(R.id.configenterpassword);
        department=findViewById(R.id.enterdepartment);
        band=findViewById(R.id.enterband);
        sign_up=findViewById(R.id.sgin_up);
        data_department=getResources().getStringArray(R.array.departments);
        data_band=getResources().getStringArray(R.array.lans);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_profile);

        iv_profile.setOnClickListener(this);
        div_profile.setOnClickListener(this);



        sign_up.setOnClickListener(this);

        adapter1=new ArrayAdapter<>(this,R.layout.spinner,R.id.text_spinner,data_department);
        adapter2=new ArrayAdapter<>(this,R.layout.spinner,R.id.text_spinner,data_band);




         department.setAdapter(adapter1);
         band.setAdapter(adapter2);

        dusername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dusername.setText("Dr.");
                Selection.setSelection(dusername.getText(),dusername.length());
                return false;
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0 && resultCode==RESULT_OK && data!=null){
Glide.with(this).asBitmap().circleCrop().override(600,200).load(data.getData()).into(new CustomTarget<Bitmap>() {
    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
       bitmap=resource;

       if(type.equals("student"))
       iv_profile.setImageBitmap(bitmap);
       else if(type.equals("doctor"))
           div_profile.setImageBitmap(bitmap);
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }
});




        }
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.iv_profile:
        intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,0); break;
    case R.id.div_profile:
         intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,0); break;


    case R.id.sgin_up:
        senddata();break;
}
    }
    public String convertImage(){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        encoding= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        return encoding;

    }
  public void senddata(){
        if(type.equals("student")) {
            getConvertSpinnerData();
            name = username.getText().toString().trim();
            id = code.getText().toString().trim();
            pass1 = password.getText().toString().trim();
            pass2 = configpassword.getText().toString().trim();
            imagename = convertImage();


            if (name.isEmpty()) {
                username.setError(getResources().getString(R.string.enterusername));

            }else if(name.startsWith("Dr.")){
                username.setError(getResources().getString(R.string.errorDr));
            }

            else if (id.isEmpty()) {
                code.setError(getResources().getString(R.string.entercode));
            } else if (pass1.isEmpty()) {
                password.setError(getResources().getString(R.string.enterpassword));
            } else if (pass1.length() < 6) {
                password.setError(getResources().getString(R.string.enterpassbigersix));
            } else if (pass2.isEmpty()) {
                configpassword.setError(getResources().getString(R.string.enterpassword));
            } else if (!pass1.equals(pass2)) {
                configpassword.setError(getResources().getString(R.string.enterpassequalpass));
            } else {
                senddatastudent();


            }
        }else if(type.equals("doctor")){
            name=dusername.getText().toString().trim();
            email=demail.getText().toString().trim();
            phone=dphone.getText().toString().trim();

            pass1 = dpassword.getText().toString().trim();
            pass2 = dconfigpassword.getText().toString().trim();
            imagename = convertImage();
            if (name.isEmpty()) {
                dusername.setError(getResources().getString(R.string.enterusername));

            }else if(!name.startsWith("Dr.")){
                new AlertDialog.Builder(Sgin_up.this)
                       .setTitle(getResources().getString(R.string.professorname)).setMessage(getResources().getString(R.string.explane)).setPositiveButton(getResources().getString(R.string.ok),null).show();
            }
            else if(email.isEmpty()){
                demail.setError("");
            }else if(phone.isEmpty()){
                dphone.setError("");
            }
            else if (pass1.length() < 6) {
                dpassword.setError(getResources().getString(R.string.enterpassbigersix));
            }
            else if (pass2.isEmpty()) {
                dconfigpassword.setError(getResources().getString(R.string.enterpassword));
            } else if (!pass1.equals(pass2)) {
                dconfigpassword.setError(getResources().getString(R.string.enterpassequalpass));
            } else {
                senddatadoctor();


            }
            
        }

  }

    private void senddatadoctor() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/doctorInformation.php";
        }else{
            url="http://ehab01998.com/doctorInformation.php";

        }

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               if(response.equals("تم التسجيل بنجاح")){
                   new AlertDialog.Builder(Sgin_up.this)
                           .setTitle(R.string.sginUpScuccess)
                           .setMessage(getResources().getString(R.string.nameuser)+name+"\n"+getResources().getString(R.string.pass)+pass1)
                           .setPositiveButton(getResources().getString(R.string.ok),null).show();
                      dusername.setError(null);
                      demail.setError(null);
                      dphone.setError(null);
                      dpassword.setError(null);


               }else
                Toast.makeText(Sgin_up.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Sgin_up.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringStringMap=new HashMap<String, String>();

                stringStringMap.put("doctor_name",name);
                stringStringMap.put("doctor_photo",imagename);
                stringStringMap.put("doctor_password",pass1);
                stringStringMap.put("mobile",phone);
                stringStringMap.put("email",email);


                return stringStringMap;
            }
        };
        requestQueue.add(request);
    }

    private void senddatastudent() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/studentInformation.php";
        }else{
            url="http://ehab01998.com/studentInformation.php";

        }

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(Sgin_up.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Sgin_up.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringStringMap=new HashMap<String, String>();
                stringStringMap.put("student_info_id",id);
                stringStringMap.put("student_info_name",name);
                stringStringMap.put("student_info_password",pass1);
                stringStringMap.put("student_info_photo",imagename);
                stringStringMap.put("student_info_level", String.valueOf(student_info_level));
                stringStringMap.put("department_id", String.valueOf(department_id));
                return stringStringMap;
            }
        };
        requestQueue.add(request);



    }

    public void getConvertSpinnerData(){
        if(department.getSelectedItem().toString().equals(data_department[0])){
            department_id=1;
        }
        else   if(department.getSelectedItem().toString().equals(data_department[1])){
            department_id=2;
        }
        else   if(department.getSelectedItem().toString().equals(data_department[2])){
            department_id=3;
        }
        else   if(department.getSelectedItem().toString().equals(data_department[3])){

            department_id=4;
        }

      if(band.getSelectedItem().toString().equals(data_band[0])){
          student_info_level=1;
      }
      else if(band.getSelectedItem().toString().equals(data_band[1])){
          student_info_level=2;
      }
      else if(band.getSelectedItem().toString().equals(data_band[2])){
          student_info_level=3;
      }
      else if(band.getSelectedItem().toString().equals(data_band[3])){
          student_info_level=4;
      }
      else if(band.getSelectedItem().toString().equals(data_band[4])){
          student_info_level=5;
      }


 }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(String.valueOf(radioGroup.getCheckedRadioButtonId()).equals(String.valueOf(R.id.student))){
          group1.setVisibility(View.VISIBLE);
          group2.setVisibility(View.INVISIBLE);
          type="student";

        }else if(String.valueOf(radioGroup.getCheckedRadioButtonId()).equals(String.valueOf(R.id.doctor))){
        group2.setVisibility(View.VISIBLE);
        group1.setVisibility(View.INVISIBLE);
        type="doctor";
        }
        
    }
}
