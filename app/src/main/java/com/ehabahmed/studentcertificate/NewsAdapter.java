package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {
Context context;
ArrayList<object_News> listitems;
    Intent intent;
    int type,subtype;
    Info info;
    RequestQueue requestQueue;
    boolean state;
    public NewsAdapter(Context context, ArrayList<object_News> listitems,int type,int subtype,boolean state) {
        this.context = context;
        this.listitems = listitems;
        this.type=type;
        info= (Info) context.getApplicationContext();
        requestQueue= Volley.newRequestQueue(context);
        this.subtype=subtype;
        this.state=state;
    }

    @Override
    public int getItemViewType(int position) {

        switch (listitems.get(position).news_type){
            case "timage":
                return 0;
            case "image":
                return 4;
            case "video":
                return 1;
            case "file":
                return 3;
            case "none":
                return 2;
        }

       return -1;
    }

    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (type==1 || type==2) {
          if (viewType == 0)
              return new Holder(LayoutInflater.from(context).inflate(R.layout.rec_news, parent, false));
          else if (viewType == 1) {
              return new Holder(LayoutInflater.from(context).inflate(R.layout.rec_news2, parent, false));
          } else if (viewType == 2) {
              return new Holder(LayoutInflater.from(context).inflate(R.layout.textcaedview2, parent, false));
          } else if (viewType == 3) {
              Holder holder = new Holder(LayoutInflater.from(context).inflate(R.layout.filegroup2new, parent, false));
              holder.doctor_name.setHeight(0);
              holder.data_time.setPadding(0, 10, 0, 0);

              return holder;
          } else if (viewType == 4)
              return new Holder(LayoutInflater.from(context).inflate(R.layout.imagecard, parent, false));
      }

      else if(type==0){
          if (viewType == 0)
              return new Holder(LayoutInflater.from(context).inflate(R.layout.rec_news_default, parent, false));
          else if (viewType == 1) {
              return new Holder(LayoutInflater.from(context).inflate(R.layout.res_news2_default, parent, false));
          } else if (viewType == 2) {
              return new Holder(LayoutInflater.from(context).inflate(R.layout.textcardview_default, parent, false));
          } else if (viewType == 3) {
              Holder holder = new Holder(LayoutInflater.from(context).inflate(R.layout.filegroup_default, parent, false));
              holder.doctor_name.setHeight(0);
              holder.data_time.setPadding(0, 10, 0, 0);

              return holder;
          } else if (viewType == 4)
              return new Holder(LayoutInflater.from(context).inflate(R.layout.image_card_news, parent, false));

      }
        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        if(type==0) {

            switch (listitems.get(position).news_type) {
                case "timage":


                    holder.textView.setText(listitems.get(position).news_text);


                    holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());

                    Glide.with(context).load(listitems.get(position).news_ivname).into(holder.imageView);


                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            intent = new Intent(context, News_detals.class);
                            intent.putExtra("news_detals", listitems.get(position).news_detals);
                            intent.putExtra("news_ivname", listitems.get(position).news_ivname);
                            context.startActivity(intent);


                        }
                    });

                    break;
                case "video":
                    holder.VideoName.setText(listitems.get(position).news_text);
                    holder.vv_new.setVideoURI(Uri.parse(listitems.get(position).news_ivname));
                    holder.vv_new.requestFocus();
                    holder.vv_new.setZOrderOnTop(true);
                   holder.vv_new.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                       @Override
                       public void onPrepared(MediaPlayer mp) {
                           holder.PrVideo.setVisibility(View.INVISIBLE);
                           mp.setVolume(0f,0f);
                           mp.start();
                       }
                   });

                    holder.vv_new.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            mp.pause();
                            holder.PrVideo.setVisibility(View.INVISIBLE);
                            holder.VideoNoInternet.setVisibility(View.VISIBLE);
                            return true;
                        }
                    });



                  holder.vv_new.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                      @Override
                      public void onViewAttachedToWindow(View v) {
                          holder.VideoNoInternet.setVisibility(View.INVISIBLE);
                          holder.PrVideo.setVisibility(View.VISIBLE);
                          holder.vv_new.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                              @Override
                              public boolean onError(MediaPlayer mp, int what, int extra) {
                                  mp.pause();
                                  holder.PrVideo.setVisibility(View.INVISIBLE);
                                  holder.VideoNoInternet.setVisibility(View.VISIBLE);
                                  return true;
                              }
                          });


                      }

                      @Override
                      public void onViewDetachedFromWindow(View v) {


                      }
                  });




                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(context, Show_video.class);
                            intent.putExtra("video",listitems.get(position).news_ivname);
                            context.startActivity(intent);
                        }
                    });




                    break;
                case "file":
                    holder.textgroup.setText(listitems.get(position).news_text);

                    String type = listitems.get(position).news_ivname.substring(listitems.get(position).news_ivname.lastIndexOf("."));
                    holder.data_time.setText(listitems.get(position).data_time.toUpperCase());
                    switch (type) {

                        case ".pdf":
                            holder.imge_group.setBackgroundResource(R.drawable.pdf);
                            break;

                        case ".doc":
                            holder.imge_group.setBackgroundResource(R.drawable.doc);
                            break;

                        case ".docx":
                            holder.imge_group.setBackgroundResource(R.drawable.docx);
                            break;
                        case ".xlsx":
                            holder.imge_group.setBackgroundResource(R.drawable.xlsx);
                            break;
                        case ".pptx":
                            holder.imge_group.setBackgroundResource(R.drawable.pptx);
                            break;
                        case ".ppt":
                            holder.imge_group.setBackgroundResource(R.drawable.ppt);
                            break;


                    }

                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(context, Show_files.class);
                            intent.putExtra("file", listitems.get(position).news_ivname);
                            context.startActivity(intent);
                        }
                    });

                    break;
                case "none":

                    holder.textonly.setText(listitems.get(position).news_text);
                    holder.doctor_name.setPadding(0, 0, 0, 0);
                    holder.doctor_name.setHeight(0);
                    holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());

                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(context, post_detals.class);
                            intent.putExtra("vgpost_pvf_name","no");
                            intent.putExtra("gpost_detals", listitems.get(position).news_detals);
                            context.startActivity(intent);
                        }
                    });
                    break;
                case "image":


                    holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());

                    Glide.with(context).load(listitems.get(position).news_ivname).into(holder.imageView);
                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent = new Intent(context, Show_image.class);
                            intent.putExtra("news_ivname", listitems.get(position).news_ivname);
                            context.startActivity(intent);
                        }
                    });




                    break;
            }


        }else if(type==1){

            switch (listitems.get(position).news_type){
                case "timage":
                    holder.textView.setText(listitems.get(position).news_text);


                    holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());

                    Glide.with(context).load(listitems.get(position).news_ivname).into(holder.imageView);


                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            intent = new Intent(context, News_detals.class);
                            intent.putExtra("news_detals", listitems.get(position).news_detals);
                            intent.putExtra("news_ivname", listitems.get(position).news_ivname);
                            context.startActivity(intent);


                        }
                    });
                    break;
                case "video":
                    holder.VideoName.setText(listitems.get(position).news_text);
                    holder.vv_new.setVideoURI(Uri.parse(listitems.get(position).news_ivname));
                    holder.vv_new.requestFocus();

                    holder.vv_new.setZOrderOnTop(true);
                    holder.vv_new.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            holder.PrVideo.setVisibility(View.INVISIBLE);
                            mp.setVolume(0f,0f);

                            mp.start();
                        }
                    });
                    holder.vv_new.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            mp.pause();
                            holder.PrVideo.setVisibility(View.INVISIBLE);
                            holder.VideoNoInternet.setVisibility(View.VISIBLE);
                            return true;
                        }
                    });


                    holder.vv_new.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {
                            holder.VideoNoInternet.setVisibility(View.INVISIBLE);
                            holder.PrVideo.setVisibility(View.VISIBLE);
                            holder.vv_new.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                                @Override
                                public boolean onError(MediaPlayer mp, int what, int extra) {
                                    mp.pause();
                                    holder.PrVideo.setVisibility(View.INVISIBLE);
                                    holder.VideoNoInternet.setVisibility(View.VISIBLE);
                                    return true;
                                }
                            });


                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {


                        }
                    });
                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(context, Show_video.class);
                            intent.putExtra("video",listitems.get(position).news_ivname);
                            context.startActivity(intent);
                        }
                    });

                    break;
                case "file":
                    holder.textgroup.setText(listitems.get(position).news_text);
                    String type = listitems.get(position).news_ivname.substring(listitems.get(position).news_ivname.lastIndexOf("."));
                    holder.data_time.setText(listitems.get(position).data_time.toUpperCase());
                    switch (type) {

                        case ".pdf":
                            holder.imge_group.setBackgroundResource(R.drawable.pdf);
                            break;

                        case ".doc":
                            holder.imge_group.setBackgroundResource(R.drawable.doc);
                            break;

                        case ".docx":
                            holder.imge_group.setBackgroundResource(R.drawable.docx);
                            break;
                        case ".xlsx":
                            holder.imge_group.setBackgroundResource(R.drawable.xlsx);
                            break;
                        case ".pptx":
                            holder.imge_group.setBackgroundResource(R.drawable.pptx);
                            break;
                        case ".ppt":
                            holder.imge_group.setBackgroundResource(R.drawable.ppt);
                            break;


                    }

                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(context, Show_files.class);
                            intent.putExtra("file", listitems.get(position).news_ivname);
                            context.startActivity(intent);
                        }
                    });




                    break;
                case "none":

                    holder.textonly.setText(listitems.get(position).news_text);
                    holder.doctor_name.setPadding(0, 0, 0, 0);
                    holder.doctor_name.setHeight(0);
                    holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());

                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(context, post_detals.class);
                            intent.putExtra("gpost_detals", listitems.get(position).news_detals);
                            context.startActivity(intent);
                        }
                    });


                    break;
                case "image":
                    holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());
                    Glide.with(context).load(listitems.get(position).news_ivname).into(holder.imageView);
                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent = new Intent(context, Show_image.class);
                            intent.putExtra("news_ivname", listitems.get(position).news_ivname);
                            context.startActivity(intent);
                        }
                    });

                    break;
            }




           if(subtype==0) {
               holder.popmenu.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       PopupMenu popupMenu = new PopupMenu(context, view);
                       MenuInflater inflater = popupMenu.getMenuInflater();
                       inflater.inflate(R.menu.student_news, popupMenu.getMenu());
                       popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                           @Override
                           public boolean onMenuItemClick(MenuItem menuItem) {
                               String url;

                               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                   url = "https://ehab01998.com/favNewsSend.php";
                               }else{
                                   url = "http://ehab01998.com/favNewsSend.php";

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

                                   }
                               }) {

                                   @Override
                                   protected Map<String, String> getParams() throws AuthFailureError {
                                       HashMap<String, String> params = new HashMap<String, String>();
                                       params.put("news_id", String.valueOf(listitems.get(position).news_id));
                                       params.put("news_text", listitems.get(position).news_text);
                                       params.put("news_detals", listitems.get(position).news_detals);
                                       params.put("news_ivname", listitems.get(position).news_ivname);
                                       params.put("news_type", listitems.get(position).news_type);
                                       params.put("type","0");
                                       params.put("data_time", listitems.get(position).data_time);
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

           }else if(subtype==1){
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
                                   url="https://ehab01998.com/favNewsDelete.php?news_id="+listitems.get(position).news_id+"&type=0"+"&code="+info.getId();
                               }else{
                                   url="http://ehab01998.com/favNewsDelete.php?news_id="+listitems.get(position).news_id+"&type=0"+"&code="+info.getId();

                               }

                               StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                   @Override
                                   public void onResponse(String response) {

                                       if(response.equals("true")){
                                           Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                           intent=new Intent(context,FavNews.class);
                                           intent.putExtra("fav","news");
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













           }





        }else if(type==2){



            if (listitems.get(position).news_type.equals("timage")) {

                holder.textView.setText(listitems.get(position).news_text);



                holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());
                Glide.with(context).load(listitems.get(position).news_ivname).into(holder.imageView);


                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        intent = new Intent(context, News_detals.class);
                        intent.putExtra("news_detals", listitems.get(position).news_detals);
                        intent.putExtra("news_ivname", listitems.get(position).news_ivname);
                        context.startActivity(intent);


                    }
                });
            } else if (listitems.get(position).news_type.equals("video")) {
                try {
                    holder.VideoName.setText(listitems.get(position).news_text);
                }catch (Exception e){}
                holder.vv_new.setVideoURI(Uri.parse(listitems.get(position).news_ivname));
                holder.vv_new.requestFocus();

                holder.vv_new.setZOrderOnTop(true);
                holder.vv_new.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        holder.PrVideo.setVisibility(View.INVISIBLE);
                        mp.setVolume(0f,0f);
                        mp.start();
                    }
                });
                holder.vv_new.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        mp.pause();
                        holder.PrVideo.setVisibility(View.INVISIBLE);
                        holder.VideoNoInternet.setVisibility(View.VISIBLE);
                        return true;
                    }
                });


                holder.vv_new.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        holder.VideoNoInternet.setVisibility(View.INVISIBLE);
                        holder.PrVideo.setVisibility(View.VISIBLE);
                        holder.vv_new.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                mp.pause();
                                holder.PrVideo.setVisibility(View.INVISIBLE);
                                holder.VideoNoInternet.setVisibility(View.VISIBLE);
                                return true;
                            }
                        });


                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {


                    }
                });
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, Show_video.class);
                        intent.putExtra("video",listitems.get(position).news_ivname);
                        context.startActivity(intent);
                    }
                });
            } else if (listitems.get(position).news_type.equals("file")) {
                holder.textgroup.setText(listitems.get(position).news_text);
                String type = listitems.get(position).news_ivname.substring(listitems.get(position).news_ivname.lastIndexOf("."));
                holder.data_time.setText(listitems.get(position).data_time.toUpperCase());
                switch (type) {

                    case ".pdf":
                        holder.imge_group.setBackgroundResource(R.drawable.pdf);
                        break;

                    case ".doc":
                        holder.imge_group.setBackgroundResource(R.drawable.doc);
                        break;

                    case ".docx":
                        holder.imge_group.setBackgroundResource(R.drawable.docx);
                        break;
                    case ".xlsx":
                        holder.imge_group.setBackgroundResource(R.drawable.xlsx);
                        break;
                    case ".pptx":
                        holder.imge_group.setBackgroundResource(R.drawable.pptx);
                        break;
                    case ".ppt":
                        holder.imge_group.setBackgroundResource(R.drawable.ppt);
                        break;


                }

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, Show_files.class);
                        intent.putExtra("file", listitems.get(position).news_ivname);
                        context.startActivity(intent);
                    }
                });


            } else if (listitems.get(position).news_type.equals("none")) {
                holder.textonly.setText(listitems.get(position).news_text);
                holder.doctor_name.setPadding(0, 0, 0, 0);
                holder.doctor_name.setHeight(0);
                holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(context, post_detals.class);
                        intent.putExtra("gpost_detals", listitems.get(position).news_detals);
                        context.startActivity(intent);
                    }
                });


            } else if (listitems.get(position).news_type.equals("image")) {


                holder.Ndata_time.setText(listitems.get(position).data_time.toUpperCase());
                Glide.with(context).load(listitems.get(position).news_ivname).into(holder.imageView);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(context, Show_image.class);
                        intent.putExtra("news_ivname", listitems.get(position).news_ivname);
                        context.startActivity(intent);
                    }
                });

            }


            if(subtype==0) {
                holder.popmenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(context, view);
                        MenuInflater inflater = popupMenu.getMenuInflater();
                        inflater.inflate(R.menu.student_news, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                String url;

                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                                    url = "https://ehab01998.com/favNewsSend.php";
                                }else{
                                    url = "http://ehab01998.com/favNewsSend.php";

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

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> params = new HashMap<String, String>();
                                        params.put("news_id", String.valueOf(listitems.get(position).news_id));
                                        params.put("news_text", listitems.get(position).news_text);
                                        params.put("news_detals", listitems.get(position).news_detals);
                                        params.put("news_ivname", listitems.get(position).news_ivname);
                                        params.put("news_type", listitems.get(position).news_type);
                                        params.put("type","1");
                                        params.put("data_time", listitems.get(position).data_time);
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


            }else if(subtype==1){

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
                                    url="https://ehab01998.com/favNewsDelete.php?news_id="+listitems.get(position).news_id+"&type=1"+"&doctor_id="+info.getDoctor_id();
                                }else{
                                    url="http://ehab01998.com/favNewsDelete.php?news_id="+listitems.get(position).news_id+"&type=1"+"&doctor_id="+info.getDoctor_id();

                                }

                                StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if(response.equals("true")){
                                            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                            intent=new Intent(context,DFavNews.class);
                                            intent.putExtra("fav","news");
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


            }




        }


    }


    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        //new1
        ImageView imageView,popmenu;
        TextView textView;
        TextView Ndata_time;
        View view;

        //new2
        VideoView vv_new;
        TextView tv_new;
        //file
        TextView data_time,textgroup;
        ImageView imge_group;
        TextView doctor_name;
        //Text_group
        TextView textonly,VideoNoInternet,VideoName;
ProgressBar PrVideo;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.news_image);
            VideoName=itemView.findViewById(R.id.news_name);
            textView=itemView.findViewById(R.id.new_name);
            vv_new=itemView.findViewById(R.id.vv_new);
            tv_new=itemView.findViewById(R.id.news_name);
            data_time =itemView.findViewById(R.id.filetype);
            textgroup=itemView.findViewById(R.id.text_group);
            imge_group=itemView.findViewById(R.id.imge_group);
            doctor_name=itemView.findViewById(R.id.doctorname);
            Ndata_time=itemView.findViewById(R.id.data_time);
            textgroup=itemView.findViewById(R.id.text_group);
            textonly=itemView.findViewById(R.id.textonly);
            popmenu=itemView.findViewById(R.id.popmenu);
            PrVideo=itemView.findViewById(R.id.PrVideo);
            VideoNoInternet=itemView.findViewById(R.id.VideoNoInternet);
            this.view=itemView;
        }
    }


}
