package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateStudentGroup extends AppCompatActivity {
String GroupId,GroupName,GroupPhoto,url;
ImageView photoGroup;
Toolbar toolbar;
CollapsingToolbarLayout toolbarLayout;
ActionBar actionBar;
AppBarLayout appBarLayout;
RecyclerView rv_group;
LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student_group);
        rv_group=findViewById(R.id.rv_group);
        layoutManager=new LinearLayoutManager(this);
        rv_group.setLayoutManager(layoutManager);
        toolbar=findViewById(R.id.toolbar);
        appBarLayout=findViewById(R.id.appbarlayout);
        toolbarLayout=findViewById(R.id.toolbarlayout);
        photoGroup=findViewById(R.id.photoGroup);
        GroupId=getIntent().getExtras().getString("GroupId");
        GroupName=getIntent().getExtras().getString("GroupName");
        GroupPhoto=getIntent().getExtras().getString("GroupPhoto");
        actionBar=getSupportActionBar();
        actionBar.hide();
        onCollapseChange();
        makeTranslucent();
        showPhotoGroup();
        setTitle(GroupName);







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

}
