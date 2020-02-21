package com.ehabahmed.studentcertificate;


import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.ehabahmed.studentcertificate.database.AppDatabase;
import com.ehabahmed.studentcertificate.database.NewsEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class News extends Fragment {
    RecyclerView recyclerView;
    ArrayList<object_News> listitems;
    String url;
    TextView nonew;
    RequestQueue requestQueue;
    NewsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    boolean inscrolling = false;
    int current, out, total;
    int userpage = 1;
    ProgressBar progressBar;
    String protocal;
    String ivname;
    String type = "online";
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recycleview);
        nonew = view.findViewById(R.id.nonew);
        nonew.setVisibility(View.INVISIBLE);
        requestQueue = Volley.newRequestQueue(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listitems = new ArrayList<object_News>();
        recyclerView.getRecycledViewPool().setMaxRecycledViews(R.id.vv_new, 0);

        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        adapter = new NewsAdapter(getContext(), listitems, 0, 3, true);
        recyclerView.setAdapter(adapter);
        getdata(userpage);

        loadonline();
        return view;
    }




    private void loadonline() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    inscrolling = true;


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                current = linearLayoutManager.getChildCount();
                out = linearLayoutManager.findFirstVisibleItemPosition();
                total = linearLayoutManager.getItemCount();
                if (inscrolling && (current + out) == total) {
                    inscrolling = false;
                    userpage++;
                    if (type.equals("online"))
                        getdata(userpage);
                }
            }
        });
    }


    private void getdata(int userpage) {
        progressBar.setVisibility(View.VISIBLE);
        String url;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/ShowNewsArray.php?page=" + userpage;
            protocal = "https://";
        } else {
            url = "http://ehab01998.com/ShowNewsArray.php?page=" + userpage;
            protocal = "http://";

        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray arrayRequest = response.getJSONArray("News");
                    for (int i = 0; i < arrayRequest.length(); i++) {
                        JSONObject object = arrayRequest.getJSONObject(i);
                        int news_id = object.getInt("news_id");
                        String news_text = object.getString("news_text");
                        String news_detals = object.getString("news_detals");
                        String news_ivname = object.getString("news_ivname");
                        String news_type = object.getString("news_type");
                        String data_time = object.getString("data_time");
                        ivname = protocal + news_ivname;

                        listitems.add(new object_News(news_id, news_text, news_detals, ivname, news_type, data_time));
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            final AppDatabase appDatabase = AppDatabase.getInstance(getContext());
                            appDatabase.taskDao().deleteNews();
                            for (int i = 0; i < Math.min(listitems.size(), 8); i++) {


                                final int finalI1 = i;

                                if (!listitems.get(i).news_type.equals("none")) {
                                    try {
                                        FileLoader.with(getContext())
                                                .load(listitems.get(i).news_ivname).fromDirectory("StudentCertificate/image", FileLoader.DIR_EXTERNAL_PUBLIC)
                                                .asFile(new FileRequestListener<File>() {
                                                    @Override
                                                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                                        File file = response.getBody();


                                                        load(file.getAbsolutePath(), finalI1, appDatabase, listitems);


                                                    }


                                                    @Override
                                                    public void onError(FileLoadRequest request, Throwable t) {

                                                    }
                                                });
                                    } catch (Exception e) {
                                    }
                                } else {
                                    load("none", finalI1, appDatabase, listitems);

                                }


                            }


                        }
                    }.start();


                    adapter.notifyDataSetChanged();
                    nonew.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                type = "offline";
                nonew.setVisibility(View.VISIBLE);
                try {


                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            final ArrayList<object_News> object_news = new ArrayList<>();
                            try {
                                final List<NewsEntity> list = AppDatabase.getInstance(getContext()).taskDao().getAllNews();

                                for (int i = (list.size() - 1); i >= 0; i--) {
                                    NewsEntity entity = list.get(i);
                                    object_news.add(new object_News(entity.getId(),
                                            entity.getNews_text(), entity.getNews_detals(),
                                            entity.getNews_ivname(), entity.getNews_type(),
                                            entity.getData_time()));
                                }

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        adapter = new NewsAdapter(getContext(), object_news, 0, 3, true);
                                        recyclerView.setAdapter(adapter);
                                        if (object_news.size() != 0)
                                            nonew.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                            } catch (Exception e) {
                            }

                        }
                    }.start();

                } catch (Exception e) {
                }

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private void load(final String paths, final int i, final AppDatabase appDatabase, final ArrayList<object_News> listitems) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                object_News object_news = listitems.get(i);
                appDatabase.taskDao().insertNews(new NewsEntity(object_news.news_id, object_news.news_text,
                        object_news.news_detals,
                        paths, object_news.news_type, object_news.data_time));
            }
        }.start();


    }


}
