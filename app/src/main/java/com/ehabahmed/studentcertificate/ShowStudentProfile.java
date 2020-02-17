package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowStudentProfile extends AppCompatActivity implements View.OnClickListener {
    String ID, Name, Photo, department, level;
    ImageView Im_photo;
    TextView Iv_name, Iv_code, Iv_department, Iv_level, Iv_mobile, Iv_email, did_not_a;
    Button MESSAGE;
    Info info;
String type;
    GsonBuilder gsonBuilder;
    Gson gson;
    Retrofit retrofit;

    //TODO: start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_profile);
        type=getIntent().getExtras().getString("type");
        ID = getIntent().getExtras().getString("ID");
        Name = getIntent().getExtras().getString("Name");
        info = (Info) getApplicationContext();
        Photo = getIntent().getExtras().getString("Photo");
      gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();
        if(type.equals("student") ) {
            department = getIntent().getExtras().getString("department");
            level = getIntent().getExtras().getString("level");
            getConvertData();
        }

        Im_photo = findViewById(R.id.photo);
        Iv_name = findViewById(R.id.Iv_name);
        Iv_code = findViewById(R.id.Iv_code);
        Iv_department = findViewById(R.id.Iv_department);
        Iv_level = findViewById(R.id.Iv_level);
        Iv_mobile = findViewById(R.id.Iv_mobile);
        Iv_email = findViewById(R.id.Iv_email);
        MESSAGE = findViewById(R.id.MESSAGE);
        did_not_a = findViewById(R.id.did_not_a);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        MESSAGE.setVisibility(Button.INVISIBLE);

        MessageA(reference);
        MESSAGE.setOnClickListener(this);

        setData();
        if(type.equals("student") ||info.getType().equals("certificate")) {
            getCpi();
        }else if(type.equals("doctor")){
            getDoctorCpi();
        }
        setAcceptSwitch(reference);

    }

    private void getDoctorCpi() {

        retrofit = new Retrofit.Builder()

                .baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiConfig apiConfig = retrofit.create(ApiConfig.class);
        apiConfig.getDoctordata(ID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.body().equals("NF")) {
                    boolean phone = response.body().startsWith("NF");
                    boolean email = response.body().endsWith("NF");
                    if (phone == false && email == false) {
                        Iv_mobile.setText(response.body().substring(0, 11));
                        Iv_email.setText(response.body().substring(11));
                        Iv_mobile.setVisibility(View.VISIBLE);
                        Iv_email.setVisibility(View.VISIBLE);

                    } else if (phone == false && email == true) {
                        Iv_mobile.setText(response.body().substring(0, 11));
                        Iv_email.setText(R.string.nofound);
                        Iv_mobile.setVisibility(View.VISIBLE);


                    } else if (phone == true && email == false) {
                        Iv_email.setText(response.body().substring(2));
                        Iv_mobile.setText(R.string.nofound);
                        Iv_email.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void MessageA(DatabaseReference reference) {
        final List<String> list = new ArrayList<>();
        reference = reference.child(ID + "").child("acceptable");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.add(dataSnapshot.getKey());
                if(info.getType().equals("student") || info.getType().equals("certificate")) {
                    if (list.contains("" + info.getId())) {
                        MESSAGE.setVisibility(Button.VISIBLE);
                        did_not_a.setVisibility(TextView.INVISIBLE);
                    } else {
                        did_not_a.setVisibility(TextView.VISIBLE);
                        MESSAGE.setVisibility(Button.INVISIBLE);

                    }
                }else if(info.getType().equals("doctor")){
                    if (list.contains("" + info.getDoctor_id())) {
                        MESSAGE.setVisibility(Button.VISIBLE);
                        did_not_a.setVisibility(TextView.INVISIBLE);
                    } else {
                        did_not_a.setVisibility(TextView.VISIBLE);
                        MESSAGE.setVisibility(Button.INVISIBLE);

                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getKey());
                if(info.getType().equals("student") || info.getType().equals("certificate")) {
                    if (list.contains("" + info.getId())) {
                        MESSAGE.setVisibility(Button.VISIBLE);
                        did_not_a.setVisibility(TextView.INVISIBLE);
                    } else {
                        MESSAGE.setVisibility(Button.INVISIBLE);
                        did_not_a.setVisibility(TextView.VISIBLE);
                    }
                }else if(info.getType().equals("doctor")){
                    if (list.contains("" + info.getDoctor_id())) {
                        MESSAGE.setVisibility(Button.VISIBLE);
                        did_not_a.setVisibility(TextView.INVISIBLE);
                    } else {
                        MESSAGE.setVisibility(Button.INVISIBLE);
                        did_not_a.setVisibility(TextView.VISIBLE);
                    }
                }


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);
    }

    private void setAcceptSwitch(DatabaseReference reference) {
        String id = null;
        if(info.getType().equals("student") || info.getType().equals("certificate"))
            id=info.getId();
            else if(info.getType().equals("doctor"))
                id=info.getDoctor_id();
        final Switch se = findViewById(R.id.accept_switch);
        reference = reference.child(id + "").child("acceptable");
        final List<String> list = new ArrayList<>();
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.add(dataSnapshot.getKey());
                if (list.contains("" + ID)) {
                    se.setChecked(true);
                }
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

        final DatabaseReference finalReference = reference;
        se.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    finalReference.child("" + ID).setValue("");
                } else {
                    finalReference.child("" + ID).getRef().removeValue();
                }
            }
        });
        reference.addChildEventListener(childEventListener);
    }

    private void setData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load("https://ehab01998.com/images_profile/" + Photo)
                    .into(Im_photo);
        } else {
            Glide.with(this).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load("http://ehab01998.com/images_profile/" + Photo)
                    .into(Im_photo);
        }
        Iv_name.setText(Name);
        Iv_code.setText(ID);
        if(type.equals("student")) {
            Iv_department.setText(department);
            Iv_level.setText(level);
        }
    }

    public void getConvertData() {
        String departments[] = getResources().getStringArray(R.array.departments);

        if (department.equals("1")) {
            department = departments[0];
        } else if (department.equals("2")) {
            department = departments[1];
        } else if (department.equals("3")) {
            department = departments[2];
        }
        if (level.equals("1")) {

            level = "One";
        }
        if (level.equals("2")) {
            level = "Two";
        }
        if (level.equals("3")) {
            level = "Three";
        }
        if (level.equals("4")) {
            level = "Four";
        }
        if (level.equals("5")) {
            level = "Graduates";
        }
    }

    private void getCpi() {
        // Get String with retrofit



     retrofit = new Retrofit.Builder()

                .baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiConfig apiConfig = retrofit.create(ApiConfig.class);
        apiConfig.getdata(ID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.body().equals("NF")) {
                    boolean phone = response.body().startsWith("NF");
                    boolean email = response.body().endsWith("NF");
                    if (phone == false && email == false) {
                        Iv_mobile.setText(response.body().substring(0, 11));
                        Iv_email.setText(response.body().substring(11));
                        Iv_mobile.setVisibility(View.VISIBLE);
                        Iv_email.setVisibility(View.VISIBLE);

                    } else if (phone == false && email == true) {
                        Iv_mobile.setText(response.body().substring(0, 11));
                        Iv_email.setText(R.string.nofound);
                        Iv_mobile.setVisibility(View.VISIBLE);


                    } else if (phone == true && email == false) {
                        Iv_email.setText(response.body().substring(2));
                        Iv_mobile.setText(R.string.nofound);
                        Iv_email.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        //Sender
        String SenderID = null,SenderName=null,Senderphoto=null;
        if(type.equals("student") || info.getType().equals("certificate")) {
            SenderID = info.getId();
           SenderName= info.getName();
            Senderphoto= "https://ehab01998.com/images_profile/" + info.getPhoto();


        }else if(type.equals("doctor")){
            SenderID = info.getId();
            SenderName=info.getDoctor_name();
            Senderphoto= "https://ehab01998.com/images_profile/" + info.getDoctor_photo();

        }


        //Receiver
        String ReceiverID = ID;
        String ReceiverName = Name;
        String Receiverphoto = "https://ehab01998.com/images_profile/" + Photo;

        Intent send = new Intent(this, Send.class);
        send.putExtra("sender_id", SenderID)
                .putExtra("receiver_name", ReceiverName)
                .putExtra("receiver_image", Receiverphoto)
                .putExtra("receiver_id", ReceiverID)
                .putExtra("sender_name", SenderName)
                .putExtra("sender_image", Senderphoto);


        startActivity(send);
    }
}
