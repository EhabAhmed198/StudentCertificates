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

public class ManyCoursesAdapter extends RecyclerView.Adapter<ManyCoursesAdapter.Holder> {
    ArrayList<ManyCoursesOne_object> listitems;
    Context context;
    Intent intent;
    public ManyCoursesAdapter(ArrayList<ManyCoursesOne_object> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.manycourses_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
    holder.manycourses.setText(listitems.get(position).name);
    holder.view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
      intent=new Intent(context,Show_Course.class);
            intent.putExtra("link",listitems.get(position).link);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    });

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }


    public class Holder extends RecyclerView.ViewHolder{

     TextView manycourses;
     View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            manycourses=itemView.findViewById(R.id.manycourses);
            this.view=itemView;
        }
    }

}
