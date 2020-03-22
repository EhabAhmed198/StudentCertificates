package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.Holder> {
Context context;
ArrayList<member> listitems;
String type,checkInvite;
Sendinvite sendinvite;
ArrayList<String> list=new ArrayList<>();
    public MemberAdapter(Context context, ArrayList<member> listitems,String type,String checkInvite) {
        this.context = context;
        this.listitems = listitems;
        this.type=type;
        this.checkInvite=checkInvite;
        if(context instanceof Sendinvite){
            sendinvite= (Sendinvite) context;
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(checkInvite.equals("invite"))
        return new Holder(LayoutInflater.from(context).inflate(R.layout.member,parent,false));
        else if(checkInvite.equals("NoInvite"))
            return new Holder(LayoutInflater.from(context).inflate(R.layout.member2,parent,false));
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.setIsRecyclable(false);
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
        if(type.equals("student")){
        holder.department.setText(convertDepartment(listitems.get(position).Personaldepartment));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ShowStudentProfile.class);
                intent.putExtra("type",type);
                intent.putExtra("ID",listitems.get(position).PersonalId);
                intent.putExtra("Name",listitems.get(position).PersonName);
                intent.putExtra("Photo",listitems.get(position).PersonalPhoto);
                intent.putExtra("department",listitems.get(position).Personaldepartment);
                intent.putExtra("level",listitems.get(position).Personallevel);
                context.startActivity(intent);
            }
        });
        }else if(type.equals("doctor")){
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ShowStudentProfile.class);
                    intent.putExtra("type",type);
                    intent.putExtra("ID",listitems.get(position).PersonalId);
                    intent.putExtra("Name",listitems.get(position).PersonName);
                    intent.putExtra("Photo",listitems.get(position).PersonalPhoto);
                    context.startActivity(intent);

                }
            });



        }
        if(checkInvite.equals("invite") && sendinvite!=null){
            if(list.contains(listitems.get(position).PersonalId)){
                holder.send.setVisibility(View.INVISIBLE);
            }
            holder.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      list.add(listitems.get(position).PersonalId);
                    sendinvite.invite(holder.pb_send,holder.send,holder.correct,listitems.get(position).PersonalId);

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView PersonPhoto;
        TextView PersonName;
        TextView department;
        View view;
        ProgressBar pb_send;
        Button send;
        ImageView correct;
        public Holder(@NonNull View itemView) {
            super(itemView);
            PersonPhoto=itemView.findViewById(R.id.PersonPhoto);
            PersonName=itemView.findViewById(R.id.PersonName);
            department=itemView.findViewById(R.id.department);
            this.view=itemView;
            pb_send=itemView.findViewById(R.id.pb_send);
            send=itemView.findViewById(R.id.send);
            correct=itemView.findViewById(R.id.correct);

        }

    }
    String convertDepartment(String department){
      String  data_department[]=context.getResources().getStringArray(R.array.departments);
        int id=Integer.parseInt(department);
        if(id==1){
            return data_department[0];
        }else if(id==2){
            return data_department[1];

        } else if(id==3){
            return data_department[2];
        }
        return null;
    }
    public void updateList(ArrayList<member> newList){
        listitems=new ArrayList<>();
        listitems.addAll(newList);
        notifyDataSetChanged();
    }

    public  interface Sendinvite{
        void invite(ProgressBar bar,Button Send,ImageView correct,String code);
    }
}
