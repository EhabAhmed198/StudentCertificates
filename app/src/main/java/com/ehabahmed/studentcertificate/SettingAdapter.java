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

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.Holder> {
ArrayList<setting_object> listitems;
Context context;
Intent intent;
    SharedPreferences.Editor editor,editor1;
    SharedPreferences sharedPreferences,sharedPreferences1;
    Info info;
    public SettingAdapter(ArrayList<setting_object> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.setting_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        sharedPreferences=context.getSharedPreferences("login", Context.MODE_PRIVATE);
        sharedPreferences1 = context.getSharedPreferences("student", Context.MODE_PRIVATE);
        editor1=sharedPreferences1.edit();
       editor=sharedPreferences.edit();
     holder.name.setText(listitems.get(position).name);
     info= (Info) context.getApplicationContext();
     if(listitems.get(position).id==1){


         if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
             Glide.with(context).load("https://ehab01998.com/images_profile/"+info.getPhoto())
                     .circleCrop().placeholder(R.drawable.profile2).into(holder.imageView);
         }else{
             Glide.with(context).load("http://ehab01998.com/images_profile/"+info.getPhoto())
                     .circleCrop().placeholder(R.drawable.profile2).into(holder.imageView);

         }
     }


else
    holder.imageView.setImageResource(listitems.get(position).Image);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int id=listitems.get(position).id;
            switch (id){
                case 1:
                    intent=new Intent(context,Student_peofile.class);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent=new Intent(context,Table_student.class);

                    context.startActivity(intent);
                    break;
                case 3:
                    intent=new Intent(context,Result_Student.class);
                    context.startActivity(intent);
                    break;

                case 4:
                    intent=new Intent(context,Courses.class);
                    context.startActivity(intent);
                    break;
                case 5:
                    intent=new Intent(context,Library.class);
                    context.startActivity(intent);
                    break;
                case 6:
                    intent=new Intent(context,Exams.class);
                    context.startActivity(intent);
                    break;


                case 8:
                    intent=new Intent(context, Inbox.class);
                    context.startActivity(intent);
                    break;
                case 9:
                    intent=new Intent(context,GraduationCertificate.class);
                    context.startActivity(intent);
                    break;

                case 10:
                    intent=new Intent(context,Compition.class);
                    context.startActivity(intent);
                    break;
                case 11:
                    intent=new Intent(context,Programs.class);
                    context.startActivity(intent);
                    break;

                case 12:
                    intent=new Intent(context,Articles.class);
                    context.startActivity(intent);
                    break;

                case 13:
                    intent=new Intent(context,TypeMap.class);
                    context.startActivity(intent);
                    break;
                case 14:
                    info=(Info)context.getApplicationContext();

                    info.setId("-1");
                    info.setPhoto("-1");
                    editor.putString("type","NoData");
                    editor.apply();


                    editor1.putString("department","-1");
                    editor1.putString("level","-1");
                    editor1.apply();
                    SharedPreferences   sharedPreferences2 = context.getSharedPreferences("Notification", Context.MODE_PRIVATE);
                    SharedPreferences.Editor   editor2 = sharedPreferences2.edit();
                    editor2.putString("type", "-1");
                    editor2.apply();
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
        ImageView imageView;
        TextView name;
        View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview);
            name=itemView.findViewById(R.id.name);
            this.view=itemView;
        }
    }
}
