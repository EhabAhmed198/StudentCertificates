package com.ehabahmed.studentcertificate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AppSettings extends RecyclerView.Adapter<AppSettings.Holder> {
ArrayList<setting_object> listitems;
Context context;
Intent intent;
char type;
public AppSettings(Context context,ArrayList<setting_object> listitems,char type){
    this.context=context;
    this.listitems=listitems;
    this.type=type;
}

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.setting_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {

        holder.name.setText(listitems.get(position).name);

        holder.imageView.setImageResource(listitems.get(position).Image);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type=='s'){
                switch (listitems.get(position).id){
                    case 1:
                        intent=new Intent(context, Privacy.class);
                        context.startActivity(intent);
                        break;
                    case 2:break;
                    case 3:break;
                    case 4:break;
                }
            }else if(type=='p'){
                    switch (listitems.get(position).id){
                        case 1:
                            intent=new Intent(context, Fingreprint.class);
                            int fingreprint=checksupport();
                            if(fingreprint==1) {
                                context.startActivity(intent);
                            }
                            else if(fingreprint==2){
                                AlertDialog.Builder dialog=new AlertDialog.Builder(context)
                                        .setTitle("Set Up Fingerprint")
                                        .setMessage("To use this features,you'll need to set up your fingerprint on your phone first.")
                                        .setPositiveButton("OK",null).setCancelable(true);
                                dialog.create().show();


                            }else {
                                AlertDialog.Builder dialog=new AlertDialog.Builder(context)
                                        .setTitle("Fingerprint Support")
                                        .setMessage("To use this features,you'll need a device Support fingerprint.")
                                        .setPositiveButton("OK",null).setCancelable(true);
                                dialog.create().show();
                            }
                            break;
                        case 2:break;
                        case 3:break;
                        case 4:break;
                    }

                }}
        });




    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }


    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview);
            name=itemView.findViewById(R.id.name);
            this.view=itemView;
        }
    }
    int checksupport() {
        BiometricManager biometricManager = BiometricManager.from(context);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                return 1;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                return 0;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                return 2;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                return 0;


        }
        return 0;
    }
}
