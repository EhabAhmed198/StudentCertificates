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

public class GroupNameAdapter  extends RecyclerView.Adapter<GroupNameAdapter.Holder> {

    Context context;
    ArrayList<DataGroup> listitems;

    public GroupNameAdapter(Context context, ArrayList<DataGroup> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.groupname,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://"+listitems.get(position).GroupPhoto;

        }else{
            url="http://"+listitems.get(position).GroupPhoto;

        }

        Glide.with(context).load(url)
                .into(holder.groupphoto);
       holder.groupName.setText(listitems.get(position).GroupName);
       holder.group.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context,CreateStudentGroup.class);
               intent.putExtra("GroupId",listitems.get(position).GroupId);
               intent.putExtra("GroupName",listitems.get(position).GroupName);
               intent.putExtra("GroupPhoto",listitems.get(position).GroupPhoto);
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }


    public class Holder extends RecyclerView.ViewHolder{
        ImageView groupphoto;
        TextView groupName;
        View group;
        public Holder(@NonNull View itemView) {
            super(itemView);
            groupphoto=itemView.findViewById(R.id.GroupPhoto);
           groupName=itemView.findViewById(R.id.GroupName);
           group=itemView;
        }
    }
}
