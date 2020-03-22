package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class Fingreprint extends AppCompatActivity {
Switch applock,grouplock,maillock,resultlock;
SharedPreferences preferences;
SharedPreferences.Editor editor;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private  BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingreprint);
        applock=findViewById(R.id.appLock);
        grouplock=findViewById(R.id.grouplock);
        maillock=findViewById(R.id.csmaillock);
        resultlock=findViewById(R.id.resultlock);
        preferences=getSharedPreferences("fingreprint", Context.MODE_PRIVATE);
        editor=preferences.edit();
        applock.setChecked(preferences.getBoolean("applock",false));
        grouplock.setChecked(preferences.getBoolean("grouplock",false));
        maillock.setChecked(preferences.getBoolean("maillock",false));
        resultlock.setChecked(preferences.getBoolean("resultlock",false));
        applock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                fingreprint("applock",isChecked);
            }
        });
        grouplock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                fingreprint("grouplock",isChecked);
            }
        });
        maillock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                fingreprint("maillock",isChecked);
            }
        });
        resultlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

             fingreprint("resultlock",isChecked);
            }
        });

    }
    void fingreprint(final String key, final boolean isChecked){

        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(Fingreprint.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
               if(key.equals("applock")){
                   if(applock.isChecked()==true)
                       applock.setChecked(false);
                       else
                           applock.setChecked(true);
               }else if(key.equals("grouplock")){
                   if(grouplock.isChecked()==true)
                       grouplock.setChecked(false);

                   else
                       grouplock.setChecked(true);
               }else if(key.equals("maillock")){
                   if(maillock.isChecked()==true)
                       maillock.setChecked(false);
                   else
                       maillock.setChecked(true);
               }
               else if(key.equals("resultlock")){
                   if(resultlock.isChecked()==true)
                       resultlock.setChecked(false);
                   else
                       resultlock.setChecked(true);
               }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                editor.putBoolean(key,isChecked);
                editor.apply();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(Fingreprint.this, "Faild", Toast.LENGTH_SHORT).show();
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
