package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CreatePostGroup extends AppCompatActivity implements View.OnClickListener {
    String GroupId,code,GroupName,GroupPhoto,GroupInfo,GroupType,NumMember;
    EditText text;
    ImageView imageView;
    int type=-1, progressbar = 10;
    String name;
    String url, filepath;
    Intent intent;
    String types;
    ConstraintLayout body;
    ProgressBar progressBar;
    TextView waitupload,uploadphoto,uploadfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_group);
         GroupId=getIntent().getExtras().getString("GroupId");
         code=getIntent().getExtras().getString("code");
        GroupName=getIntent().getExtras().getString("GroupName");
        GroupPhoto=getIntent().getExtras().getString("GroupPhoto");
        GroupInfo=getIntent().getExtras().getString("GroupInfo");
        GroupType=getIntent().getExtras().getString("type");
        NumMember=getIntent().getExtras().getString("NumMember");
        body = findViewById(R.id.body);
        progressBar = findViewById(R.id.pr);
        waitupload = findViewById(R.id.waitupload);

        imageView = findViewById(R.id.image);

        text = findViewById(R.id.et_text);
        uploadphoto=findViewById(R.id.uploads1);
        uploadfile=findViewById(R.id.uploads2);
        uploadphoto.setOnClickListener(this);
        uploadfile.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                onBackPressed();
                return true;
            case R.id.ok:
                item.setVisible(false);
                if (type == 0) {
                    if (!text.getText().toString().trim().isEmpty())
                        types="timage";
                    else
                        types="image";
                    uploadnew();
                } else if(type==2){
                    types="file";
                    uploadnew();

                }else if(type==-1){
                    if(text.getText().toString().trim().isEmpty())
                        text.setError(getResources().getString(R.string.entertext));
                    else
                        uploadtext();

                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            imageView.setVisibility(View.VISIBLE);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            filepath = picturePath;
            type = 0;

            Glide.with(CreatePostGroup.this).load(filepath).into(imageView);



        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            imageView.setVisibility(View.VISIBLE);
            type=2;
            filepath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            String type=filepath.substring(filepath.lastIndexOf("."));

            switch (type){



                case ".doc":
                    imageView.setBackgroundResource(R.drawable.doc);
                    break;

                case ".docx":
                    imageView.setBackgroundResource(R.drawable.docx);
                    break;
                case ".xlsx":
                    imageView.setBackgroundResource(R.drawable.xlsx);
                    break;
                case ".pptx":
                    imageView.setBackgroundResource(R.drawable.pptx); break;
                case ".ppt":
                    imageView.setBackgroundResource(R.drawable.ppt);
                    break;
                case ".pdf":
                    imageView.setBackgroundResource(R.drawable.pdf);
                    break;


            }


        }
    }


    public void uploadtext(){
        progressBar.setVisibility(View.VISIBLE);
        waitupload.setVisibility(View.VISIBLE);
        body.setVisibility(View.INVISIBLE);
        progressBar.setProgress(progressbar += 10);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/";
        }else{
            url="http://ehab01998.com/";

        }

        Retrofit retrofit=new Retrofit.Builder().baseUrl(url)
                .build();

        ApiConfig apiConfig=retrofit.create(ApiConfig.class);


        String as=text.getText().toString().trim();
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "none");
        RequestBody texts = RequestBody.create(MediaType.parse("multipart/form-data"), as);
        RequestBody groupId = RequestBody.create(MediaType.parse("multipart/form-data"), GroupId);
        RequestBody CODE = RequestBody.create(MediaType.parse("multipart/form-data"), code);
        RequestBody file_nothing = RequestBody.create(MediaType.parse("multipart/form-data"), "none");

        apiConfig.uploadtextPostGroup(texts,type,groupId,CODE,file_nothing).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), R.string.upload_post_scuccess, Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(), CreateStudentGroup.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("GroupId",GroupId);
                intent.putExtra("GroupName",GroupName);
                intent.putExtra("GroupPhoto",GroupPhoto);
                intent.putExtra("GroupInfo",GroupInfo);
                intent.putExtra("type",GroupType);
                intent.putExtra("NumMember",NumMember);
                progressBar.setProgress(100);
                startActivity(intent);
                finish();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreatePostGroup.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






    }






















    public void uploadnew(){
        progressBar.setVisibility(View.VISIBLE);
        waitupload.setVisibility(View.VISIBLE);
        body.setVisibility(View.INVISIBLE);
        progressBar.setProgress(progressbar += 10);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/";
        }else{
            url="http://ehab01998.com/";

        }

        Retrofit retrofit=new Retrofit.Builder().baseUrl(url)
                .build();

        ApiConfig apiConfig=retrofit.create(ApiConfig.class);

        File file = new File(filepath);
        String te=text.getText().toString().trim();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), types);
        RequestBody groupId = RequestBody.create(MediaType.parse("multipart/form-data"), GroupId);
        RequestBody CODE = RequestBody.create(MediaType.parse("multipart/form-data"), code);
        RequestBody texts = RequestBody.create(MediaType.parse("multipart/form-data"), te);
        try {
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            apiConfig.uploadPostSGroup(texts, type,groupId ,CODE,multipartBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    Toast.makeText(getApplicationContext(), R.string.upload_post_scuccess, Toast.LENGTH_LONG).show();



                    intent = new Intent(getApplicationContext(), CreateStudentGroup.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("GroupId",GroupId);
                    intent.putExtra("GroupName",GroupName);
                    intent.putExtra("GroupPhoto",GroupPhoto);
                    intent.putExtra("GroupInfo",GroupInfo);
                    intent.putExtra("type",GroupType);
                    intent.putExtra("NumMember",NumMember);
                    progressBar.setProgress(100);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(CreatePostGroup.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){

            progressBar.setVisibility(View.INVISIBLE);
            waitupload.setVisibility(View.INVISIBLE);
            body.setVisibility(View.VISIBLE);
            Toast.makeText(this, "يجب ان يكون اسم الملف بالغة الانجليزية", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.uploads1){
            intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,"image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);

        }else if(view.getId()==R.id.uploads2){

            new MaterialFilePicker()
                    .withActivity(CreatePostGroup.this)
                    .withRequestCode(2)
                    .start();

        }

    }
}