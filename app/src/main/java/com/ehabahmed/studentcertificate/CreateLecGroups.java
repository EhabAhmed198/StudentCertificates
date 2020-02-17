package com.ehabahmed.studentcertificate;


import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class CreateLecGroups extends Fragment implements View.OnClickListener {
EditText groupName;
Spinner department,level;
Button create;
String url;
Info info;
RequestQueue requestQueue;

String[] level_names,department_names;
int student_info_level,department_id;
ArrayAdapter<String> adapter1,adapter2; // adapter1==department_names : adapter2==level_names

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_create_groups, container, false);
       department_names=getResources().getStringArray(R.array.departments);
       level_names=getResources().getStringArray(R.array.level);
       groupName=view.findViewById(R.id.groupName);
       department=view.findViewById(R.id.department);
       info=(Info)getActivity().getApplicationContext();
       level=view.findViewById(R.id.level);
       create=view.findViewById(R.id.create);
        adapter1=new ArrayAdapter<>(getContext(),R.layout.spinner,R.id.text_spinner,department_names);
        adapter2=new ArrayAdapter<>(getContext(),R.layout.spinner,R.id.text_spinner,level_names);
        department.setAdapter(adapter1);
        level.setAdapter(adapter2);
       requestQueue= Volley.newRequestQueue(getContext());
      create.setOnClickListener(this);


       return view;
    }

    private void createGroups() {
        getConvertSpinnerData();
        String url;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/createGroups.php";
        }else{
            url="http://ehab01998.com/createGroups.php";

        }

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
   if(response.equals("Scuccess"))
       Toast.makeText(getContext(), R.string.create_Success_Group, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("groups_name",groupName.getText().toString().trim());
                params.put("department_id", String.valueOf(department_id));
                params.put("groups_level", String.valueOf(student_info_level));
                params.put("doctor_id",info.getDoctor_id());
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void getConvertSpinnerData(){
        if(department.getSelectedItem().toString().equals(department_names[0])){
            department_id=1;
        }
        else   if(department.getSelectedItem().toString().equals(department_names[1])){
            department_id=2;
        }
        else   if(department.getSelectedItem().toString().equals(department_names[2])){
            department_id=3;
        }
        else   if(department.getSelectedItem().toString().equals(department_names[3])){

            department_id=4;
        }

        if(level.getSelectedItem().toString().equals(level_names[0])){
            student_info_level=1;
        }
        else if(level.getSelectedItem().toString().equals(level_names[1])){
            student_info_level=2;
        }
        else if(level.getSelectedItem().toString().equals(level_names[2])){
            student_info_level=3;
        }
        else if(level.getSelectedItem().toString().equals(level_names[3])){
            student_info_level=4;
        }


    }


    @Override
    public void onClick(View view) {
        String check_groupName=groupName.getText().toString();
        if(check_groupName.isEmpty()){
            groupName.setError(getResources().getString(R.string.entergroupName));
        }else {
            createGroups();
        }
    }
}
