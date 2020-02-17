package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public final class InboxAdabter extends RecyclerView.Adapter<InboxAdabter.InHolder> {

    private List<SendForm> list;
    private Context context;

    public InboxAdabter(List<SendForm> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public InHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_holder, parent, false);
        return new InHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InHolder holder, int position) {
        CardView linearLayout = holder.linearLayout;
        TextView title = linearLayout.findViewById(R.id.inbox_title);
        final SendForm sendForm = list.get(position);
        String utitle=sendForm.getTitle().substring(0,1).toUpperCase()+sendForm.getTitle().substring(1);
        title.setText((utitle + ""));
        TextView name = linearLayout.findViewById(R.id.inbox_name);
        name.setText((sendForm.getSender_Name() + ""));
        TextView time = linearLayout.findViewById(R.id.inbox_time);
        time.setText((sendForm.getTime() + ""));
        ImageView receiver_image = linearLayout.findViewById(R.id.inbox_image);
        String Photo = sendForm.getSender_image();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Glide.with(context).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load(Photo)
                    .into(receiver_image);
        } else {
            Glide.with(context).asBitmap()
                    .placeholder(R.drawable.iv_profile).circleCrop().load(Photo)
                    .into(receiver_image);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageReader.class);
                intent.putExtra("id", sendForm.getSender_id());
                intent.putExtra("name", sendForm.getSender_Name());
                intent.putExtra("image", sendForm.getSender_image());
                intent.putExtra("message", sendForm.getMessage());
                intent.putExtra("title", sendForm.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InHolder extends RecyclerView.ViewHolder {
        CardView linearLayout;

        public InHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (CardView) itemView;
        }
    }
}
