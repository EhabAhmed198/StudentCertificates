package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.Holder> {
    ArrayList<object_post_group> listitem = new ArrayList<object_post_group>();
    Context context;
    Intent intent;

    String dstype;
    public GroupsAdapter(ArrayList<object_post_group> listitem, Context context,String type) {
        this.listitem = listitem;
        this.context = context;
        this.dstype=type;
    }

    @Override
    public int getItemViewType(int position) {

        switch (listitem.get(position).type) {
            case "timage":
                return 0;

            case "video":
                return 1;
            case "file":
                return 3;
            case "none":
                return 2;
            case "image":
                return 4;

        }


        return -1;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new Holder(LayoutInflater.from(context).inflate(R.layout.image_group, parent, false));
        else if (viewType == 1)
            return new Holder(LayoutInflater.from(context).inflate(R.layout.video_group, parent, false));
        else if (viewType == 2)
            return new Holder(LayoutInflater.from(context).inflate(R.layout.textcardview, parent, false));
        else if (viewType == 3) {
            return new Holder(LayoutInflater.from(context).inflate(R.layout.filegroup, parent, false));

        } else if (viewType == 4) {
            return new Holder(LayoutInflater.from(context).inflate(R.layout.dimagecard, parent, false));
        }

        return
                null;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        Info info = (Info) context.getApplicationContext();

        switch (listitem.get(position).type) {
            case "timage":
                Glide.with(context).load(listitem.get(position).pvf_name).into(holder.imageView);
                holder.textView.setText(listitem.get(position).text);

                holder.Nddata_time.setText(listitem.get(position).data_time.toUpperCase());

                holder.Idoctorname.setText(info.getDoctor_name());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        intent = new Intent(context, post_detals.class);
                        intent.putExtra("gpost_detals", listitem.get(position).text);
                        intent.putExtra("gpost_pvf_name", listitem.get(position).pvf_name);
                        context.startActivity(intent);

                    }
                });
                getnumbercomment(holder,listitem.get(position).id);
                holder.timage_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
               holder.addcomment.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       intent=new Intent(context,Comments.class);
                       intent.putExtra("postid",listitem.get(position).id);
                       intent.putExtra("type",dstype);
                       context.startActivity(intent);

                   }
               });

                break;
            case "video":

                holder.vv_new.setVideoURI(Uri.parse(listitem.get(position).pvf_name));
                holder.vv_new.requestFocus();
                try {
                    holder.tv_new.setText(listitem.get(position).text);
                    holder.Idoctorname.setText(info.getDoctor_name());
                }catch (Exception e){}

                holder.vv_new.start();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, Show_video.class);
                        intent.putExtra("video", listitem.get(position).pvf_name);
                        context.startActivity(intent);
                    }
                });
                getnumbercomment(holder,listitem.get(position).id);
                holder.timage_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                holder.addcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                break;


            case "file":
                if (!listitem.get(position).text.equals(""))
                    holder.textView.setText(listitem.get(position).text);
                else {
                    holder.textView.setHeight(0);
                    holder.textView.setPadding(5, 5, 5, 5);
                }

                holder.Idoctorname.setText(info.getDoctor_name());
                String type = listitem.get(position).pvf_name.substring(listitem.get(position).pvf_name.lastIndexOf("."));
                String data_time = listitem.get(position).data_time;


                holder.filetype.setText(data_time.toUpperCase());
                getnumbercomment(holder,listitem.get(position).id);
                holder.timage_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                holder.addcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                switch (type) {

                    case ".pdf":
                        holder.imageView.setBackgroundResource(R.drawable.pdf);
                        break;

                    case ".doc":
                        holder.imageView.setBackgroundResource(R.drawable.doc);
                        break;

                    case ".docx":
                        holder.imageView.setBackgroundResource(R.drawable.docx);
                        break;
                    case ".xlsx":
                        holder.imageView.setBackgroundResource(R.drawable.xlsx);
                        break;
                    case ".pptx":
                        holder.imageView.setBackgroundResource(R.drawable.pptx);
                        break;
                    case ".ppt":
                        holder.imageView.setBackgroundResource(R.drawable.ppt);
                        break;


                }



                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, Show_files.class);
                        intent.putExtra("file", listitem.get(position).pvf_name);
                        context.startActivity(intent);
                    }
                });

            holder.dispopmenu.setVisibility(View.INVISIBLE);
                break;


            case "none":

                holder.textonly.setText(listitem.get(position).text);
                holder.Nddata_time.setText(listitem.get(position).data_time.toUpperCase());
                holder.Idoctorname.setText(info.getDoctor_name());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, post_detals.class);
                        intent.putExtra("gpost_detals", listitem.get(position).text);
                        context.startActivity(intent);
                    }
                });

                getnumbercomment(holder,listitem.get(position).id);
                holder.timage_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                holder.addcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                break;


            case "image":
                Glide.with(context).load(listitem.get(position).pvf_name).into(holder.imageView);
                holder.Idoctorname.setText(info.getDoctor_name());
                holder.Nddata_time.setText(listitem.get(position).data_time.toUpperCase());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(context, Show_image.class);
                        intent.putExtra("news_ivname", listitem.get(position).pvf_name);
                        context.startActivity(intent);
                    }
                });
                getnumbercomment(holder,listitem.get(position).id);
                holder.timage_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                holder.addcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(context,Comments.class);
                        intent.putExtra("postid",listitem.get(position).id);
                        intent.putExtra("type",dstype);
                        context.startActivity(intent);

                    }
                });
                break;

        }

    }



    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        //image_group
        ImageView imageView;
        TextView textView, Idoctorname, filetype, Nddata_time;

        View view;

        //video_group
        VideoView vv_new;
        TextView tv_new;
        //Text_group
        TextView textonly;

        TextView timage_comment;
        Button addcomment;
 ImageView dispopmenu;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imge_group);
            textView = itemView.findViewById(R.id.text_group);
            vv_new = itemView.findViewById(R.id.vv_new);
            Idoctorname = itemView.findViewById(R.id.doctorname);
            tv_new = itemView.findViewById(R.id.news_name);
            filetype = itemView.findViewById(R.id.filetype);
            Nddata_time = itemView.findViewById(R.id.data_time);
            textonly = itemView.findViewById(R.id.textonly);
            timage_comment=itemView.findViewById(R.id.image_group_comment);
            dispopmenu=itemView.findViewById(R.id.popmenu);
            addcomment=itemView.findViewById(R.id.addcomment);
            this.view = itemView;
        }
    }
    private void getnumbercomment(final Holder holder,String post_id) {
       String url="http://www.ehab01998.com/showComments.php?post_id="+post_id;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Comments");
                  holder.timage_comment.setText(array.length()+"");



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        Volley.newRequestQueue(context).add(request);


    }

}