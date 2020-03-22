package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentgroupAdapter extends RecyclerView.Adapter<StudentgroupAdapter.Holder>  {
Info info;
Context context;
ArrayList<objectPostGroup> listitems;
Infogroup infogroup;
int pos;
Gson gson;
GsonBuilder builder;
    long count=0;
    MediaPlayer player;
    Retrofit retrofit;
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
            case 2:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.post_student_text_group,parent,false));
            case 0:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.post_student_timage_group,parent,false));
            case 4:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.post_student_image_group,parent,false));
            case 3:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.post_student_file_group,parent,false));

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
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
                        if(infogroup!=null)
                        infogroup.sendDataIntoGroup();
                    }
                });



                break;

            case "info":
                if(infogroup!=null)
                    infogroup.setInfoGroup(holder.groupName,holder.numberMember,holder.invite,
                    holder.iv_admin,holder.iv_member1,holder.iv_member2,holder.iv_member3,holder.iv_member4
                    ,holder.iv_member11,holder.iv_member22,holder.iv_member33,holder.iv_member44,holder.iv_admin00,holder.container1,holder.container2);


                break;

            case "none":
                getNumberLike(listitems.get(position).group_Id,holder.Nimage_group_like,holder.Naddlike);
                 holder.Nname.setText(listitems.get(position).getPublisherName());
                 Glide.with(context).load("http://ehab01998.com/"+listitems.get(position).publisherPhoto).placeholder(R.drawable.profile2).into(holder.NpersonPhoto);
                 holder.Ntextonly.setText(listitems.get(position).getGroup_text());
                 holder.Ndata_time.setText(listitems.get(position).getGroup_time());

                holder.iaddcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                commentNumber(holder.iimage_group_comment,listitems.get(position).group_Id);
                holder.iimage_group_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                holder.Naddlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(info.getType().equals("student") || info.getType().equals("certificate")) {
                            addLike(info.getName(),info.getPhoto() ,info.getId(), listitems.get(position).group_Id, holder.iaddlike,holder.Nimage_group_like);
                        }else if(info.getType().equals("doctor")){
                            addLike(info.getDoctor_name(),info.getDoctor_photo(),info.getDoctor_id() , listitems.get(position).group_Id, holder.iaddlike,holder.Nimage_group_like);

                        }
                    }
                });
                if(info.getType().equals("student") || info.getType().equals("certificate")) {
                    checkLikeButton(holder.Naddlike,info.getId(),listitems.get(position).group_Id);
                }else if(info.getType().equals("doctor")){
                    checkLikeButton(holder.Naddlike,info.getDoctor_id(),listitems.get(position).group_Id);

                }
                holder.Nimage_group_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,MemberMakeLike.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                break;

            case "timage":
                getNumberLike(listitems.get(position).group_Id,holder.timage_group_like,holder.taddlike);

                holder.tname.setText(listitems.get(position).getPublisherName());
                Glide.with(context).load("http://ehab01998.com/"+listitems.get(position).publisherPhoto).placeholder(R.drawable.profile2).into(holder.tpersonPhoto);
                holder.tdata_time.setText(listitems.get(position).getGroup_time());
                Glide.with(context).load("http://"+listitems.get(position).group_ivname).into(holder.tgroup_image);
                holder.tgroup_text.setText(listitems.get(position).getGroup_text());
                holder.taddcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                commentNumber(holder.timage_group_comment,listitems.get(position).group_Id);
                holder.timage_group_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                holder.taddlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(info.getType().equals("student") || info.getType().equals("certificate")) {
                            addLike(info.getName(),info.getPhoto() ,info.getId(), listitems.get(position).group_Id, holder.iaddlike,holder.timage_group_like);
                        }else if(info.getType().equals("doctor")){
                            addLike(info.getDoctor_name(),info.getDoctor_photo(),info.getDoctor_id() , listitems.get(position).group_Id, holder.iaddlike,holder.timage_group_like);

                        }
                    }
                });
                if(info.getType().equals("student") || info.getType().equals("certificate")) {
                    checkLikeButton(holder.taddlike,info.getId(),listitems.get(position).group_Id);
                }else if(info.getType().equals("doctor")){
                    checkLikeButton(holder.taddlike,info.getDoctor_id(),listitems.get(position).group_Id);

                }
                holder.timage_group_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,MemberMakeLike.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                break;
            case "image":
                getNumberLike(listitems.get(position).group_Id,holder.iimage_group_like,holder.iaddlike);

                holder.iname.setText(listitems.get(position).getPublisherName());
                Glide.with(context).load("http://ehab01998.com/"+listitems.get(position).publisherPhoto).placeholder(R.drawable.profile2).into(holder.ipersonPhoto);
                holder.idata_time.setText(listitems.get(position).getGroup_time());
                Glide.with(context).load("http://"+listitems.get(position).group_ivname).into(holder.igroup_image);
                holder.iaddcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                commentNumber(holder.iimage_group_comment,listitems.get(position).group_Id);

                holder.iimage_group_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                holder.iaddlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(info.getType().equals("student") || info.getType().equals("certificate")) {
                            addLike(info.getName(),info.getPhoto() ,info.getId(), listitems.get(position).group_Id, holder.iaddlike,holder.iimage_group_like);
                        }else if(info.getType().equals("doctor")){
                            addLike(info.getDoctor_name(),info.getDoctor_photo(),info.getDoctor_id() , listitems.get(position).group_Id, holder.iaddlike,holder.iimage_group_like);

                        }
                    }
                });
                if(info.getType().equals("student") || info.getType().equals("certificate")) {
                    checkLikeButton(holder.iaddlike,info.getId(),listitems.get(position).group_Id);
                }else if(info.getType().equals("doctor")){
                    checkLikeButton(holder.iaddlike,info.getDoctor_id(),listitems.get(position).group_Id);

                }
                holder.iimage_group_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,MemberMakeLike.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                break;

            case "file":
                getNumberLike(listitems.get(position).group_Id,holder.timage_group_like,holder.taddlike);

                holder.tname.setText(listitems.get(position).getPublisherName());
                Glide.with(context).load("http://ehab01998.com/"+listitems.get(position).publisherPhoto).placeholder(R.drawable.profile2).into(holder.tpersonPhoto);
                holder.tdata_time.setText(listitems.get(position).getGroup_time());
                holder.tgroup_text.setText(listitems.get(position).getGroup_text());
                String type = listitems.get(position).group_ivname.substring(listitems.get(position).group_ivname.lastIndexOf("."));
                switch (type) {

                    case ".pdf":
                        holder.tgroup_image.setBackgroundResource(R.drawable.pdf);
                        break;

                    case ".doc":
                        holder.tgroup_image.setBackgroundResource(R.drawable.doc);
                        break;

                    case ".docx":
                        holder.tgroup_image.setBackgroundResource(R.drawable.docx);
                        break;
                    case ".xlsx":
                        holder.tgroup_image.setBackgroundResource(R.drawable.xlsx);
                        break;
                    case ".pptx":
                        holder.tgroup_image.setBackgroundResource(R.drawable.pptx);
                        break;
                    case ".ppt":
                        holder.tgroup_image.setBackgroundResource(R.drawable.ppt);
                        break;


                }
                holder.fileview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent  intent = new Intent(context, Show_files.class);
                      String file="http://"+listitems.get(position).group_ivname;
                        intent.putExtra("file", file);
                        context.startActivity(intent);
                    }
                });
                holder.taddcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                commentNumber(holder.timage_group_comment,listitems.get(position).group_Id);

                holder.timage_group_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,CommentStudentPost.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
                holder.taddlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(info.getType().equals("student") || info.getType().equals("certificate")) {
                            addLike(info.getName(),info.getPhoto() ,info.getId(), listitems.get(position).group_Id, holder.iaddlike,holder.timage_group_like);
                        }else if(info.getType().equals("doctor")){
                            addLike(info.getDoctor_name(),info.getDoctor_photo(),info.getDoctor_id() , listitems.get(position).group_Id, holder.iaddlike,holder.timage_group_like);

                        }
                    }
                });
                if(info.getType().equals("student") || info.getType().equals("certificate")) {
                    checkLikeButton(holder.taddlike,info.getId(),listitems.get(position).group_Id);
                }else if(info.getType().equals("doctor")){
                    checkLikeButton(holder.taddlike,info.getDoctor_id(),listitems.get(position).group_Id);

                }
                holder.timage_group_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,MemberMakeLike.class);
                        intent.putExtra("postId",listitems.get(position).group_Id);
                        context.startActivity(intent);
                    }
                });
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




        //Strat Text Group None
        TextView Nname,Ndata_time,Ntextonly,Nimage_group_comment,Nimage_group_like;
        Button Naddcomment,Naddlike;
ImageView NpersonPhoto;
        //End Text Group None

        //Strat timage Group
       TextView tname,tdata_time,tgroup_text,timage_group_comment,timage_group_like;
        Button taddcomment,taddlike;
        ImageView tpersonPhoto,tgroup_image;
        View fileview;
        //End timage Group


        //Strat image Group
        TextView iname,idata_time,iimage_group_comment,iimage_group_like;
        Button iaddcomment,iaddlike;
        ImageView ipersonPhoto,igroup_image;
        //End image Group

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






            //Strat Text Group None
            Nname=itemView.findViewById(R.id.name);
            Ndata_time=itemView.findViewById(R.id.data_time);
            Ntextonly=itemView.findViewById(R.id.group_text);
            Nimage_group_comment=itemView.findViewById(R.id.image_group_comment);
            Nimage_group_like=itemView.findViewById(R.id.image_group_like);
            Naddcomment=itemView.findViewById(R.id.addcomment);
            Naddlike=itemView.findViewById(R.id.addlike);
            NpersonPhoto=itemView.findViewById(R.id.personPhoto);

            //End Text Group None


            //Strat timage Group

            tname=itemView.findViewById(R.id.name);
            tdata_time=itemView.findViewById(R.id.data_time);
            tgroup_image=itemView.findViewById(R.id.group_image);
            tgroup_text=itemView.findViewById(R.id.group_text);
            timage_group_comment=itemView.findViewById(R.id.image_group_comment);
            timage_group_like=itemView.findViewById(R.id.image_group_like);
            taddcomment=itemView.findViewById(R.id.addcomment);
            taddlike=itemView.findViewById(R.id.addlike);
            tpersonPhoto=itemView.findViewById(R.id.personPhoto);
      fileview=itemView;
            //End timage Group


            //Strat image Group

            iname=itemView.findViewById(R.id.name);
            idata_time=itemView.findViewById(R.id.data_time);
            igroup_image=itemView.findViewById(R.id.group_image);
            iimage_group_comment=itemView.findViewById(R.id.image_group_comment);
            iimage_group_like=itemView.findViewById(R.id.image_group_like);
            iaddcomment=itemView.findViewById(R.id.addcomment);
            iaddlike=itemView.findViewById(R.id.addlike);
            ipersonPhoto=itemView.findViewById(R.id.personPhoto);

            //End image Group


        }

    }
    public void checkLikeButton(final Button button, String code, String postid){
        builder=new GsonBuilder();
        builder.setLenient();
        gson=builder.create();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiConfig apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.checkColorLikeButton(code,postid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("color")){
                    button.setBackgroundColor(Color.parseColor("#2A3D60"));
                    button.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void getNumberLike(final String postid, final TextView textView, final Button button){

         retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiConfig apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.getNumberLike(postid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                       button.setVisibility(View.VISIBLE);
                if(Integer.parseInt(response.body())>0)
                    textView.setText(response.body()+"");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getNumberLike(postid,textView,button);
            }
        });

    }
    public void addLike(String name, String photo, String code, String postid, final Button button, final TextView textView){
        builder=new GsonBuilder();
        builder.setLenient();
        gson=builder.create();
        player=MediaPlayer.create(context,R.raw.soundlike);
        player.start();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiConfig apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.addLikeStudentGroup(name,photo,code,postid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("delete")) {
                    button.setBackgroundColor(Color.WHITE);
                    button.setTextColor(Color.GRAY);
                    String text=String.valueOf(Integer.parseInt(textView.getText().toString())-1);
                    textView.setText(text);
                }
                else if(response.body().equals("added")) {
                    button.setBackgroundColor(Color.parseColor("#2A3D60"));
                    button.setTextColor(Color.WHITE);
                    String str=textView.getText().toString();
                    if(!str.isEmpty()) {
                        String text = String.valueOf(Integer.parseInt(str) + 1) + "";
                        textView.setText(text);
                    }
                  else {
                        textView.setText("1");
                  }

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(info, ""+t.getMessage()+" addLikeStudentGroup", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public interface Infogroup{
        void setInfoGroup(TextView groupName,TextView groupNumber,Button invite,ImageView iv_admin,ImageView iv_member1,ImageView iv_member2,ImageView iv_member3,ImageView iv_member4,ImageView iv_member11,ImageView iv_member22,ImageView iv_member33,ImageView iv_member44,ImageView iv_admin00,ConstraintLayout Container1,ConstraintLayout Container2);
       void sendDataIntoGroup();
    }
    private void commentNumber(final TextView textView, String PostId){

        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference reference =firebaseDatabase.getReference().child("comment").child(""+PostId);
        ValueEventListener valueEventListener =new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           count =dataSnapshot.getChildrenCount();
               if(count >0) {
                   textView.setText("" + count);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
    }
}
