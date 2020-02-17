package com.ehabahmed.studentcertificate;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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

import static android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED;
import static android.content.Context.DOWNLOAD_SERVICE;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.Holder> {
Context context;
ArrayList<Exama_Object> listitems;

int type;
Info info;
    Intent intent;
    RequestQueue requestQueue;
    char types;
    public ExamsAdapter(Context context, ArrayList<Exama_Object> listitems,int type,char types) {
        this.context = context;
        this.listitems = listitems;
        this.type=type;
        this.types=types;
        requestQueue=Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.exams_gride,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        Glide.with(context).load(listitems.get(position).Image).into(holder.imageView);
        holder.textView.setText(listitems.get(position).name);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent=new Intent(context,Show_Result.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //exams_image=result_image
                intent.putExtra("result",listitems.get(position).Image);
              context.startActivity(intent);
            }
        });
        info= (Info) context.getApplicationContext();
        holder.popmenu.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(types=='s'){
                switch (type) {
                    case 1:
                    final PopupMenu popup = new PopupMenu(context, view);

                    MenuInflater inflater = popup.getMenuInflater();

                    inflater.inflate(R.menu.popmenu_exams1, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                       if(menuItem.getItemId()==R.id.favexams){
                             String url;

                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                 url = "https://ehab01998.com/favExamsSend.php";
                             } else {
                                 url = "http://ehab01998.com/favExamsSend.php";

                             }


                             StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {
                                     Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                                 }
                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {
                                     Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                                 }
                             }) {
                                 @Override
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     HashMap<String, String> params = new HashMap<String, String>();
                                     params.put("id", listitems.get(position).id);
                                     params.put("name", listitems.get(position).name);
                                     params.put("image", listitems.get(position).Image);
                                     params.put("type", "0");
                                     params.put("term", listitems.get(position).toString());
                                     params.put("level", info.getLevel());
                                     params.put("code", info.getId());
                                     params.put("department", info.getDepartment());

                                     return params;
                                 }
                             };
                             requestQueue.add(request);



                         }else if(menuItem.getItemId()==R.id.downloadExam){

                           Uri uri=Uri.parse(listitems.get(position).Image);

                           Environment
                                   .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                   .mkdirs();
                           DownloadManager downloadManager=(DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);

                           downloadManager.enqueue(new DownloadManager.Request(uri)
                                   .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                           DownloadManager.Request.NETWORK_MOBILE)
                                   .setAllowedOverRoaming(false)
                                   .setTitle(listitems.get(position).name)

                                   .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                   .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                           listitems.get(position).name));


                       }

                            return false;}
                    });

                    popup.show();

                    break;
                    case 0:
                     holder.popmenu.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             PopupMenu popupMenu=new PopupMenu(context,view);
                             popupMenu.getMenuInflater().inflate(R.menu.popmenu_general2,popupMenu.getMenu());
                             popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                 @Override
                                 public boolean onMenuItemClick(MenuItem menuItem) {
                                     String url;

                                     if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                         url="https://ehab01998.com/favExamsDelete.php?type=0&id="+listitems.get(position).id+"&code="+info.getId();
                                     }else{
                                         url="http://ehab01998.com/favExamsDelete.php?type=0&id="+listitems.get(position).id+"&code="+info.getId();

                                     }

                                     StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                         @Override
                                         public void onResponse(String response) {
                                             if(response.equals("true")){
                                                 Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                               intent=new Intent(context,Exams.class);
                                                 intent.putExtra("fav","exams");
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
                            final PopupMenu popup = new PopupMenu(context, view);

                            MenuInflater inflater = popup.getMenuInflater();

                            inflater.inflate(R.menu.popmenu_exams1, popup.getMenu());
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    if (menuItem.getItemId() == R.id.favexams) {
                                        String url;

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                            url = "https://ehab01998.com/favExamsSend.php";
                                        } else {
                                            url = "http://ehab01998.com/favExamsSend.php";

                                        }


                                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                HashMap<String, String> params = new HashMap<String, String>();
                                                params.put("id", listitems.get(position).id);
                                                params.put("name", listitems.get(position).name);
                                                params.put("image", listitems.get(position).Image);
                                                params.put("type", "1");
                                                params.put("term", listitems.get(position).toString());
                                                params.put("doctor_id", info.getDoctor_id());


                                                return params;
                                            }
                                        };
                                        requestQueue.add(request);



                                    }else if(menuItem.getItemId()==R.id.downloadExam){

                                        Uri uri=Uri.parse(listitems.get(position).Image);

                                        Environment
                                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                                .mkdirs();
                                        DownloadManager downloadManager=(DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);

                                        downloadManager.enqueue(new DownloadManager.Request(uri)
                                                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                                        DownloadManager.Request.NETWORK_MOBILE)
                                                .setAllowedOverRoaming(false)
                                                .setTitle(listitems.get(position).name)

                                                .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                                        listitems.get(position).name));
                                    }

                                    return false;
                                }
                            });

                            popup.show();
                            break;


                        case 0:
                            holder.popmenu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    PopupMenu popupMenu=new PopupMenu(context,view);
                                    popupMenu.getMenuInflater().inflate(R.menu.popmenu_general2,popupMenu.getMenu());
                                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem menuItem) {
                                            String url;

                                            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                                url="https://ehab01998.com/favExamsDelete.php?type=1&id="+listitems.get(position).id+"&doctor_id="+info.getDoctor_id();
                                            }else{
                                                url="http://ehab01998.com/favExamsDelete.php?type=1&id="+listitems.get(position).id+"&doctor_id="+info.getDoctor_id();

                                            }

                                            StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    if(response.equals("true")){
                                                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                                        intent=new Intent(context,DoctorExams.class);
                                                        intent.putExtra("fav","exams");
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
        });

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
            imageView=itemView.findViewById(R.id.iv_exams);
            textView=itemView.findViewById(R.id.tv_exams);
            popmenu=itemView.findViewById(R.id.popmenu);
            this.view=itemView;

        }
    }

public void updateList(ArrayList<Exama_Object> newList){
        listitems=new ArrayList<>();
        listitems.addAll(newList);
        notifyDataSetChanged();
}
}
