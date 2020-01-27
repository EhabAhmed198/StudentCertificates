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


public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.Holder> {
ArrayList<Courses_object> listitems;
Context context;
int type;
Info info;
char types;
    Intent intent;
    RequestQueue requestQueue;
    public CoursesAdapter(ArrayList<Courses_object> listitems, Context context,int type,char types) {
        this.listitems = listitems;
        this.context = context;
        this.type=type;
        this.types=types;
        requestQueue=Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.courses_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Glide.with(context).load(listitems.get(position).image).into(holder.iv_courses);
        info= (Info) context.getApplicationContext();
        holder.tv_courses.setText(listitems.get(position).name);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              intent=new Intent(context,OneCourse.class);
                intent.putExtra("id",listitems.get(position).id);
                context.startActivity(intent);
            }
        });

        holder.popmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(types=='s'){
       if(type==1) {
           PopupMenu popup = new PopupMenu(context, view);

           MenuInflater inflater = popup.getMenuInflater();

           inflater.inflate(R.menu.popmenu_courses1, popup.getMenu());
           popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
               @Override
               public boolean onMenuItemClick(MenuItem menuItem) {
                   String url;
                   if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                      url="https://ehab01998.com/favCourseSend.php";
                   }else{

                              url="http://ehab01998.com/favCourseSend.php";

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
                           Toast.makeText(context, R.string.no_communcationInternet, Toast.LENGTH_SHORT).show();

                       }
                   }){
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           HashMap<String,String> params=new HashMap<>();
                           params.put("courses_type_id",listitems.get(position).id);
                           params.put("courses_type_name",listitems.get(position).name);
                           params.put("course_image",listitems.get(position).image);
                           params.put("type","0");
                           params.put("code",info.getId());
                           params.put("department_id",info.getDepartment());
                           return params;
                       }
                   }
                   ;
                   requestQueue.add(request);


                   return false;

               }
           });
           popup.show();
       }else if(type==0) {
           PopupMenu popup = new PopupMenu(context, view);
           popup.getMenuInflater().inflate(R.menu.popmenu_general2, popup.getMenu());
           popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
               @Override
               public boolean onMenuItemClick(MenuItem menuItem) {
                   String url;
                   if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                       url ="https://ehab01998.com/favcoursedelete.php?type=0&courses_type_id="+listitems.get(position).id+"&code="+info.getId();
                   }else{
                       url ="http://ehab01998.com/favcoursedelete.php?type=0&courses_type_id="+listitems.get(position).id+"&code="+info.getId();

                   }

                   StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           if (response.equals("true")) {
                               Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                               intent = new Intent(context, Courses.class);
                               intent.putExtra("fav", "course");
                               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
           popup.show();

       }

       }else if(types=='d'){

                    if(type==1) {
                        PopupMenu popup = new PopupMenu(context, view);

                        MenuInflater inflater = popup.getMenuInflater();

                        inflater.inflate(R.menu.popmenu_courses1, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                String url;
                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                    url="https://ehab01998.com/favCourseSend.php";
                                }else{

                                    url="http://ehab01998.com/favCourseSend.php";

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
                                        Toast.makeText(context, R.string.no_communcationInternet, Toast.LENGTH_SHORT).show();

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String,String> params=new HashMap<>();
                                        params.put("courses_type_id",listitems.get(position).id);
                                        params.put("courses_type_name",listitems.get(position).name);
                                        params.put("course_image",listitems.get(position).image);
                                        params.put("type","1");
                                        params.put("doctor_id",info.getDoctor_id());
                                        return params;
                                    }
                                };
                                requestQueue.add(request);


                                return false;

                            }
                        });
                        popup.show();
                    }else if(type==0){

                        PopupMenu popup = new PopupMenu(context, view);
                        popup.getMenuInflater().inflate(R.menu.popmenu_general2, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                String url;
                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                    url = "https://ehab01998.com/favcoursedelete.php?type=1&courses_type_id="+listitems.get(position).id+"&doctor_id="+info.doctor_id;
                                }else{
                                    url = "http://ehab01998.com/favcoursedelete.php?type=1&courses_type_id="+listitems.get(position).id+"&doctor_id="+info.doctor_id;

                                }

                                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("true")) {
                                            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                            intent = new Intent(context, DoctorCourses.class);
                                            intent.putExtra("fav", "course");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        popup.show();








                    }

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
ImageView iv_courses,popmenu;
TextView tv_courses;
View view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            iv_courses=itemView.findViewById(R.id.iv_courses);
            tv_courses=itemView.findViewById(R.id.tv_courses);
            this.view=itemView;
            popmenu=itemView.findViewById(R.id.popmenu);
        }
    }
    public void updateList(ArrayList<Courses_object> newList){
        listitems=new ArrayList<>();
        listitems.addAll(newList);
        notifyDataSetChanged();
    }
}
