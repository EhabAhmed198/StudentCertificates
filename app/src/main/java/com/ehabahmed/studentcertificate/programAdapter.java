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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class programAdapter extends RecyclerView.Adapter<programAdapter.Holder> {
    Intent intent;
    Context context;
ArrayList<Library_object> listitems;     //programm_object==Library_object

    public programAdapter(Context context, ArrayList<Library_object> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.program,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.textView.setText(listitems.get(position).name);
        Glide.with(context).load(listitems.get(position).photo).into(holder.imageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(context,Book_deatls.class);  //program_deatls==book_deatls
                intent.putExtra("type","program");
                intent.putExtra("detals",listitems.get(position).detals);
                intent.putExtra("photo",listitems.get(position).photo);
                intent.putExtra("name",listitems.get(position).name);
                intent.putExtra("link",listitems.get(position).link);
                context.startActivity(intent);
            }
        });

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
            imageView=itemView.findViewById(R.id.iv_program);
            textView=itemView.findViewById(R.id.tv_program);
            view=itemView;
        }
    }
    public void updateList(ArrayList<Library_object> newList){
        listitems=new ArrayList<>();
        listitems.addAll(newList);
        notifyDataSetChanged();
    }
}
