package com.ehabahmed.studentcertificate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DoctorSettingAdapter extends RecyclerView.Adapter<DoctorSettingAdapter.Holder> {
Context context;
ArrayList<setting_object> listitems;
    SharedPreferences.Editor editor,editor1;
    SharedPreferences sharedPreferences,sharedPreferences1;
    Intent intent;
Info info;
    public DoctorSettingAdapter(Context context, ArrayList<setting_object> listitems) {
        this.context = context;
        this.listitems = listitems;
        info= (Info) context.getApplicationContext();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(context).inflate(R.layout.setting_doctor,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.name.setText(listitems.get(position).name);
        if(listitems.get(position).id==1) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                Glide.with(context).load("https://ehab01998.com/images_profile/" + info.getDoctor_photo())
                        .circleCrop().placeholder(R.drawable.profile2).into(holder.image);
            }else{
                Glide.with(context).load("http://ehab01998.com/images_profile/" + info.getDoctor_photo())
                        .circleCrop().placeholder(R.drawable.profile2).into(holder.image);

            }

        } else
            holder.image.setImageResource(listitems.get(position).Image);


        sharedPreferences=context.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           switch (listitems.get(position).id){
               case 1:
                   intent=new Intent(context,DoctorProfile.class);
                   context.startActivity(intent);
                   break;
               case 2:
                   intent=new Intent(context,Doctor_Table.class);
                   context.startActivity(intent);
                   break;
               case 3:
                   intent=new Intent(context,DoctorCourses.class);
                   context.startActivity(intent);

                   break;
               case 4:
                   intent=new Intent(context,DoctorLibrary.class);
                   context.startActivity(intent);

                   break;
               case 5:
                   intent=new Intent(context,DoctorExams.class);
                   context.startActivity(intent);

                   break;
               case 6:
                   intent=new Intent(context,DoctorCompetition.class);
                   context.startActivity(intent);

                   break;
               case 7:
                   intent=new Intent(context,Programs.class);
                   context.startActivity(intent);

                   break;
               case 8:
                   intent=new Intent(context,Inbox.class);
                   context.startActivity(intent);
                   break;
               case 9:
                   intent=new Intent(context,TypeMap.class);
                   context.startActivity(intent);

                   break;

               case 10:
                   editor.putString("type","NoData");
                   editor.apply();
                   sharedPreferences1 = context.getSharedPreferences("doctor", Context.MODE_PRIVATE);
                   editor1=sharedPreferences1.edit();
                   editor1.putString("id","-1");
                   editor1.apply();
                   intent=new Intent(context,MainActivity.class);
                   context.startActivity(intent);
                   ((Activity)context).finish();
                   break;
           }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
ImageView image;
TextView name;
View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
image=itemView.findViewById(R.id.imageview);
name=itemView.findViewById(R.id.name);
this.view=itemView;
        }
    }
}
