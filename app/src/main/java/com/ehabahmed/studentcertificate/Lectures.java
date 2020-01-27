package com.ehabahmed.studentcertificate;

import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Lectures extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Ngroups> listitems;
    Info studuentInfo;
    Intent intent;
    RequestQueue requestQueue;
TextView nogroup;
ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lectures, container, false);
        studuentInfo = (Info) getActivity().getApplicationContext();

        listitems = new ArrayList<Ngroups>();
        recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        requestQueue = Volley.newRequestQueue(getContext());
        nogroup=view.findViewById(R.id.no_group);
        nogroup.setVisibility(View.INVISIBLE);
        progressBar=view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        getdata();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.lectures));
    }



    public class groupsAdapter extends RecyclerView.Adapter<groupsAdapter.Holder> {
        ArrayList<Ngroups> listitems;
        Context context;

        public groupsAdapter(ArrayList<Ngroups> listitems, Context context) {
            this.listitems = listitems;
            this.context = context;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(context).inflate(R.layout.groups_lectures, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, final int position) {

            holder.ngroup.setText(listitems.get(position).ngroup);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getContext(), Group_post.class);
                    intent.putExtra("group_id", listitems.get(position).idgroup);
                    intent.putExtra("doctor_id", listitems.get(position).iddoctor);

                    studuentInfo.setGroup_name(listitems.get(position).ngroup);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listitems.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView ngroup;
            View view;

            public Holder(@NonNull View itemView) {
                super(itemView);
                ngroup = itemView.findViewById(R.id.tv_grops);
                this.view = itemView;
            }
        }
    }



    private void getdata() {
      progressBar.setVisibility(View.VISIBLE);
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url = "https://ehab01998.com/groups.php?department_id=" + studuentInfo.getDepartment() + "&groups_level=" + studuentInfo.getLevel();
        }else{
            url = "http://ehab01998.com/groups.php?department_id=" + studuentInfo.getDepartment() + "&groups_level=" + studuentInfo.getLevel();

        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("groups");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String id_g = object.getString("groups_id");
                        String id_d = object.getString("doctor_id");
                        String name = object.getString("groups_name");
//                        editor1.putString("Ngroup" + i, id_g);
                        listitems.add(new Ngroups(id_g, id_d, name));
                    }

                    groupsAdapter groupsAdapter = new groupsAdapter(listitems, getContext());
                    recyclerView.setAdapter(groupsAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nogroup.setVisibility(View.VISIBLE);
                    else
                        nogroup.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        nogroup.setVisibility(View.VISIBLE);
                    else
                        nogroup.setVisibility(View.INVISIBLE);


                } catch (Exception e) {
                }


            }
        });
        requestQueue.add(request);


    }




}
