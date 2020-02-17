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

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.Holder> {
ArrayList<Library_object> listitems;
Context context;
Info info;
    Intent intent;
    int type;
    char types;
    RequestQueue requestQueue;
    public LibraryAdapter(ArrayList<Library_object> listitems, Context context,int type,char types) {
        this.listitems = listitems;
        this.context = context;
        requestQueue=Volley.newRequestQueue(context);
this.type=type;
this.types=types;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.library_gride,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
holder.textView.setText(listitems.get(position).name);
        info= (Info) context.getApplicationContext();
        Glide.with(context).load(listitems.get(position).photo).into(holder.imageView);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(context,Book_deatls.class);
                intent.putExtra("detals",listitems.get(position).detals);
                intent.putExtra("photo",listitems.get(position).photo);
                intent.putExtra("name",listitems.get(position).name);
                intent.putExtra("link",listitems.get(position).link);
                context.startActivity(intent);

            }
        });
        if(types=='s'){
switch (type){
    case 1:

        holder.popmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                MenuInflater inflater=popupMenu.getMenuInflater();
                inflater.inflate(R.menu.library1,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String url;

                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                            url="https://ehab01998.com/favBookSend.php";
                        }else{
                            url="http://ehab01998.com/favBookSend.php";

                        }

                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("true")) {
                                    Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,R.string.no_communcationInternet, Toast.LENGTH_SHORT).show();

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> params=new HashMap<String, String>();
                                String id=listitems.get(position).id;
                                String name=listitems.get(position).name.trim();
                                String details=listitems.get(position).detals.trim();
                                String photo=listitems.get(position).photo;
                                String link=listitems.get(position).link;
                                params.put("books_id",id);
                                params.put("books_name",name);
                                params.put("books_photo",photo);
                                params.put("book_details",details);
                                params.put("books_link",link);
                                params.put("type","0");
                                params.put("department_id",info.getDepartment());
                                params.put("code",info.getId());
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
    case 0:
        holder.popmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                MenuInflater inflater=popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popmenu_general2,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String url;

                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                            url="https://ehab01998.com/favbookDelete.php?type=0&books_id="+listitems.get(position).id+"&code="+info.getId();
                        }else{
                            url="http://ehab01998.com/favbookDelete.php?type=0&books_id="+listitems.get(position).id+"&code="+info.getId();

                        }

                        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("true")){
                                    Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                    intent=new Intent(context,Library.class);
                                    intent.putExtra("fav","library");
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

}}else if(types=='d'){

            switch (type) {
                case 1:
                    holder.popmenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popupMenu = new PopupMenu(context,view);
                            MenuInflater inflater=popupMenu.getMenuInflater();
                            inflater.inflate(R.menu.library1,popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    String url;

                                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                        url="https://ehab01998.com/favBookSend.php";
                                    }else{
                                        url="http://ehab01998.com/favBookSend.php";

                                    }

                                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("true")) {
                                                Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context,error.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String,String> params=new HashMap<String, String>();
                                            String id=listitems.get(position).id;
                                            String name=listitems.get(position).name.trim();
                                            String details=listitems.get(position).detals.trim();
                                            String photo=listitems.get(position).photo;
                                            String link=listitems.get(position).link;
                                            params.put("books_id",id);
                                            params.put("books_name",name);
                                            params.put("books_photo",photo);
                                            params.put("book_details",details);
                                            params.put("books_link",link);
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

                case 0:
                    holder.popmenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popupMenu = new PopupMenu(context,view);
                            MenuInflater inflater=popupMenu.getMenuInflater();
                            inflater.inflate(R.menu.popmenu_general2,popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    String url;

                                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                        url="https://ehab01998.com/favbookDelete.php?type=1&books_id="+listitems.get(position).id+"&doctor_id="+info.doctor_id;
                                    }else{
                                        url="http://ehab01998.com/favbookDelete.php?type=1&books_id="+listitems.get(position).id+"&doctor_id="+info.doctor_id;

                                    }

                                    StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            if(response.equals("true")){
                                                Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                                intent=new Intent(context,DoctorLibrary.class);
                                                intent.putExtra("fav","library");
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
            imageView=itemView.findViewById(R.id.iv_library);
            textView=itemView.findViewById(R.id.tv_library);
            popmenu=itemView.findViewById(R.id.popmenu);
            this.view=itemView;
        }
    }
    public void updateList(ArrayList<Library_object> newList){
        listitems=new ArrayList<>();
        listitems.addAll(newList);
        notifyDataSetChanged();
    }
}
