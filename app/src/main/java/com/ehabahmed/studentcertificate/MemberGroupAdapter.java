package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
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

public class MemberGroupAdapter extends RecyclerView.Adapter<MemberGroupAdapter.Holder> {
    Context context;
    ArrayList<member> listitems;
    public MemberGroupAdapter(Context context, ArrayList<member> listitems) {
        this.context = context;
        this.listitems = listitems;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.member,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            Glide.with(context).asBitmap()
                    .placeholder(R.drawable.profile2).circleCrop().load("https://ehab01998.com/images_profile/"+listitems.get(position).PersonalPhoto)
                    .into(holder.PersonPhoto);
        }else{
            Glide.with(context).asBitmap()
                    .placeholder(R.drawable.profile2).circleCrop().load("http://ehab01998.com/images_profile/"+listitems.get(position).PersonalPhoto)
                    .into(holder.PersonPhoto);
        }
        holder.PersonName.setText(listitems.get(position).PersonName);

        if(!listitems.get(position).getPersonName().startsWith("Dr")){
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ShowStudentProfile.class);
                    intent.putExtra("type","student");
                    intent.putExtra("ID",listitems.get(position).PersonalId);
                    intent.putExtra("Name",listitems.get(position).PersonName);
                    intent.putExtra("Photo",listitems.get(position).PersonalPhoto);
                    intent.putExtra("department",listitems.get(position).Personaldepartment);
                    intent.putExtra("level",listitems.get(position).Personallevel);
                    context.startActivity(intent);
                }
            });
        }else if(listitems.get(position).getPersonName().startsWith("Dr")){
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ShowStudentProfile.class);
                    intent.putExtra("type","doctor");
                    intent.putExtra("ID",listitems.get(position).PersonalId);
                    intent.putExtra("Name",listitems.get(position).PersonName);
                    intent.putExtra("Photo",listitems.get(position).PersonalPhoto);
                    context.startActivity(intent);

                }
            });



        }}

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView PersonPhoto;
        TextView PersonName;
        View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            PersonPhoto=itemView.findViewById(R.id.PersonPhoto);
            PersonName=itemView.findViewById(R.id.PersonName);
            this.view=itemView;

        }
    }
}
