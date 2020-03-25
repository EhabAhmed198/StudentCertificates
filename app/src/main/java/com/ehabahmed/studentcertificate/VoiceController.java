package com.ehabahmed.studentcertificate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import java.util.List;

public class VoiceController extends AppCompatActivity {
Intent intent;
Info info;
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
                info= (Info) getApplicationContext();
                switch (s){
                    case "message":
                        intent=new Intent(this,Inbox.class);
                        startActivity(intent);
                        break;
                    case "news":
                        intent=new Intent(this,Student.class);
                       intent.putExtra("voic","0");
                        intent.putExtra("id",info.getId());
                        intent.putExtra("name",info.getName());
                        intent.putExtra("pass",info.getPass());
                        intent.putExtra("photo",info.getPhoto());
                        intent.putExtra("department",info.getDepartment());
                        intent.putExtra("level",info.getLevel());
                        startActivity(intent);
                        break;
                    case "contents":
                    case "content":
                        intent=new Intent(this,Student.class);
                        intent.putExtra("voic","2");
                        intent.putExtra("id",info.getId());
                        intent.putExtra("name",info.getName());
                        intent.putExtra("pass",info.getPass());
                        intent.putExtra("photo",info.getPhoto());
                        intent.putExtra("department",info.getDepartment());
                        intent.putExtra("level",info.getLevel());
                        startActivity(intent);
                        break;
                    case "Lectures":
                    case "Lecture":
                    case "picture":
                    case "Lincolnshire":
                    case "lincolnshire":
                    case "literature":
                    case "the kitchen":
                        intent=new Intent(this,Student.class);
                        intent.putExtra("voic","1");
                        intent.putExtra("id",info.getId());
                        intent.putExtra("name",info.getName());
                        intent.putExtra("pass",info.getPass());
                        intent.putExtra("photo",info.getPhoto());
                        intent.putExtra("department",info.getDepartment());
                        intent.putExtra("level",info.getLevel());
                        startActivity(intent);
                        break;
                    case "menu":
                        intent=new Intent(this,Student.class);
                        intent.putExtra("voic","3");
                        intent.putExtra("id",info.getId());
                        intent.putExtra("name",info.getName());
                        intent.putExtra("pass",info.getPass());
                        intent.putExtra("photo",info.getPhoto());
                        intent.putExtra("department",info.getDepartment());
                        intent.putExtra("level",info.getLevel());
                        startActivity(intent);
                        break;

                    case "exit":
                    case "is it":
                        onDestroy();
                        break;
                    case "tables":
                    case "table":
                    case "tip":
                        intent=new Intent(this,Table_student.class);
                        startActivity(intent);
                        break;
                    case "profile":
                    case "my profile":
                        intent=new Intent(this,Student_peofile.class);
                        startActivity(intent);
                        break;
                    case "results":
                    case "result":
                        intent=new Intent(this,Result_Student.class);
                        startActivity(intent);
                        break;
                    case "courses":
                    case "course":
                    case "cost":
                    case "custard":
                        intent=new Intent(this,Courses.class);
                       startActivity(intent);
                        break;

                    case "library":
                    case "book":
                        intent=new Intent(this,Library.class);
                        startActivity(intent);
                        break;
                    case "exams":
                    case "exam":
                        intent=new Intent(this,Exams.class);
                        this.startActivity(intent);
                          break;
                    case "group":
                    case "groups":
                        intent=new Intent(this,Groups.class);
                        this.startActivity(intent);
                        break;
                    case "mail":
                    case "csmail":
                    case "cs mail":
                        intent=new Intent(this,Inbox.class);
                        this.startActivity(intent);
                        break;
                    case "competition":
                        intent=new Intent(this,Compition.class);
                        this.startActivity(intent);
                        break;
                    case "software":
                    case "programs":
                    case "program":
                        intent=new Intent(this,Programs.class);
                        this.startActivity(intent);
                        break;
                    case "articles":
                    case "article":
                        intent=new Intent(this,Articles.class);
                        this.startActivity(intent);
                        break;
                    case "maps":
                    case "map":
                        intent=new Intent(this,TypeMap.class);
                        this.startActivity(intent);
                        break;
                    case "settings":
                    case "setting":
                    case "fitting":
                        intent=new Intent(this,Settings.class);
                        this.startActivity(intent);
                        break;
                    case "sign out":
                    case "find out":
                    case "signout":
                    case "out":
                        intent=new Intent(this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.startActivity(intent);
                        break;




                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
