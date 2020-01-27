package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.Holder> {
    Context context;
private ArrayList<Articles_Object> listitems;

    public ArticlesAdapter(Context context, ArrayList<Articles_Object> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.articles_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.date.setText(listitems.get(position).date);
        holder.title.setText(listitems.get(position).title);
        Glide.with(context).load(listitems.get(position).photo).into(holder.im_articles);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent intent=new Intent(context,News_detals.class);
                intent.putExtra("news_detals", listitems.get(position).deatls);  //articles_details=news_detals
                intent.putExtra("news_ivname", listitems.get(position).photo);   //articles_photo=news_ivname
            context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView date,title;
        ImageView im_articles;
        View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.data_time);
            title=itemView.findViewById(R.id.tv_articles);
            im_articles=itemView.findViewById(R.id.image_articles);
            view=itemView;
        }
    }
    public void updateList(ArrayList<Articles_Object> newList){
        listitems=new ArrayList<>();
        listitems.addAll(newList);
        notifyDataSetChanged();
    }
}
