package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentStudentPost extends AppCompatActivity {

    EditText commentText;
    Button send;
    Info info;
    String code,username,comment,photo,postid;
    ArrayList<comment> listitems;
    RecyclerView recyclerView;
    String type;
    CommentAdapter adapter;
    ProgressBar progressBar;
    TextView nocomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_student_post);
        postid=getIntent().getExtras().getString("postId");
        progressBar=findViewById(R.id.pb_comment);
        nocomment=findViewById(R.id.tv_comment);
        recyclerView=findViewById(R.id.showcomments);
        commentText=findViewById(R.id.textcomment);
        send=findViewById(R.id.send);
        info=(Info)getApplicationContext();
        progressBar.setVisibility(View.INVISIBLE);
        nocomment.setVisibility(View.INVISIBLE);
        send.setActivated(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(info.getType().equals("student") || info.getType().equals("certificate")) {
            username=info.getName();
            code = info.getId();
            photo=info.getPhoto();
        }else if(info.getType().equals("doctor")){
            username=info.getDoctor_name();
            code=info.getDoctor_id();
            photo=info.getDoctor_photo();
        }
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if(s.toString().trim().length()>0){
                  send.setActivated(true);
              }
            }
        });
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference =firebaseDatabase.getReference().child("comment").child(""+postid);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long t = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss a");
                String time = simpleDateFormat.format(t);
                databaseReference.push().setValue(new comment(username,""+commentText.getText().toString(),
                        ""+code,""+photo,""+time));
                commentText.setText("");
            }
        });
        listitems=new ArrayList<>();
        adapter=new CommentAdapter(this,listitems);
        ChildEventListener childEventListener =new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listitems.add(dataSnapshot.getValue(comment.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

       databaseReference.addChildEventListener(childEventListener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
