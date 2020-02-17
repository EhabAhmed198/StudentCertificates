package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorLecGroupAdapter extends RecyclerView.Adapter<DoctorLecGroupAdapter.Holder> {
Context context;
ArrayList<DoctorLecGroups_Object> listitems;

    public DoctorLecGroupAdapter(Context context, ArrayList<DoctorLecGroups_Object> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.groups_lectures,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
     holder.textView.setText(listitems.get(position).name);
     holder.view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(context,DGroup_Post.class);
             intent.putExtra("name",listitems.get(position).name);
             intent.putExtra("group_id",listitems.get(position).id);
             context.startActivity(intent);
         }
     });
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
TextView textView;
View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_grops);
            this.view=itemView;
        }
    }
}
