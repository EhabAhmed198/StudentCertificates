package com.ehabahmed.studentcertificate;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DoctorInfo extends Fragment {
TextView tv_mobile,tv_email;
Info doctorInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_doctor_info, container, false);
        doctorInfo=(Info) getContext().getApplicationContext();
        tv_mobile=view.findViewById(R.id.enterphone);
        tv_email=view.findViewById(R.id.enteremail);

       tv_mobile.setText(doctorInfo.getDoctor_mobile());

       tv_email.setText(doctorInfo.getDoctor_email());
        return view;
    }

}