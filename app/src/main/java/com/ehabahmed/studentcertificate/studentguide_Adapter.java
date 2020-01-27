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

public class studentguide_Adapter extends RecyclerView.Adapter<studentguide_Adapter.Holder> {
    ArrayList<String>  listitems;
    Context context;
    Intent intent;
Info studuentInfo;
    public studentguide_Adapter(ArrayList<String> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.studentguide_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.textView.setText(listitems.get(position).toString());
        studuentInfo=(Info)context.getApplicationContext();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!listitems.get(position).toString().equals(context.getResources().getString(R.string.lland1))
                && !listitems.get(position).toString().equals(context.getResources().getString(R.string.lland2)))
                {
                    Intent intent=new Intent(context,Studentguide12.class);
                        if(listitems.get(position).toString().equals(context.getResources().getString(R.string.land1)))
                            studuentInfo.setGhange_level("1");
                            else if(listitems.get(position).toString().equals(context.getResources().getString(R.string.land2)))
                            studuentInfo.setGhange_level("2");
                                else if(listitems.get(position).toString().equals(context.getResources().getString(R.string.land3)))
                            studuentInfo.setGhange_level("3");
                                    else if(listitems.get(position).toString().equals(context.getResources().getString(R.string.land4)))
                             studuentInfo.setGhange_level("4");
                               context.startActivity(intent);

                }else if(listitems.get(position).toString().equals(context.getResources().getString(R.string.lland1))){
                intent=new Intent(context,Show_image2.class);
                       intent.putExtra("part","1");
                       context.startActivity(intent);

                }else if(listitems.get(position).toString().equals(context.getResources().getString(R.string.lland2))){
                  intent=new Intent(context,Show_image2.class);
                    intent.putExtra("part","2");
                    context.startActivity(intent);

                }}
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
            textView=itemView.findViewById(R.id.studentguide_name);
            this.view=itemView;
        }
    }
}
