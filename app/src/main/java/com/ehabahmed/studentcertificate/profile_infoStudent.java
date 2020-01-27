package com.ehabahmed.studentcertificate;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class profile_infoStudent extends Fragment {

TextView code,department,land;
Info studuentInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
studuentInfo=(Info)getContext().getApplicationContext();
     View view=    inflater.inflate(R.layout.fragment_profile_info_student, container, false);
     code=view.findViewById(R.id.entercode);
     department=view.findViewById(R.id.enterdepartment);
     land=view.findViewById(R.id.enterland);
code.setText(studuentInfo.getId());
switch (studuentInfo.getDepartment()){
         case "1":
             department.setText(getResources().getString(R.string.Sc));
             break;
         case "2":
             department.setText(getResources().getString(R.string.IS));
             break;
         case "3":
             department.setText("ادارة عربي");
             break;
         case "4":
             department.setText("ادارة انجليزي");
             break;
     }
     switch (studuentInfo.getLevel()){
         case "1":
                   land.setText(getResources().getString(R.string.one));
             break;
         case "2":
             land.setText(getResources().getString(R.string.two));
             break;
         case "3":
             land.setText(getResources().getString(R.string.thired));
             break;

         case "4":
             land.setText(getResources().getString(R.string.foure));
             break;
         case "5":
             land.setText(getResources().getString(R.string.graduate));
     }
     return view;
    }


}