package com.ehabahmed.studentcertificate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.WebViewHolder> {
    private ArrayList<String> arrayList;
    private Context context;

    public WebAdapter(ArrayList<String> set, Context context) {
        arrayList = set;
        this.context = context;
    }

    @NonNull
    @Override
    public WebViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_holder, parent, false);

        WebViewHolder webViewHolder = new WebViewHolder(view);
        return webViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WebViewHolder holder, final int position) {
        holder.webView.loadUrl(arrayList.get(position));
        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Browser.class);
                intent.putExtra("URL",arrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class WebViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        FloatingActionButton floatingActionButton;

        @SuppressLint("SetJavaScriptEnabled")
        WebViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.Websites_Holder);
            floatingActionButton = itemView.findViewById(R.id.floatingActionButton_web);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
        }
    }
}
