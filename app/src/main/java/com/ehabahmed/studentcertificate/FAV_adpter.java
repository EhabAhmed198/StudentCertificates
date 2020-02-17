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

import java.util.ArrayList;

public class FAV_adpter extends RecyclerView.Adapter<FAV_adpter.Holder> {
Context context;
ArrayList<FAV_object> listitems;
    Intent intent;
    char type;
    public FAV_adpter(Context context, ArrayList<FAV_object> listitems,char type) {
        this.context = context;
        this.listitems = listitems;
        this.type=type;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.fav_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.textView.setText(listitems.get(position).name);
        holder.imageView.setImageResource(listitems.get(position).image);
        if(type=='s'){
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:
                    intent=new Intent(context,FavNews.class);
                    intent.putExtra("fav","news");
                    context.startActivity(intent);
                    break;
                    case 1:
                      intent=new Intent(context,Courses.class);
                        intent.putExtra("fav","course");
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(context,Library.class);
                        intent.putExtra("fav","library");
                        context.startActivity(intent);

                        break;
                    case 3:
                        intent=new Intent(context,Compition.class);
                        intent.putExtra("fav","comptition");
                        context.startActivity(intent);

                        break;
                    case 4:
                        intent=new Intent(context,Exams.class);
                        intent.putExtra("fav","exams");
                        context.startActivity(intent);
                        break;
                }
            }
        });}else if(type=='d'){
           holder.view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   switch (position){
                       case 0:
                           intent=new Intent(context,DFavNews.class);
                           intent.putExtra("fav","news");
                           context.startActivity(intent);
                           break;
                       case 1:
                           intent=new Intent(context,DoctorCourses.class);
                           intent.putExtra("fav","course");
                           context.startActivity(intent);
                           break;

                       case 2:
                           intent=new Intent(context,DoctorLibrary.class);
                           intent.putExtra("fav","library");
                           context.startActivity(intent);
                           break;
                       case 3:
                           intent=new Intent(context,DoctorCompetition.class);
                           intent.putExtra("fav","comptition");
                           context.startActivity(intent);

                           break;
                       case 4:
                           intent=new Intent(context,DoctorExams.class);
                           intent.putExtra("fav","exams");
                           context.startActivity(intent);
                           break;


                   }
               }
           });
        }

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.im_fav);
            textView=itemView.findViewById(R.id.fav_text);
            this.view=itemView;

        }
    }
}
