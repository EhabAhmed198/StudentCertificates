package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class Inbox extends AppCompatActivity implements View.OnClickListener {

TabLayout Tabs;
ViewPager pager;
Handler handler;
  PagerAdapter adapter;
    com.google.android.material.floatingactionbutton.FloatingActionButton FloatingActionButton;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private  BiometricPrompt.PromptInfo promptInfo;
    SharedPreferences maillock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        maillock=getSharedPreferences("fingreprint", Context.MODE_PRIVATE);;
        if( maillock.getBoolean("maillock",false)){
            fingreprint();

        }

        FloatingActionButton=findViewById(R.id.fab);
        FloatingActionButton.setOnClickListener(this);
        Tabs = findViewById(R.id.htabs);
        pager = findViewById(R.id.continar);
        adapter=new PagerAdapter(getSupportFragmentManager(),1);
        handler=new Handler();
        pager.setAdapter(adapter);
        Tabs.setupWithViewPager(pager);
        Tabs.getTabAt(0).setText(getString(R.string.Reciver));
        Tabs.getTabAt(1).setText(getString(R.string.Sender));

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Inbox.this,Members.class);
        intent.putExtra("invite","NoInvite");

        startActivity(intent);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {


            public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
                super(fm, behavior);
            }

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:

                        return    new ReciverInbox();
                    case 1:

                        return  new SenderInbox();


                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.setPrimaryItem(container, position, object);
                switch (position) {
                    case 0:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setTitle(getResources().getString(R.string.Reciver));
                            }
                        }, 1);

                        break;

                    case 1:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setTitle(getResources().getString(R.string.Sender));
                            }
                        }, 1);

                        break;


                }

            }}
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
