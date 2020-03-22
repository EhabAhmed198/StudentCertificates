package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder> {
    Context context;
ArrayList<comment> listitems;
Info studentinfo;

    public CommentAdapter(Context context, ArrayList<comment> listitems) {
        this.context = context;
        this.listitems = listitems;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.comment,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        studentinfo=(Info)context.getApplicationContext();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            Glide.with(context).asBitmap().apply(new RequestOptions().override(150, 150))
                    .placeholder(R.drawable.profile2).circleCrop()
                    .load("https://ehab01998.com/images_profile/"+listitems.get(position).comment_photo)
                    .into(holder.photoperson);
        }else{
            Glide.with(context).asBitmap().apply(new RequestOptions().override(150, 150))
                    .placeholder(R.drawable.profile2).circleCrop()
                    .load("http://ehab01998.com/images_profile/"+listitems.get(position).comment_photo)
                    .into(holder.photoperson);
        }



        holder.namePerson.setText(listitems.get(position).comment_user);
        if(!listitems.get(position).comment_user.startsWith("Dr."))
        holder.codePerson.setText("  "+"("+listitems.get(position).code+")");
        else if(listitems.get(position).comment_user.startsWith("Dr."))
            holder.codePerson.setText("");
        holder.datatimePublish.setText(listitems.get(position).data_time);
        holder.textPerson.setText(listitems.get(position).comment_text);

        holder.photoperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Show_image.class);
                intent.putExtra("news_ivname","http://ehab01998.com/images_profile/"+studentinfo.getPhoto());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView photoperson;
        TextView namePerson,codePerson,datatimePublish,textPerson;


        public Holder(@NonNull View itemView) {
            super(itemView);
            photoperson=itemView.findViewById(R.id.photoPerson);
            namePerson=itemView.findViewById(R.id.namePerson);
            codePerson=itemView.findViewById(R.id.codePerson);
            datatimePublish=itemView.findViewById(R.id.datatimePublish);
            textPerson=itemView.findViewById(R.id.textPerson);

        }
    }
}
