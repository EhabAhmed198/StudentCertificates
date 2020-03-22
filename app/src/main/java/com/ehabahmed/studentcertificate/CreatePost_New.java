package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreatePost_New extends AppCompatActivity implements  View.OnClickListener {

    ConstraintLayout body;
    ProgressBar progressBar;

    String group_id;
    EditText text;
    ImageView imageView;
    int type=-1, progressbar = 10;
    String name;
    String url, filepath;
    Intent intent;
    String types;
    TextView waitupload,uploadphoto,uploadfile;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post__new);
        body = findViewById(R.id.body);
        progressBar = findViewById(R.id.pr);
        waitupload = findViewById(R.id.waitupload);

        sharedPreferences =getSharedPreferences("number_news", Context.MODE_PRIVATE);

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
                 intent = new Intent(this, Doctor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("check","0");
                startActivity(intent);
                finish();
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

                Glide.with(CreatePost_New.this).load(filepath).into(imageView);



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
        RequestBody file_nothing = RequestBody.create(MediaType.parse("multipart/form-data"), "none");

        apiConfig.uploadtextnew(texts,type,file_nothing).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), R.string.upload_post_scuccess, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Doctor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("check","0");
                progressBar.setProgress(100);
                editor=sharedPreferences.edit();
                editor.putInt("News",(sharedPreferences.getInt("News",100)+1));
                editor.apply();
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
    RequestBody texts = RequestBody.create(MediaType.parse("multipart/form-data"), te);
try {
    MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

    apiConfig.uploadnew(texts, type, multipartBody).enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            Toast.makeText(getApplicationContext(), "تم النشر", Toast.LENGTH_LONG).show();
             intent = new Intent(getApplicationContext(), Doctor.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("check", "0");
            progressBar.setProgress(100);
            editor=sharedPreferences.edit();
            editor.putInt("News",(sharedPreferences.getInt("News",100)+1));
            editor.apply();
            startActivity(intent);
            finish();
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(CreatePost_New.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    .withActivity(CreatePost_New.this)
                    .withRequestCode(2)
                    .start();

        }

    }
}