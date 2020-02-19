package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

public class CreateStudentGroup extends AppCompatActivity implements StudentgroupAdapter.Infogroup {
String GroupId,GroupName,GroupPhoto,GroupInfo,GroupType,NumMember,url;
ImageView photoGroup;
Toolbar toolbar;
CollapsingToolbarLayout toolbarLayout;
ActionBar actionBar;
AppBarLayout appBarLayout;
RecyclerView rv_group;
LinearLayoutManager layoutManager;
Retrofit retrofit;
ApiConfig apiConfig;
ArrayList<objectPostGroup> listitems;
StudentgroupAdapter adapter;
    Intent intent;
Info info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student_group);
        rv_group=findViewById(R.id.rv_group);
        info= (Info) getApplicationContext();
        layoutManager=new LinearLayoutManager(this);
        rv_group.setLayoutManager(layoutManager);
        toolbar=findViewById(R.id.toolbar);
        appBarLayout=findViewById(R.id.appbarlayout);
        toolbarLayout=findViewById(R.id.toolbarlayout);
        photoGroup=findViewById(R.id.photoGroup);
        listitems=new ArrayList<>();
        GroupId=getIntent().getExtras().getString("GroupId");
        GroupName=getIntent().getExtras().getString("GroupName");
        GroupPhoto=getIntent().getExtras().getString("GroupPhoto");
        GroupInfo=getIntent().getExtras().getString("GroupInfo");
        GroupType=getIntent().getExtras().getString("type");
        NumMember=getIntent().getExtras().getString("NumMember");
        actionBar=getSupportActionBar();
        actionBar.hide();
        onCollapseChange();
        makeTranslucent();
        showPhotoGroup();
        setTitle(GroupName);
        listitems.add(new objectPostGroup("info","info","info","info","info","info"));
        listitems.add(new objectPostGroup("post","post","post","post","post","post"));

        getposts();







    }

    private void getposts() {
        retrofit=new Retrofit.Builder()
                .baseUrl("http://ehab01998.com").addConverterFactory(GsonConverterFactory.create())
                .build();
        apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.getposts(GroupId).enqueue(new Callback<objectPostGroup>() {
            @Override
            public void onResponse(Call<objectPostGroup> call, Response<objectPostGroup> response) {
                Toast.makeText(CreateStudentGroup.this, "ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<objectPostGroup> call, Throwable t) {
                Toast.makeText(CreateStudentGroup.this, "false", Toast.LENGTH_SHORT).show();

            }
        });

      adapter=new StudentgroupAdapter(CreateStudentGroup.this,listitems);
      rv_group.setAdapter(adapter);

    }

    void showPhotoGroup(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://"+GroupPhoto;

        }else{
            url="http://"+GroupPhoto;

        }
        Glide.with(this).load(url).into(photoGroup);

    }

  public  void onCollapseChange(){
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if(Math.abs(i)==appBarLayout.getTotalScrollRange()){
                    actionBar.show();
                }
                else if(i==0){
                    actionBar.hide();
                }

            }
        });



    }
    public void makeTranslucent(){
        if(Build.VERSION.SDK_INT>=19 && Build.VERSION.SDK_INT<21){
            setWindowsFlag(this,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,true);
        }
        if(Build.VERSION.SDK_INT>=19){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if(Build.VERSION.SDK_INT>=21){
            setWindowsFlag(this,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }
public static void setWindowsFlag(Activity activity,final int bits, boolean on){
    Window win=activity.getWindow();
    WindowManager.LayoutParams winparam=win.getAttributes();
    if(on){
        winparam.flags |=bits;
    }else{
        winparam.flags &= ~bits;
    }
        win.setAttributes(winparam);
}

    @Override
    public void setInfoGroup(TextView groupName, TextView groupNumber, Button invite, ImageView iv_admin, ImageView iv_member1, ImageView iv_member2, ImageView iv_member3, ImageView iv_member4, ImageView iv_member11, ImageView iv_member22, ImageView iv_member33, ImageView iv_member44, ImageView iv_admin00, ConstraintLayout Container1, ConstraintLayout Container2) {
        String name=GroupName.substring(0,1).toUpperCase()+GroupName.substring(1);
        groupName.setText(name+" >");
        groupNumber.setText("PRIVATE GROUP . "+NumMember+" MEMMBER");
      if(GroupType.equals("admin"))
          invite.setVisibility(View.VISIBLE);
      if(NumMember.equals("1")){
          if(info.getType().equals("student")) {
              iv_admin.setVisibility(View.VISIBLE);
             iv_admin00.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getPhoto()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin00);

          }else if(info.getType().equals("doctor")){
              iv_admin.setVisibility(View.VISIBLE);
              iv_admin00.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getDoctor_photo()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin00);

          }
      }else if(NumMember.equals("2")){
          if(info.getType().equals("student")) {
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
              iv_member11.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getPhoto()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member11);

          }else if(info.getType().equals("doctor")){
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
              iv_member11.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getDoctor_photo()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member11);

          }


      }else if(NumMember.equals("3")){
          if(info.getType().equals("student")) {
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
             iv_member2.setVisibility(View.VISIBLE);
             iv_member22.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getPhoto()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member2);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member22);

          }else if(info.getType().equals("doctor")){
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
              iv_member2.setVisibility(View.VISIBLE);
              iv_member22.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getDoctor_photo()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member2);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member22);

          }

      }else if(NumMember.equals("4")){

          if(info.getType().equals("student")) {
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
              iv_member2.setVisibility(View.VISIBLE);
             iv_member3.setVisibility(View.VISIBLE);
             iv_member33.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getPhoto()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member2);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member3);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member33);

          }else if(info.getType().equals("doctor")){
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
              iv_member2.setVisibility(View.VISIBLE);
              iv_member3.setVisibility(View.VISIBLE);
              iv_member33.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getDoctor_photo()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member2);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member3);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member33);
          }


      }else {
          if(info.getType().equals("student")) {
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
              iv_member2.setVisibility(View.VISIBLE);
              iv_member3.setVisibility(View.VISIBLE);
              iv_member4.setVisibility(View.VISIBLE);
              iv_member44.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getPhoto()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member2);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member3);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member4);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member44);

          }else if(info.getType().equals("doctor")){
              iv_admin.setVisibility(View.VISIBLE);
              iv_member1.setVisibility(View.VISIBLE);
              iv_member2.setVisibility(View.VISIBLE);
              iv_member3.setVisibility(View.VISIBLE);
              iv_member4.setVisibility(View.VISIBLE);
              iv_member44.setVisibility(View.VISIBLE);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/" +info.getDoctor_photo()).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_admin);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member1);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member2);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member3);
              Glide.with(CreateStudentGroup.this).load("http://ehab01998.com/images_profile/"+"").placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member4);

              Glide.with(CreateStudentGroup.this).load(R.drawable.ic_more_horiz_black_24dp).placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(iv_member44);
          }

      }

Container1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      intent=new Intent(CreateStudentGroup.this,ShowInfoGroup.class);
        intent.putExtra("GroupName",GroupName);
        intent.putExtra("GroupInfo",GroupInfo);
        startActivity(intent);
    }
});

Container2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        intent=new Intent(CreateStudentGroup.this,ShowMemberGroup.class);
        intent.putExtra("GroupId",GroupId);
        startActivity(intent);

    }
});
    }
}
