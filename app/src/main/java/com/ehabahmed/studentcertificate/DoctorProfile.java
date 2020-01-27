package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.android.material.tabs.TabLayout;

public class DoctorProfile extends AppCompatActivity implements View.OnClickListener{
    ImageView iv_profile;
    Info doctorInfo;
    FragmentManager fragmentManager;
    TextView name;
    TabLayout tabLayout;
    Intent intent;


    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        iv_profile=findViewById(R.id.iv_profile);
        doctorInfo=(Info)getApplicationContext();
        tabLayout=findViewById(R.id.tabs_doctor);
        pager=findViewById(R.id.container);
        name=findViewById(R.id.name);
        name.setText(doctorInfo.getDoctor_name());
        fragmentManager=getSupportFragmentManager();
        setTitle(getResources().getString(R.string.profile_name_page));

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            Glide.with(this).load("https://ehab01998.com/images_profile/" + doctorInfo.getDoctor_photo())
                    .circleCrop().placeholder(R.drawable.profile2).into(iv_profile);
        }else{
            Glide.with(this).load("http://ehab01998.com/images_profile/" + doctorInfo.getDoctor_photo())
                    .circleCrop().placeholder(R.drawable.profile2).into(iv_profile);

        }



        iv_profile.setOnClickListener(this);

        pager.setAdapter(new MyPager(getSupportFragmentManager(),1));
        tabLayout.setupWithViewPager(pager);


    }

    @Override
    public void onClick(View view) {
     intent=new Intent(this,Show_image.class);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            intent.putExtra("news_ivname","https://ehab01998.com/images_profile/" + doctorInfo.getDoctor_photo());
        }else{
            intent.putExtra("news_ivname","http://ehab01998.com/images_profile/" + doctorInfo.getDoctor_photo());

        }

        startActivity(intent);
    }


    public class MyPager extends FragmentStatePagerAdapter {


        public MyPager(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new DoctorInfo();
                case 1:
                    return new DoctorFav();
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
                    return getResources().getString(R.string.doctor_info);
                case 1:
                    return getResources().getString(R.string.doctor_fav);
            }
            return super.getPageTitle(position);
        }
    }



}
