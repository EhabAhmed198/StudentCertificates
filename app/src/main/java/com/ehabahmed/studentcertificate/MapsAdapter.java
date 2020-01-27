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

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.Holder> {
Context context;
ArrayList<MapsObject> listitems;

    Intent intent;
    public MapsAdapter(Context context, ArrayList<MapsObject> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.map_gride,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.textView.setText(listitems.get(position).name);
        Glide.with(context).load(listitems.get(position).Image).into(holder.imageView);
       holder.view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               switch (listitems.get(position).id){
                   case "1":
                        intent=new Intent(context,Maps.class);
                       intent.putExtra("type","m");
                       context.startActivity(intent);
                       break;
                   case "2":
                   intent=new Intent(context,Maps.class);
                       intent.putExtra("type","b1");
                       context.startActivity(intent);

                       break;
                   case "3":
                       intent=new Intent(context,Maps.class);
                       intent.putExtra("type","b2");
                       context.startActivity(intent);
                       break;
                   case "4":
                       intent=new Intent(context,Maps.class);
                       intent.putExtra("type","b3");
                       context.startActivity(intent);
                       break;



                   case "5":
                       intent=new Intent(context,Maps.class);
                       intent.putExtra("type","b5");
                       context.startActivity(intent);
                       break;

                   case "6":
                       intent=new Intent(context,Maps.class);
                       intent.putExtra("type","b7");
                       context.startActivity(intent);
                       break;
                   case "7":
                       intent=new Intent(context,Maps.class);
                       intent.putExtra("type","ATEC");
                       context.startActivity(intent);
                       break;



               }

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
        imageView=itemView.findViewById(R.id.mapImage);
        textView=itemView.findViewById(R.id.tvMap);
        this.view=itemView;
    }
}

}
