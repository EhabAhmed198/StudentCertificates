package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.app.AlertDialog;
import android.content.Intent;
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

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
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


public class CreatePostLecture extends AppCompatActivity implements  View.OnClickListener {

    ConstraintLayout body;
    ProgressBar progressBar;
    String group_id;
    EditText text;
    ImageView imageView;
    VideoView videoView;
    Bitmap bitmap;
    int type = -1, progressbar = 10;
    String name;
    RequestQueue requestQueue;
    String url, filepath;
    Intent intent;
    String types;

    TextView waitupload,uploadphoto,uploadvideo,uploadfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        body = findViewById(R.id.body);
        progressBar = findViewById(R.id.pr);
        waitupload = findViewById(R.id.waitupload);
        requestQueue = Volley.newRequestQueue(this);
        group_id = getIntent().getExtras().getString("group_id");
        name = getIntent().getExtras().getString("name");
        imageView = findViewById(R.id.image);
        videoView = findViewById(R.id.video);
        text = findViewById(R.id.et_text);
       uploadphoto=findViewById(R.id.uploads1);
        uploadvideo=findViewById(R.id.uploads2);
        uploadfile=findViewById(R.id.uploads3);
    uploadphoto.setOnClickListener(this);
        uploadvideo.setOnClickListener(this);
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
               intent = new Intent(this, DGroup_Post.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("group_id", group_id);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
                break;
            case R.id.ok:
                item.setVisible(false);
                if (type == 0) {

                    if(!text.getText().toString().equals(""))
                    types = "timage";
                    else
                        types="image";
                    uploadfile();
                } else if (type == 1) {

                    types = "video";
                    uploadfile();
                } else if (type == 2) {

                    types = "file";
                    uploadfile();

                } else if (type == -1) {
                    uploadtext();

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
cursor.close();

            filepath = picturePath;
            type = 0;
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                try {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), data.getData()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
                imageView.setBackground(drawable);

            }


        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.VISIBLE);
            type = 1;
            videoView.setVideoURI(data.getData());
            videoView.start();
            String[] filePathColumn = { MediaStore.Video.Media.DATA };

            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

           cursor.close();



            filepath = picturePath;

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            type = 2;
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




    public void uploadfile() {
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .build();

        ApiConfig apiConfig = retrofit.create(ApiConfig.class);

        File file = new File(filepath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), group_id);
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), types);
        RequestBody texts = RequestBody.create(MediaType.parse("multipart/form-data"), text.getText().toString().trim());
try {
    MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

    apiConfig.upload(id, texts, type, multipartBody).enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            Toast.makeText(getApplicationContext(), R.string.upload_post_scuccess, Toast.LENGTH_LONG).show();
             intent = new Intent(getApplicationContext(), DGroup_Post.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("group_id", group_id);
            intent.putExtra("name", name);
            progressBar.setProgress(100);
            startActivity(intent);
            finish();
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

            new AlertDialog.Builder(CreatePostLecture.this)
                    .setTitle(getResources().getString(R.string.Error)).setMessage(getResources().getString(R.string.errorupload))
                    .show();

        }
    });
}catch (Exception e){

    progressBar.setVisibility(View.INVISIBLE);
    waitupload.setVisibility(View.INVISIBLE);
    body.setVisibility(View.VISIBLE);
    Toast.makeText(this, R.string.wrong, Toast.LENGTH_LONG).show();
}

    }


    public void uploadtext() {
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .build();

        ApiConfig apiConfig = retrofit.create(ApiConfig.class);


        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), group_id);
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "none");
        RequestBody texts = RequestBody.create(MediaType.parse("multipart/form-data"), text.getText().toString().trim());
        RequestBody file_nothing = RequestBody.create(MediaType.parse("multipart/form-data"), "none");

        apiConfig.uploadtext(id, texts, type, file_nothing).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), R.string.upload_post_scuccess, Toast.LENGTH_LONG).show();
               intent = new Intent(getApplicationContext(), DGroup_Post.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("group_id", group_id);
                progressBar.setProgress(100);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                new AlertDialog.Builder(CreatePostLecture.this)
                        .setTitle(getResources().getString(R.string.Error)).setMessage(getResources().getString(R.string.errorupload))
                        .show();


            }
        });


    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.uploads1){
            intent = new Intent( );
            intent.setAction(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,"image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);

        }else    if(view.getId()==R.id.uploads2){
            intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setDataAndType( MediaStore.Images.Media.INTERNAL_CONTENT_URI,"video/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

        }
        else    if(view.getId()==R.id.uploads3){
            new MaterialFilePicker()
                    .withActivity(CreatePostLecture.this)
                    .withRequestCode(2)
                    .start();


        }

    }
}