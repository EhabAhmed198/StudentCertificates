package com.ehabahmed.studentcertificate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.jar.Attributes;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditYPP extends AppCompatActivity implements View.OnClickListener {
String Newphoto=null,code,name,mobile,email,Oldphoto;
EditText uname,ucode,umobile,uemail;
TextView tv_uphoto;
ImageView Im_uphoto;
    Intent    intent;
    Info info;
Retrofit retrofit;
    ApiConfig apiConfig;
    Button update;
    ProgressBar pr_EditYPP;

GsonBuilder builder;
Gson gson;
    String check="old";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ypp);
builder=new GsonBuilder();
builder.setLenient();
gson=builder.create();

        tv_uphoto=findViewById(R.id.editphoto);
        pr_EditYPP=findViewById(R.id.pr_EditYPP);
        Im_uphoto=findViewById(R.id.iv_profile);

        update=findViewById(R.id.update);
        info=(Info)getApplicationContext();
        Newphoto=info.getPhoto();
        mobile=info.getSMobile();
        email=info.getSEmail();
        uname=findViewById(R.id.uname);
        ucode=findViewById(R.id.ucode);
        umobile=findViewById(R.id.umobile);
        uemail=findViewById(R.id.uemail);
        setPersonalData();
        tv_uphoto.setOnClickListener(this);
        Im_uphoto.setOnClickListener(this);
        update.setOnClickListener(this);

    }

    void setPersonalData(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.profile2).circleCrop().load("https://ehab01998.com/images_profile/"+info.getPhoto())
                    .into(Im_uphoto);
        }else{
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.profile2).circleCrop().load("http://ehab01998.com/images_profile/"+info.getPhoto())
                    .into(Im_uphoto);
        }
        uname.setText(info.getName());
        ucode.setText(info.getId());
        if(!info.getSMobile().equals("NF"))
            umobile.setText(info.getSMobile());
        if(!info.getSEmail().equals("NF"))
            uemail.setText(info.getSEmail());


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.editphoto || v.getId()==R.id.iv_profile){

         intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent,0);
        }else if(v.getId()==R.id.update){


            pr_EditYPP.setVisibility(View.VISIBLE);
                name=uname.getText().toString().trim();
                code=ucode.getText().toString().trim();
                Oldphoto=info.getPhoto();
             if (umobile.getText().toString().trim().length()>0)
                 mobile=umobile.getText().toString().trim();
             if(uemail.getText().toString().trim().length()>0)
                 email=uemail.getText().toString().trim();
            update.setEnabled(false);
            uname.setEnabled(false);
            ucode.setEnabled(false);
            umobile.setEnabled(false);
            uemail.setEnabled(false);
             if(check.equals("new"))
                 SendDataPart1(check,info.getId(),Newphoto,name,mobile,email,code,Oldphoto);
             else if(check.equals("old"))
                 SendDataPart2(check,info.getId(),name,mobile,email,code,Oldphoto);



        }

    }
    private void SendDataPart2(String state,String oldcode, final String name, final String phone, final String mail, final String code, String oldphoto) {
        retrofit = new Retrofit.Builder().baseUrl("http://ehab01998.com/").addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiConfig = retrofit.create(ApiConfig.class);

        final RequestBody COldPhoto = RequestBody.create(MediaType.parse("multipart/form-data"), oldphoto);
        RequestBody OldeCode = RequestBody.create(MediaType.parse("multipart/form-data"), oldcode);
        RequestBody CCode = RequestBody.create(MediaType.parse("multipart/form-data"), code);
        RequestBody CName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody CPhone = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody CEmail = RequestBody.create(MediaType.parse("multipart/form-data"), mail);
        RequestBody Cstate = RequestBody.create(MediaType.parse("multipart/form-data"), state);

        apiConfig.changeData2(Cstate,OldeCode,COldPhoto,CCode,CName,CPhone,CEmail).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                info.setId(code);
                info.setName(name);
                info.setSEmail(mail);
                info.setSMobile(phone);
              SharedPreferences  sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor=sharedPreferences.edit();
               editor.putString("username",name);
               editor.apply();

                Intent intent=new Intent(EditYPP.this,Student_peofile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EditYPP.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                pr_EditYPP.setVisibility(View.INVISIBLE);
                update.setEnabled(true);
                uname.setEnabled(true);
                ucode.setEnabled(true);
                umobile.setEnabled(true);
                uemail.setEnabled(true);


            }
        });


    }

    private void SendDataPart1(String state,String oldcode, String newphoto, final String name, final String phone, final String mail, final String code, String oldphoto) {
       retrofit = new Retrofit.Builder().baseUrl("http://ehab01998.com/")
               .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiConfig = retrofit.create(ApiConfig.class);

        RequestBody COldPhoto = RequestBody.create(MediaType.parse("multipart/form-data"), oldphoto);
        RequestBody OldeCode = RequestBody.create(MediaType.parse("multipart/form-data"), oldcode);
        RequestBody CCode = RequestBody.create(MediaType.parse("multipart/form-data"), code);
        RequestBody CName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody CPhone = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody CEmail = RequestBody.create(MediaType.parse("multipart/form-data"), mail);
        RequestBody Cstate = RequestBody.create(MediaType.parse("multipart/form-data"), state);

        File photo1 = new File(newphoto);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photo1);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("NewImg", photo1.getName(), requestFile);
apiConfig.changeData1(Cstate,OldeCode,COldPhoto,CCode,CName,CPhone,CEmail,multipartBody).enqueue(new Callback<String>() {
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        info.setPhoto(response.body().toString().trim());
        info.setId(code);
        info.setName(name);
        info.setSEmail(mail);
        info.setSMobile(phone);


        SharedPreferences  sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",name);
        editor.apply();

        Intent intent=new Intent(EditYPP.this,Student_peofile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(intent);

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Toast.makeText(EditYPP.this, getResources().getString(R.string.Error), Toast.LENGTH_SHORT).show();
        pr_EditYPP.setVisibility(View.INVISIBLE);
        update.setEnabled(true);
        uname.setEnabled(true);
        ucode.setEnabled(true);
        umobile.setEnabled(true);
        uemail.setEnabled(true);

    }
});


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){
            
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.profile2).circleCrop().load(data.getData())
                    .into(Im_uphoto);
            check="new";
            String[] filePathColumn = { MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            Newphoto= cursor.getString(columnIndex);
            cursor.close();
        }
    }
}
