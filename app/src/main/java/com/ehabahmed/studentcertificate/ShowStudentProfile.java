package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowStudentProfile extends AppCompatActivity implements View.OnClickListener {
    String ID, Name, Photo, department, level;
    ImageView Im_photo;
    TextView Iv_name, Iv_code, Iv_department, Iv_level, Iv_mobile, Iv_email;
    Button MESSAGE;
    Info info;

    //TODO: start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_profile);
        ID = getIntent().getExtras().getString("ID");
        Name = getIntent().getExtras().getString("Name");
        Photo = getIntent().getExtras().getString("Photo");
        department = getIntent().getExtras().getString("department");
        level = getIntent().getExtras().getString("level");
        info = (Info) getApplicationContext();
        Im_photo = findViewById(R.id.photo);
        Iv_name = findViewById(R.id.Iv_name);
        Iv_code = findViewById(R.id.Iv_code);
        Iv_department = findViewById(R.id.Iv_department);
        Iv_level = findViewById(R.id.Iv_level);
        Iv_mobile = findViewById(R.id.Iv_mobile);
        Iv_email = findViewById(R.id.Iv_email);
        MESSAGE = findViewById(R.id.MESSAGE);
        MESSAGE.setOnClickListener(this);
        getConvertData();
        setData();
        getCpi();

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
        Iv_department.setText(department);
        Iv_level.setText(level);
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

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()

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
        String SenderID = info.getId();
        String SenderName = info.getName();
        String Senderphoto = "https://ehab01998.com/images_profile/" + info.getPhoto();

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
