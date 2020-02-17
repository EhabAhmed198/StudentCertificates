package com.ehabahmed.studentcertificate;


import android.os.Build;
import android.os.Bundle;

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


public class DoctorLecGroups extends Fragment {
Info info;
RecyclerView recyclerView;
ArrayList<DoctorLecGroups_Object> listitems;
RequestQueue requestQueue;
TextView no_groups;
ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_doctor__groups, container, false);
        info=(Info)getActivity().getApplicationContext();
        listitems=new ArrayList<DoctorLecGroups_Object>();
        requestQueue= Volley.newRequestQueue(getContext());
        recyclerView=view.findViewById(R.id.doctor_groups);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        no_groups=view.findViewById(R.id.no_groups);
        no_groups.setVisibility(View.INVISIBLE);
        progressBar=view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

      getdata();
      return view;
    }

    private void getdata() {
        progressBar.setVisibility(View.VISIBLE);
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="http://ehab01998.com/dgroups.php?doctor_id="+info.getDoctor_id();
        }else{
            url="http://ehab01998.com/dgroups.php?doctor_id="+info.getDoctor_id();

        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Groups");
                    for (int i=0;i<array.length();i++){
                        JSONObject current=array.getJSONObject(i);
                        String id=current.getString("groups_id");
                        String name=current.getString("groups_name");
                        listitems.add(new DoctorLecGroups_Object(id,name));
                    }
                    DoctorLecGroupAdapter adapter=new DoctorLecGroupAdapter(getContext(),listitems);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    if(listitems.size()<=0)
                        no_groups.setVisibility(View.VISIBLE);
                    else
                        no_groups.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                if(listitems.size()<=0)
                    no_groups.setVisibility(View.VISIBLE);
                else
                    no_groups.setVisibility(View.INVISIBLE);

            }
        });
        requestQueue.add(request);
    }

}
