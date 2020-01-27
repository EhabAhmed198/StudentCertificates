package com.ehabahmed.studentcertificate;


import android.os.Bundle;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class Doctor_Lectures extends Fragment implements View.OnClickListener {
    Button btn_create, btn_groups;
    FragmentManager fragmentManager;
    ConstraintLayout group1;
    ConstraintLayout.LayoutParams params;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor__lectures, container, false);
        btn_create = view.findViewById(R.id.createGroups);
        btn_groups = view.findViewById(R.id.groups);
        group1 = view.findViewById(R.id.group1);
        params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        fragmentManager = getChildFragmentManager();
        btn_create.setOnClickListener(this);
        btn_groups.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createGroups:
                group1.setLayoutParams(params);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.container, new CreateLecGroups()).commit();
                break;
            case R.id.groups:
                group1.setLayoutParams(params);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.container, new DoctorLecGroups()).commit();
                break;
        }

    }
}