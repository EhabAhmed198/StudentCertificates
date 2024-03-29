package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;


import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    Bundle bundle;
    Intent intent;
    Handler handler;
    ViewPagerAdapter adapter;
    View view;
    boolean StateDeletePage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestphonepermision();
        tabLayout = findViewById(R.id.tlHome);
        final ViewPager viewPager = findViewById(R.id.container);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        handler = new Handler();


        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 1) {
                    if (StateDeletePage == false) {

                        viewPager.removeViewAt(0);
                        StateDeletePage = true;
                    }


                } else if (position == 0) {
                    if (StateDeletePage == true) {
                        viewPager.setAdapter(adapter);
                        StateDeletePage = false;
                    }

                } else if (position == 2) {
                    if (StateDeletePage == false) {

                        viewPager.removeViewAt(0);
                        StateDeletePage = true;
                    }

                } else if (position == 3) {
                    if (StateDeletePage == false) {

                        viewPager.removeViewAt(0);
                        StateDeletePage = true;
                    }
                }
            }
        });
    }



    private void requestphonepermision() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            }, 3);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menustart, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shair_app:
                shair();

                break;
            case R.id.sendSuggestions:
                sendSuggestions();
                break;

            case R.id.rate_app:
                rate_app();
                break;
            case R.id.exit_app:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void rate_app() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse("market://details?id=" + getPackageName()));
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(intent);
        }
    }

    private void sendSuggestions() {
        intent = new Intent(Intent.ACTION_SEND);
        String into = "السلام علكم ورحمتة الله وبركاته" + "\n" + "اقتراحي عن التطبيق هو : ";
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ehabahmed01998@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Student Certificate");
        intent.putExtra(Intent.EXTRA_TEXT, into);
        try {
            startActivity(Intent.createChooser(intent, "Send Emial"));
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.nosendprogrem), Toast.LENGTH_SHORT).show();
        }


    }

    private void shair() {

        String app_address = "Student Certificate";
        String app_link;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            app_link = "https://play.google.com/store/apps/details?id=com.ehabahmed.studentcertificate";

        } else {
            app_link = "http://play.google.com/store/apps/details?id=com.ehabahmed.studentcertificate";

        }

        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, app_address + "\n" + app_link);
        startActivity(intent);
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {


        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:
                    return new News();
                case 1:
                    return new Sgin_or_Login();
                case 2:
                    return new Vision();
                case 3:
                    return new Communication();

            }
            return null;

        }

        @Override
        public int getCount() {
            return 4; //three fragments
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.news);
                case 1:
                    return getResources().getString(R.string.sign_or_login);
                case 2:
                    return getResources().getString(R.string.vision);
                case 3:
                    return getResources().getString(R.string.communication);
                default:
                    return null;

            }


        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            switch (position) {
                case 0:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.news));
                        }
                    }, 1);

                    break;
                case 1:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.sign_or_login));
                        }
                    }, 1);

                    break;
                case 2:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.vision));
                        }
                    }, 1);

                    break;
                case 3:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(getResources().getString(R.string.communication));
                        }
                    }, 1);


                    break;
            }
        }
    }

}

