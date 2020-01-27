package com.ehabahmed.studentcertificate;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorSetting extends Fragment {
RecyclerView recyclerView;
ArrayList<setting_object> listitems;
Info info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View  view= inflater.inflate(R.layout.fragment_doctor_setting, container, false);
       recyclerView=view.findViewById(R.id.doctorsetting);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
info=(Info)getActivity().getApplicationContext();
putdata();
       return view;
    }

    private void putdata() {
        listitems=new ArrayList<setting_object>();
        listitems.add(new setting_object(1,info.getDoctor_name(),R.drawable.profile2));
        listitems.add(new setting_object(2,getResources().getString(R.string.table),R.drawable.tables));
        listitems.add(new setting_object(3,getResources().getString(R.string.courses),R.drawable.courses));
        listitems.add(new setting_object(4,getResources().getString(R.string.library),R.drawable.library));
        listitems.add(new setting_object(5,getResources().getString(R.string.examp),R.drawable.exams));
        listitems.add(new setting_object(6,getResources().getString(R.string.compition),R.drawable.compition));
        listitems.add(new setting_object(7,getResources().getString(R.string.programs),R.drawable.programs));
        listitems.add(new setting_object(8,getResources().getString(R.string.map),R.drawable.map));
        listitems.add(new setting_object(9,getResources().getString(R.string.signout),R.drawable.signout));
        DoctorSettingAdapter doctorSettingAdapter=new DoctorSettingAdapter(getContext(),listitems);
        recyclerView.setAdapter(doctorSettingAdapter);
    }


}
