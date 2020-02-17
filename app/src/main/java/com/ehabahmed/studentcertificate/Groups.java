package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Groups extends AppCompatActivity implements View.OnClickListener {
RecyclerView groupNames,groupPosts;
LinearLayoutManager vertical,horzental;
Retrofit retrofit;
ApiConfig apiConfig;
GroupNameAdapter adapter;
ArrayList<DataGroup> listitems;
Info info;
String id;
String urlPhoto="NF";
    ImageView iv_uiploadPhoto;
    Dialog dialog;
    GsonBuilder builder;
    Gson gson;
    EditText name;
    ProgressBar pb_group;
    Button ok,cacell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        pb_group=findViewById(R.id.pb_group);
        info=(Info)getApplicationContext();
        if(info.getType().equals("student")){
            id=info.getId();
        }
        else    if(info.getType().equals("doctor")){
            id=info.getDoctor_id();
        }
        builder=new GsonBuilder();
        builder.setLenient();
        gson=builder.create();
        groupNames=findViewById(R.id.rv_groupsName);
        groupPosts=findViewById(R.id.rv_groupsNotification);
        horzental=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        vertical=new LinearLayoutManager(this);
        groupNames.setLayoutManager(horzental);
        groupPosts.setLayoutManager(vertical);
        listitems=new ArrayList<>();
        getGroups();




    }

    private void getGroups() {
        retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com").addConverterFactory(GsonConverterFactory.create())
                .build();
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
            }else{
                pb_group.setVisibility(View.VISIBLE);
               dialog.dismiss();
                String groupName=name.getText().toString().trim();
                createGroup(urlPhoto,groupName);

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
    private void createGroup(String urlPhoto,String groupName) {
        retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiConfig=retrofit.create(ApiConfig.class);
        File photo=new File(urlPhoto);

        RequestBody name=RequestBody.create(MediaType.parse("multipart/form-data"), groupName);
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        MultipartBody.Part multipartBody1 = MultipartBody.Part.createFormData("file", photo.getName(), image);

        apiConfig.CreateGroup(name,multipartBody1).enqueue(new Callback<String>() {
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
        retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiConfig=retrofit.create(ApiConfig.class);
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
}
