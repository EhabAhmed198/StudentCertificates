package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.android.material.tabs.TabLayout;

public class Student_peofile extends AppCompatActivity implements  View.OnClickListener {
ImageView iv_profile;
Info studuentInfo;
FragmentManager fragmentManager;
TextView name;
TabLayout tabLayout;
Bitmap bitmap;
ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_peofile);
        iv_profile=findViewById(R.id.iv_profile);
        studuentInfo=(Info)getApplicationContext();
        pager=findViewById(R.id.container);
        tabLayout=findViewById(R.id.tabs_student);
        fragmentManager=getSupportFragmentManager();
setTitle(getResources().getString(R.string.profile_name_page));


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.profile2).circleCrop().load("https://ehab01998.com/images_profile/"+studuentInfo.getPhoto())
                    .into(iv_profile);
        }else{
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.profile2).circleCrop().load("http://ehab01998.com/images_profile/"+studuentInfo.getPhoto())
                    .into(iv_profile);
        }






      
      
      
        iv_profile.setOnClickListener(this);
      name=findViewById(R.id.name);
      name.setText(studuentInfo.getName());
    pager.setAdapter(new MyPager(getSupportFragmentManager(),1));
    tabLayout.setupWithViewPager(pager);



    }


    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Show_image.class);
        intent.putExtra("news_ivname","http://ehab01998.com/images_profile/"+studuentInfo.getPhoto());
        startActivity(intent);
    }



    public class MyPager extends FragmentPagerAdapter{


        public MyPager(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new profile_infoStudent();
                case 1:
                    return new profile_favStudent();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getResources().getString(R.string.student_info);
                case 1:
                    return getResources().getString(R.string.student_fav);
            }
            return super.getPageTitle(position);
        }
    }
}
