package com.ehabahmed.studentcertificate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.security.Provider;

import static android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED;

public class Book_deatls extends AppCompatActivity implements View.OnClickListener {
    CollapsingToolbarLayout collapsetitle;
    ImageView iv_collapse;
    TextView tv_detals;
    String book_detals,book_photo,book_name,book_link;
    Intent intent;
Button dwonload,copyLink;

String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_deatls);

        collapsetitle=findViewById(R.id.collapseing);
        iv_collapse=findViewById(R.id.iv_collapseing);
        tv_detals=findViewById(R.id.detals);
        copyLink=findViewById(R.id.copyLink);
type=getIntent().getExtras().getString("type","no");
if(type.equals("program")) {
    copyLink.setVisibility(View.VISIBLE);
copyLink.setOnClickListener(this);
}
else
    copyLink.setVisibility(View.INVISIBLE);

        dwonload=findViewById(R.id.dwonload_book);
        book_detals=getIntent().getExtras().getString("detals");
        book_photo=getIntent().getExtras().getString("photo");
        book_name=getIntent().getExtras().getString("name");
        book_link=getIntent().getExtras().getString("link");
        tv_detals.setText(book_detals);

        Glide.with(getApplicationContext()).load(book_photo).into(iv_collapse);
        iv_collapse.setOnClickListener(this);
        dwonload.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_collapseing:
            intent = new Intent(this, Show_image.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("news_ivname", book_photo);
            startActivity(intent);
            break;

            case R.id.dwonload_book:

                Toast.makeText(this, getResources().getString(R.string.download), Toast.LENGTH_SHORT).show();
                    Uri uri=Uri.parse(book_link);

                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .mkdirs();
                 DownloadManager  downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);

                            downloadManager.enqueue(new DownloadManager.Request(uri)
                                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                            DownloadManager.Request.NETWORK_MOBILE)
                                    .setAllowedOverRoaming(false)
                                    .setTitle(book_name)
                                    .setDescription(book_detals)
                                    .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                            book_name));









                break;
            case R.id.copyLink:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", book_link);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, getResources().getString(R.string.scuccessCopy), Toast.LENGTH_SHORT).show();
                break;
        }
    }



}
