package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StudentgroupAdapter extends RecyclerView.Adapter<StudentgroupAdapter.Holder> {
Info info;
Context context;
ArrayList<objectPostGroup> listitems;
Infogroup infogroup;

    public StudentgroupAdapter(Context context, ArrayList<objectPostGroup> listitems) {
        this.context = context;
        this.listitems = listitems;
        if(context instanceof Infogroup)
        infogroup= (Infogroup) context;
    }

    @Override
    public int getItemViewType(int position) {
      switch (listitems.get(position).group_type){
          case "timage":
              return 0;
          case "image":
              return 4;
          case "video":
              return 1;
          case "file":
              return 3;
          case "none":
              return 2;
          case "post":
              return 5;
          case "info":
              return 6;

      }

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        switch (viewType) {
            case 5:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.group_student_post,parent,false));
            case 6:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.info_group,parent,false));


        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        info= (Info) context.getApplicationContext();


        switch (listitems.get(position).group_type){
            case "post":
                if(info.getType().equals("student"))
                    Glide.with(context).load("https://ehab01998.com/images_profile/"+info.getPhoto())
                            .placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(holder.photoPersonPost);
                else if(info.getType().equals("doctor"))
                    Glide.with(context).load("https://ehab01998.com/images_profile/"+info.getDoctor_photo())
                            .placeholder(R.drawable.profile2).error(R.drawable.profile2).circleCrop().into(holder.photoPersonPost);

                holder.groupStudentPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(info, "ok", Toast.LENGTH_SHORT).show();
                    }
                });

                
                break;

            case "info":
                if(infogroup!=null)
                    infogroup.setInfoGroup(holder.groupName,holder.numberMember,holder.invite,
                    holder.iv_admin,holder.iv_member1,holder.iv_member2,holder.iv_member3,holder.iv_member4
                    ,holder.iv_member11,holder.iv_member22,holder.iv_member33,holder.iv_member44,holder.iv_admin00,holder.container1,holder.container2);


                break;

        }

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        //Start make post
        ImageView photoPersonPost;
        TextView makePost;
        View  groupStudentPost;
        //End Make Post



        //Start Info Group
        TextView groupName,numberMember;
        Button invite;
        ImageView iv_admin,iv_member1,iv_member2,iv_member3,iv_member4,iv_member11,iv_member22,iv_member33,iv_member44,iv_admin00;
        ConstraintLayout container1,container2;
        //End Info Group

        public Holder(@NonNull View itemView) {
            super(itemView);
            //Start make post
            photoPersonPost=itemView.findViewById(R.id.photoPersonPost);
            makePost=itemView.findViewById(R.id.makepost);
            this.groupStudentPost=itemView;
            //End Make Post



            //Start Info Group
            groupName=itemView.findViewById(R.id.group_name);
            numberMember=itemView.findViewById(R.id.numberMember);
            invite=itemView.findViewById(R.id.invite);
            iv_admin=itemView.findViewById(R.id.iv_admin);
            iv_member1=itemView.findViewById(R.id.iv_member1);
            iv_member2=itemView.findViewById(R.id.iv_member2);
            iv_member3=itemView.findViewById(R.id.iv_member3);
            iv_member4=itemView.findViewById(R.id.iv_member4);
            iv_member11=itemView.findViewById(R.id.iv_member11);
            iv_member22=itemView.findViewById(R.id.iv_member22);
            iv_member33=itemView.findViewById(R.id.iv_member33);
            iv_member44=itemView.findViewById(R.id.iv_member44);
            iv_admin00=itemView.findViewById(R.id.iv_admin00);
            container1=itemView.findViewById(R.id.container1);
            container2=itemView.findViewById(R.id.container2);
            //End Info Group



        }

    }
    public interface Infogroup{
        void setInfoGroup(TextView groupName,TextView groupNumber,Button invite,ImageView iv_admin,ImageView iv_member1,ImageView iv_member2,ImageView iv_member3,ImageView iv_member4,ImageView iv_member11,ImageView iv_member22,ImageView iv_member33,ImageView iv_member44,ImageView iv_admin00,ConstraintLayout Container1,ConstraintLayout Container2);

    }
}
