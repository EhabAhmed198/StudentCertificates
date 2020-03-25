package com.ehabahmed.studentcertificate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import java.util.List;

public class VoiceController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_controller);
          displaySpeechRecognizer();
    }
    private void displaySpeechRecognizer(){
        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==99 &&resultCode==RESULT_OK){
            if (data != null) {
                List<String> list=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String s=list.get(0);
                if(s.equalsIgnoreCase("open message")){
                    startActivity(new Intent(this,Inbox.class));
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
