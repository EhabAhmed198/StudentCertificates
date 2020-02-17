package com.ehabahmed.studentcertificate;


import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Graduatesform extends Fragment {
String name,dataofbirths="n",address,mobile,email,department,graudtionYear,currentJob,CompayName,dataEmployee="n";
EditText ed_name,ed_address,ed_mobile,ed_email,ed_graudtionYear,ed_currentJob
        ,ed_CompayName;
Button send;
TextView ed_dataEmployee,ed_dataofbirth;
RequestQueue requestQueue;
public AlertDialog.Builder alertDialog;

DatePickerDialog.OnDateSetListener mDateSetListener1,mDateSetListener2;
    public Graduatesform() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_graduatesform, container, false);
       send=view.findViewById(R.id.Send);
    ed_name=view.findViewById(R.id.name);
       ed_dataofbirth=view.findViewById(R.id.data_of_brith);
        ed_address=view.findViewById(R.id.Address);
        ed_mobile=view.findViewById(R.id.mobile);
        ed_email=view.findViewById(R.id.email);
        ed_graudtionYear=view.findViewById(R.id.GraduationYear);
        ed_currentJob=view.findViewById(R.id.Currentjob);
        ed_CompayName=view.findViewById(R.id.CompayName);
        ed_dataEmployee=view.findViewById(R.id.dateofemployment);
        requestQueue= Volley.newRequestQueue(getContext());
     ed_dataofbirth.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Calendar cal=Calendar.getInstance();
             int year=cal.get(Calendar.YEAR);
             int month=cal.get(Calendar.MONTH);
             int day=cal.get(Calendar.DAY_OF_MONTH);
             DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
                     android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener1,
                     year,month,day);
             datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
             datePickerDialog.show();
         }
     });

     mDateSetListener1=new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker datePicker, int year, int month, int day) {
             month=month+1;
          dataofbirths=day+ "/"+month  + "/"+ year;
             ed_dataofbirth.setError(null);
            ed_dataofbirth.setText(dataofbirths);
         }
     };


     ed_dataEmployee.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Calendar cal=Calendar.getInstance();
             int year=cal.get(Calendar.YEAR);
             int month=cal.get(Calendar.MONTH);
             int day=cal.get(Calendar.DAY_OF_MONTH);
             DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
                     android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener2,
                     year,month,day);
             datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
             datePickerDialog.show();
         }
     });
        mDateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                dataEmployee=day+ "/"+month  + "/"+ year;
                ed_dataEmployee.setError(null);
                ed_dataEmployee.setText(dataEmployee);
            }
        };

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info info=(Info)getContext().getApplicationContext();
                name=ed_name.getText().toString().trim();
                address=ed_address.getText().toString().trim();
                mobile=ed_mobile.getText().toString().trim();
                email=ed_email.getText().toString().trim();
                department=info.getDepartment();
                graudtionYear=ed_graudtionYear.getText().toString().trim();
                currentJob=ed_currentJob.getText().toString().trim();
                CompayName=ed_CompayName.getText().toString().trim();
                //--dataofemployee
                if(name.isEmpty())
                    ed_name.setError(getResources().getString(R.string.EnterYourName));

                else if(dataofbirths.equals("n"))
                ed_dataofbirth.setError(getResources().getString(R.string.EnterDataofBirth));
//                    Toast.makeText(info, getResources().getString(R.string.EnterDataofBirth), Toast.LENGTH_SHORT).show();

                else if(address.isEmpty())
                    ed_name.setError(getResources().getString(R.string.EnterYourAddress));

                else if(mobile.isEmpty())
                    ed_mobile.setError(getResources().getString(R.string.EnterYourEmail));

                else if(email.isEmpty())
                    ed_email.setError(getResources().getString(R.string.EnterYourEmail));


                else if(graudtionYear.isEmpty())
                    ed_graudtionYear.setError(getResources().getString(R.string.EnterGraduationYear));

                else if(currentJob.isEmpty())
                    ed_currentJob.setError(getResources().getString(R.string.EnterCurentJob));

                else if(CompayName.isEmpty())
                    ed_CompayName.setError(getResources().getString(R.string.EnterCompanyName));

                else if(dataEmployee.equals("n"))
                    ed_dataEmployee.setError(getResources().getString(R.string.EnterDateofEmployee));
                else
                    sendDataForm();


            }
        });

       return view;
    }
    public void sendDataForm(){
        String url;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
            url="https://ehab01998.com/GraduateForm.php";
        }else{
            url="http://ehab01998.com/GraduateForm.php";

        }

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               if(response.equals("Sucess"))
                   massege(getResources().getString(R.string.sendscuccess),
                           getResources().getString(R.string.massegeGruadtion),getResources().getString(R.string.ok));
               else if(response.equals("exist"))
                   Toast.makeText(getContext(), getResources().getString(R.string.exitname), Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),getResources().getString(R.string.no_communcationInternet), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> parms=new HashMap<String, String>();
                parms.put("name",name);
                parms.put("dataofbirth",dataofbirths);
                parms.put("address",address);
                parms.put("mobile",mobile);
                parms.put("email",email);
                parms.put("department",department);
                parms.put("graudtionYear",graudtionYear);
                parms.put("currentJob",currentJob);
                parms.put("CompayName",CompayName);
                parms.put("dataEmployee",dataEmployee);
                return parms;
            }
        };

        requestQueue.add(request);


    }
    public void massege(String title ,String massege,String positive){
        alertDialog=new AlertDialog.Builder(getContext());
        alertDialog.setTitle(title);
        alertDialog.setMessage(massege);
        alertDialog.setPositiveButton(positive, null);
        alertDialog.show();

    }

}
