package com.ehabahmed.studentcertificate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;




import java.io.File;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraduationCertificate extends AppCompatActivity implements View.OnClickListener {
Spinner entergraduationLanguage,entergraduationtype,departments,GraduationYear,Graduationmonth;
String[] languages,type,department,yearss,months;
int department_id;
ArrayAdapter<String> LanguageAdapter,typeAdapter,departmentsAdapter,yearAdapter,monthAdapter;
EditText name,code;
TextView uploadImageID,uploadImageGraduation,uploadImageAdd;
int photo1=0,photo2=0,photo3=0;
String pphoto1="null",pphoto2="null",pphoto3="null";
    Drawable img;
    Button send;
    File  photo,ontherphoto;
    ConstraintLayout constraintLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduation_certificate);
        send=findViewById(R.id.send);
        send.setOnClickListener(this);
        name=findViewById(R.id.enterName);
        code=findViewById(R.id.code);
        entergraduationLanguage=findViewById(R.id.entergraduationLanguage);
        entergraduationtype=findViewById(R.id.entergraduationtype);
        uploadImageID=findViewById(R.id.photoId);
        uploadImageGraduation=findViewById(R.id.gradutionphoto);
        uploadImageAdd=findViewById(R.id.addphoto);
        departments=findViewById(R.id.enterdepartment);
        GraduationYear=findViewById(R.id.GraduationYear);
constraintLayout=findViewById(R.id.containers);
progressBar=findViewById(R.id.progressbar);
        uploadImageID.setOnClickListener(this);
        uploadImageGraduation.setOnClickListener(this);
        uploadImageAdd.setOnClickListener(this);


        Graduationmonth=findViewById(R.id.Graduationmonth);
        yearss=getResources().getStringArray(R.array.yearss);
        months=getResources().getStringArray(R.array.month);
         languages=getResources().getStringArray(R.array.languages);
        type=getResources().getStringArray(R.array.type);
       department=getResources().getStringArray(R.array.departments);
        LanguageAdapter=new ArrayAdapter<>(GraduationCertificate.this,R.layout.spinner2,R.id.text_spinner,languages);
        typeAdapter=new ArrayAdapter<>(GraduationCertificate.this,R.layout.spinner2,R.id.text_spinner,type);
        yearAdapter=new ArrayAdapter<>(GraduationCertificate.this,R.layout.spinner2,R.id.text_spinner,yearss);
        monthAdapter=new ArrayAdapter<>(GraduationCertificate.this,R.layout.spinner2,R.id.text_spinner,months);

        departmentsAdapter=new ArrayAdapter<>(GraduationCertificate.this,R.layout.spinner2,R.id.text_spinner,department);

        departments.setAdapter(departmentsAdapter);
        entergraduationtype.setAdapter(typeAdapter);
        entergraduationLanguage.setAdapter(LanguageAdapter);
        GraduationYear.setAdapter(yearAdapter);
        Graduationmonth.setAdapter(monthAdapter);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.photoId){

            uploadPhoto(0);
        }
        else if(view.getId()==R.id.gradutionphoto){

            uploadPhoto(1);

        }
        else if(view.getId()==R.id.addphoto){

            uploadPhoto(2);
        }else if(view.getId()==R.id.send){


           if(photo1==1 && photo2==1 && photo3==1) {
               String getname=name.getText().toString().trim();
               String getcode=code.getText().toString().trim();
               if(getname.isEmpty()){
                   name.setError(getResources().getString(R.string.entername));
               }else if(getcode.isEmpty()){
                   code.setError(getResources().getString(R.string.entercode));
               }else{
                   sendphoto1();
               }

           }else if(photo1==1 && photo2==0 && photo3==0   ){
               new AlertDialog.Builder(GraduationCertificate.this)
                       .setMessage(getResources().getString(R.string.addimage)).setPositiveButton(getResources().getString(R.string.ok),null).show();
           }
           else if(photo1==0 && photo2==1 && photo3==0   ){
               new AlertDialog.Builder(GraduationCertificate.this)
                       .setMessage(getResources().getString(R.string.addimage)).setPositiveButton(getResources().getString(R.string.ok),null).show();
           }else if(photo1==0 && photo2==0 && photo3==1   ){

               String getname=name.getText().toString().trim();
               String getcode=code.getText().toString().trim();
               if(getname.isEmpty()){
                   name.setError(getResources().getString(R.string.entername));
               }else if(getcode.isEmpty()){
                   code.setError(getResources().getString(R.string.entercode));
               }else{
                   sendphoto2();
               }
           }else if(photo1==0 && photo2==0 && photo3==0   ){
               new AlertDialog.Builder(GraduationCertificate.this)
                       .setMessage(getResources().getString(R.string.addimage)).setPositiveButton(getResources().getString(R.string.ok),null).show();
           }else if(photo1==1 && photo2==1 && photo3==0){
               new AlertDialog.Builder(GraduationCertificate.this)
                       .setMessage(getResources().getString(R.string.addimage)).setPositiveButton(getResources().getString(R.string.ok),null).show();
           }else if(photo1==1 && photo2==0 && photo3==1){
               String getname=name.getText().toString().trim();
               String getcode=code.getText().toString().trim();
               if(getname.isEmpty()){
                   name.setError(getResources().getString(R.string.entername));
               }else if(getcode.isEmpty()){
                   code.setError(getResources().getString(R.string.entercode));
               }else{
                   sendphoto3("6");
               }

           }else if(photo1==0 && photo2==1 && photo3==1){
               String getname=name.getText().toString().trim();
               String getcode=code.getText().toString().trim();
               if(getname.isEmpty()){
                   name.setError(getResources().getString(R.string.entername));
               }else if(getcode.isEmpty()){
                   code.setError(getResources().getString(R.string.entercode));
               }else{
                   sendphoto3("7");
               }

           }



        }

    }

    private void sendphoto3(String phototype) {
progressBar.setVisibility(View.VISIBLE);
constraintLayout.setVisibility(View.INVISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/";
        }else{
            url="http://ehab01998.com/";

        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiConfig apiConfig = retrofit.create(ApiConfig.class);
 if(phototype.equals("6")){

    photo=new File(pphoto1);
    ontherphoto=new File(pphoto3);
}
else if(phototype.equals("7")){

    photo=new File(pphoto2);
    ontherphoto=new File(pphoto3);
}


        RequestBody types = RequestBody.create(MediaType.parse("multipart/form-data"), phototype);
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), entergraduationtype.getSelectedItem().toString());
        RequestBody name1 = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
        RequestBody department = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(getConvertSpinnerData()));
        RequestBody code1 = RequestBody.create(MediaType.parse("multipart/form-data"),code.getText().toString());
        RequestBody year = RequestBody.create(MediaType.parse("multipart/form-data"), GraduationYear.getSelectedItem().toString());
        RequestBody month = RequestBody.create(MediaType.parse("multipart/form-data"), Graduationmonth.getSelectedItem().toString());


        RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), ontherphoto);
        RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), "null");

        MultipartBody.Part multipartBody1 = MultipartBody.Part.createFormData("photoID", photo.getName(), requestFile1);
        MultipartBody.Part multipartBody2 = MultipartBody.Part.createFormData("photoPersonal", ontherphoto.getName(), requestFile2);


        apiConfig.uploadImage2(types,type,name1,department,code1,requestFile3,multipartBody1,multipartBody2,year,month)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        new AlertDialog.Builder(GraduationCertificate.this)
                                .setMessage(getResources().getString(R.string.senddata)).setPositiveButton(getResources().getString(R.string.ok),null).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(GraduationCertificate.this, getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void sendphoto2() {
progressBar.setVisibility(View.VISIBLE);
constraintLayout.setVisibility(View.INVISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/";
        }else{
            url="http://ehab01998.com/";

        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiConfig apiConfig = retrofit.create(ApiConfig.class);


           photo= new File(pphoto3);



        RequestBody types = RequestBody.create(MediaType.parse("multipart/form-data"), "3");
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), entergraduationtype.getSelectedItem().toString());
        RequestBody name1 = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
        RequestBody department = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(getConvertSpinnerData()));
        RequestBody code1 = RequestBody.create(MediaType.parse("multipart/form-data"),code.getText().toString());
        RequestBody year = RequestBody.create(MediaType.parse("multipart/form-data"), GraduationYear.getSelectedItem().toString());
        RequestBody month = RequestBody.create(MediaType.parse("multipart/form-data"), Graduationmonth.getSelectedItem().toString());


        RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), "null");
        RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), "null");

        MultipartBody.Part multipartBody1 = MultipartBody.Part.createFormData("photoID", photo.getName(), requestFile1);


        apiConfig.uploadImage1(types,type,name1,department,code1,multipartBody1,requestFile2,requestFile3,year,month)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                          progressBar.setVisibility(View.INVISIBLE);
                          constraintLayout.setVisibility(View.VISIBLE);
                        new AlertDialog.Builder(GraduationCertificate.this)
                                .setMessage(getResources().getString(R.string.senddata)).setPositiveButton(getResources().getString(R.string.ok),null).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        progressBar.setVisibility(View.INVISIBLE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(GraduationCertificate.this, getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void sendphoto1() {

        progressBar.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/";
        }else{
            url="http://ehab01998.com/";

        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiConfig apiConfig = retrofit.create(ApiConfig.class);


        File photo1 = new File(pphoto1);
        File photo2 = new File(pphoto2);
        File photo3 = new File(pphoto3);
        RequestBody types = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), entergraduationtype.getSelectedItem().toString());
        RequestBody name1 = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
        RequestBody department = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(getConvertSpinnerData()));
        RequestBody code1 = RequestBody.create(MediaType.parse("multipart/form-data"),code.getText().toString());
        RequestBody year = RequestBody.create(MediaType.parse("multipart/form-data"), GraduationYear.getSelectedItem().toString());
        RequestBody month = RequestBody.create(MediaType.parse("multipart/form-data"), Graduationmonth.getSelectedItem().toString());

        RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), photo1);
        RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), photo2);
        RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), photo3);

        MultipartBody.Part multipartBody1 = MultipartBody.Part.createFormData("photoID", photo1.getName(), requestFile1);
        MultipartBody.Part multipartBody2 = MultipartBody.Part.createFormData("photoPersonal", photo2.getName(), requestFile2);
        MultipartBody.Part multipartBody3 = MultipartBody.Part.createFormData("photoGradaution", photo3.getName(), requestFile3);
       apiConfig.uploadImage(types,type,name1,department,code1,multipartBody1,multipartBody2,multipartBody3,year,month)
             .enqueue(new Callback<ResponseBody>() {
                 @Override
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
progressBar.setVisibility(View.INVISIBLE);
constraintLayout.setVisibility(View.VISIBLE);
                     new AlertDialog.Builder(GraduationCertificate.this)
                             .setMessage(getResources().getString(R.string.senddata)).setPositiveButton(getResources().getString(R.string.ok),null).show();
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                     progressBar.setVisibility(View.INVISIBLE);
                     constraintLayout.setVisibility(View.VISIBLE);
                     Toast.makeText(GraduationCertificate.this, getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();

                 }
             });

    }



    private void uploadPhoto(int requestcode) {

        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,requestcode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA};
            photo1=1;
            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            pphoto1= cursor.getString(columnIndex);
            cursor.close();
            img = getResources().getDrawable(R.drawable.correct);
            uploadImageID.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        }else       if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            photo2=1;
            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            pphoto2= cursor.getString(columnIndex);
            cursor.close();
            img = getResources().getDrawable(R.drawable.correct);
            uploadImageGraduation.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        }else       if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            photo3=1;
            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            pphoto3= cursor.getString(columnIndex);
            cursor.close();
            img = getResources().getDrawable(R.drawable.correct);
            uploadImageAdd.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        }






    }

    public int getConvertSpinnerData(){
        if(departments.getSelectedItem().toString().equals(department[0])){
          return  department_id=1;
        }
        else   if(departments.getSelectedItem().toString().equals(department[1])){
          return  department_id=2;
        }
        else   if(departments.getSelectedItem().toString().equals(department[2])){
           return department_id=3;
        }
        return -1;

    }


}
