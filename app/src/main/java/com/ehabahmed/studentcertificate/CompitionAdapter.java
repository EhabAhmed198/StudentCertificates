package com.ehabahmed.studentcertificate;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompitionAdapter extends RecyclerView.Adapter<CompitionAdapter.Holder> {
Context context;
ArrayList<Compition_Object> listitems;
RequestQueue requestQueue;
Info info;
int type;
    Intent intent;
    char types;
    public CompitionAdapter(Context context, ArrayList<Compition_Object> listitems,int type,char types) {
        this.context = context;
        this.listitems = listitems;
        requestQueue= Volley.newRequestQueue(context);
        info= (Info) context.getApplicationContext();
        this.type=type;
        this.types=types;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.compition_gride,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Glide.with(context).load(listitems.get(position).image).into(holder.imageView);
        holder.textView.setText(listitems.get(position).name);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           intent=new Intent(context,Compition_details.class);

            intent.putExtra("compition_name",listitems.get(position).name);
            intent.putExtra("compition_image",listitems.get(position).image);
            intent.putExtra("compition_detals",listitems.get(position).details);
            context.startActivity(intent);
            }
        });
        if(types=='s'){
        switch (type){

            case 0:
        holder.popmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(context,view);
                MenuInflater inflater=popupMenu.getMenuInflater();
                inflater.inflate(R.menu.compitation,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String url;
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                            url="https://ehab01998.com/favcomptitionSend.php";
                        }else{
                            url="http://ehab01998.com/favcomptitionSend.php";

                        }
                        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("true")) {
                                    Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", listitems.get(position).id);
                                params.put("name", listitems.get(position).name);
                                params.put("image", listitems.get(position).image);
                                params.put("details", listitems.get(position).details);
                                params.put("type", "0");
                                params.put("department", info.getDepartment());
                                params.put("code", info.getId());
                                return params;
                            }

                        };
                        requestQueue.add(request);
                        return false;
                    }
                });

                popupMenu.show();

            }
        });
        break;
            case 1:
                holder.popmenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu=new PopupMenu(context,view);
                        MenuInflater inflater=popupMenu.getMenuInflater();
                        inflater.inflate(R.menu.popmenu_general2,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                String url;
                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                    url="https://ehab01998.com/favcomptitionDelete.php?id="+listitems.get(position).id+"&type=0&code="+info.getId();
                                }else{
                                    url="http://ehab01998.com/favcomptitionDelete.php?id="+listitems.get(position).id+"&type=0&code="+info.getId();

                                }

                               StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                   @Override
                                   public void onResponse(String response) {
                                       if(response.equals("true")){
                                           Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                          intent=new Intent(context,Compition.class);
                                           intent.putExtra("fav","comptition");
                                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                           context.startActivity(intent);
                                       }

                                   }
                               }, new Response.ErrorListener() {
                                   @Override
                                   public void onErrorResponse(VolleyError error) {

                                   }
                               });

                                requestQueue.add(request);


                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });


                break;}}else if(types=='d'){

            switch (type){
                case 0:
                    holder.popmenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popupMenu=new PopupMenu(context,view);
                            MenuInflater inflater=popupMenu.getMenuInflater();
                            inflater.inflate(R.menu.compitation,popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    String url;
                                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                        url="https://ehab01998.com/favcomptitionSend.php";
                                    }else{
                                        url="http://ehab01998.com/favcomptitionSend.php";

                                    }

                                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("true")) {
                                                Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String,String> params=new HashMap<>();
                                            params.put("id",listitems.get(position).id);
                                            params.put("name",listitems.get(position).name);
                                            params.put("image",listitems.get(position).image);
                                            params.put("details",listitems.get(position).details);
                                            params.put("type","1");
                                            params.put("doctor_id",info.getDoctor_id());
                                            return params;
                                        }
                                    };
                                    requestQueue.add(request);
                                    return false;
                                }
                            });

                            popupMenu.show();

                        }
                    });
                    break;
                case 1:
                    holder.popmenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popupMenu=new PopupMenu(context,view);
                            MenuInflater inflater=popupMenu.getMenuInflater();
                            inflater.inflate(R.menu.popmenu_general2,popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    String url;
                                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                        url="https://ehab01998.com/favcomptitionDelete.php?id="+listitems.get(position).id+"&type=1&doctor_id="+info.getDoctor_id();
                                    }else{
                                        url="http://ehab01998.com/favcomptitionDelete.php?id="+listitems.get(position).id+"&type=1&doctor_id="+info.getDoctor_id();

                                    }

                                    StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.equals("true")){
                                                Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                                intent=new Intent(context,DoctorCompetition.class);
                                                intent.putExtra("fav","comptition");
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                context.startActivity(intent);
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });

                                    requestQueue.add(request);


                                    return false;
                                }
                            });
                            popupMenu.show();
                        }
                    });


                    break;
            }




        }

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView,popmenu;
        TextView textView;
        View view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_compition);
            textView=itemView.findViewById(R.id.tv_compition);
            this.view=itemView;
            popmenu=itemView.findViewById(R.id.popmenu);

        }
    }

    public void updateList(ArrayList<Compition_Object> newList){
        listitems=new ArrayList<>();
        listitems.addAll(newList);
        notifyDataSetChanged();
    }

}
