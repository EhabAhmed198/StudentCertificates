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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupWaitAdapter extends RecyclerView.Adapter<GroupWaitAdapter.Holder> {
Context context;
ArrayList<NewGroupAdd> listitems;

    public GroupWaitAdapter(Context context, ArrayList<NewGroupAdd> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.group_wait_shape,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://"+listitems.get(position).group_photo;

        }else{
            url="http://"+listitems.get(position).group_photo;

        }

        Glide.with(context).load(url)
                .into(holder.GroupPhoto);
        holder.GroupName.setText(listitems.get(position).group_name);
holder.GroupInfo.setText(listitems.get(position).name+" Invites you to join the "+listitems.get(position).group_name+"\n"+listitems.get(position).group_info);
holder.accept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       changeStateStudentGroup(listitems.get(position).Id,"YES");
    }
});
holder.noaccept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        changeStateStudentGroup(listitems.get(position).Id,"Refused");


    }
});
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView GroupPhoto;
        TextView GroupName,GroupInfo;
        Button accept,noaccept;
        public Holder(@NonNull View itemView) {
            super(itemView);
            GroupPhoto=itemView.findViewById(R.id.GroupPhoto);
            GroupName=itemView.findViewById(R.id.GroupName);
            GroupInfo=itemView.findViewById(R.id.GroupInfo);
            accept=itemView.findViewById(R.id.accept);
            noaccept=itemView.findViewById(R.id.noaccept);
        }
    }

    void changeStateStudentGroup(String changeState,String State){
       Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ehab01998.com")
             .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiConfig  apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.changeState(changeState, State).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Intent intent=new Intent(context,Groups.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
