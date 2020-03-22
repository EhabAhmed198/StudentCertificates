package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Groups extends AppCompatActivity implements View.OnClickListener {
RecyclerView groupNames,groupWiat;
LinearLayoutManager vertical,horzental;
Retrofit retrofit;
ApiConfig apiConfig;
GroupNameAdapter adapter;
GroupWaitAdapter waitAdapter;
ArrayList<DataGroup> listitems;
ArrayList<NewGroupAdd> listitemGroupWait;
Info info;
String id;
String urlPhoto="NF";
    ImageView iv_uiploadPhoto;
    Dialog dialog;
    GsonBuilder builder;
    Gson gson;
    EditText name,infogroup;
    ProgressBar pb_group;
    Button ok,cacell;
    String AddGroupFromNotification="NO",changeStateId;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private  BiometricPrompt.PromptInfo promptInfo;
    SharedPreferences grouplock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        grouplock=getSharedPreferences("fingreprint", Context.MODE_PRIVATE);;
        if( grouplock.getBoolean("grouplock",false)){
            fingreprint();

        }
        builder=new GsonBuilder();
        builder.setLenient();
        gson=builder.create();


        pb_group=findViewById(R.id.pb_group);
        info=(Info)getApplicationContext();
        if(info.getType().equals("student")){
            id=info.getId();
        }
        else    if(info.getType().equals("doctor")){
            id=info.getDoctor_id();
        }

        groupNames=findViewById(R.id.rv_groupsName);
        groupWiat=findViewById(R.id.rv_groupsNotification);
        horzental=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        vertical=new LinearLayoutManager(this);
        groupNames.setLayoutManager(horzental);
        groupWiat.setLayoutManager(vertical);
        listitems=new ArrayList<>();
        retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiConfig=retrofit.create(ApiConfig.class);
        getGroupsWait();
       getGroups();




    }

    private void getGroupsWait() {
        apiConfig.getWaitStateStudentGroup(id).enqueue(new Callback<ArrayList<NewGroupAdd>>() {
            @Override
            public void onResponse(Call<ArrayList<NewGroupAdd>> call, Response<ArrayList<NewGroupAdd>> response) {
                listitemGroupWait=response.body();
                waitAdapter=new GroupWaitAdapter(Groups.this,listitemGroupWait);
                groupWiat.setAdapter(waitAdapter);
                pb_group.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ArrayList<NewGroupAdd>> call, Throwable t) {
                Toast.makeText(info, t.getMessage(), Toast.LENGTH_SHORT).show();
                pb_group.setVisibility(View.INVISIBLE);


            }
        });
    }

    private void getGroups() {
        retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.getDataGroup(id).enqueue(new Callback<ArrayList<DataGroup>>() {
            @Override
            public void onResponse(Call<ArrayList<DataGroup>> call, Response<ArrayList<DataGroup>> response) {
                listitems=response.body();
                adapter=new GroupNameAdapter(Groups.this,listitems);
                groupNames.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<ArrayList<DataGroup>> call, Throwable t) {
                Toast.makeText(Groups.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addgroup,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addGroup){

             dialog=new Dialog(Groups.this);
            dialog.setContentView(R.layout.dialog_create_group);
            dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);

       name=dialog.findViewById(R.id.nameGroup);
       infogroup=dialog.findViewById(R.id.infoGroup);
            TextView tv_uploadPhoto=dialog.findViewById(R.id.tv_uploadPhotoGroup);
          iv_uiploadPhoto=dialog.findViewById(R.id.iv_uploadPhotoGroup);
            tv_uploadPhoto.setOnClickListener(this);
            iv_uiploadPhoto.setOnClickListener(this);
          ok=dialog.findViewById(R.id.ok);
    cacell=dialog.findViewById(R.id.cancel);
            ok.setOnClickListener(this);
            cacell.setOnClickListener(this);
            dialog.setCancelable(false);
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_uploadPhotoGroup || v.getId()==R.id.iv_uploadPhotoGroup){
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }else if(v.getId()==R.id.cancel){

            dialog.dismiss();

        }else if(v.getId()==R.id.ok){
            if(name.getText().toString().isEmpty()){
                name.setError("Enter Group Name");
            }else if(urlPhoto.equals("NF")){
                Toast.makeText(info, "Upload photo Group", Toast.LENGTH_SHORT).show();
            }else if(infogroup.getText().toString().isEmpty()){

                infogroup.setError("Enter Group Information");
            }else if(infogroup.getText().toString().trim().length()<29){
                infogroup.setError("Group Information must be at least 30 character");
            }
            else{
                pb_group.setVisibility(View.VISIBLE);
               dialog.dismiss();
                String groupName=name.getText().toString().trim();
                String information=infogroup.getText().toString().trim();
                createGroup(urlPhoto,groupName,information);

            }


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && RESULT_OK==resultCode && data!=null){
            String[] filePathColumn = { MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            urlPhoto= cursor.getString(columnIndex);
            cursor.close();
            iv_uiploadPhoto.setImageResource(R.drawable.correct);

        }
    }
    private void createGroup(String urlPhoto,String groupName,String group_info) {
        retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiConfig=retrofit.create(ApiConfig.class);
        File photo=new File(urlPhoto);

        RequestBody name=RequestBody.create(MediaType.parse("multipart/form-data"), groupName);
        RequestBody info=RequestBody.create(MediaType.parse("multipart/form-data"), group_info);
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        MultipartBody.Part multipartBody1 = MultipartBody.Part.createFormData("file", photo.getName(), image);

        apiConfig.CreateGroup(name,info,multipartBody1).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String group_id=response.body();
                SetMember(id,group_id,"admin","Yes");

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Groups.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void SetMember(String code,String group_id,String type,String state) {
        apiConfig.SetMember(code,group_id,type,state).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
              pb_group.setVisibility(View.INVISIBLE);
              Intent intent=new Intent(Groups.this,Groups.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);

                Toast.makeText(info, "Create Group Scucess..", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(info, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    void changeStateStudentGroup(String changeState){
        apiConfig.changeState(changeState, "YES").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Toast.makeText(Groups.this, "ok   "+id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Groups.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    void fingreprint(){
        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if(errString.toString().contains("No")){}else{
                   onBackPressed();
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Confirm fingerprint")
                .setSubtitle("Touch the fingerprint sensor")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);

    }
}
