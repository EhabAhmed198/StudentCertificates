package com.ehabahmed.studentcertificate;



import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


public class StudentSettings extends Fragment {

RecyclerView recyclerView;
ArrayList<setting_object> listitem;
    Info studuentInfo;
    SettingAdapter adapter;
    String name,n1="",n2="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      studuentInfo=(Info)getContext().getApplicationContext();
        View view= inflater.inflate(R.layout.fragment_student_settings, container, false);
        name=studuentInfo.getName();

        if(name.contains(" ")) {
            n1 = name.substring(0, name.indexOf(" "));
            n2 = name.substring(name.indexOf(" "), name.length());
            name = n1.substring(0, 1).toUpperCase() + n1.substring(1) + n2.substring(0, 1).toUpperCase() + n2.substring(1);
        }
        recyclerView=view.findViewById(R.id.recycleview);
        putdata();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       getActivity().setTitle(getResources().getString(R.string.settings));
    }


    private void putdata() {
        listitem=new ArrayList<setting_object>();
    String name=studuentInfo.getName().toUpperCase().charAt(0)+studuentInfo.getName().substring(1);
        listitem.add(new setting_object(1,name,1));
        if(Integer.parseInt(studuentInfo.getLevel())!=5)
        listitem.add(new setting_object(2,getResources().getString(R.string.table),R.drawable.tables));
        if(Integer.parseInt(studuentInfo.getLevel())!=5)
        listitem.add(new setting_object(3,getResources().getString(R.string.result),R.drawable.result));

        listitem.add(new setting_object(4,getResources().getString(R.string.courses),R.drawable.courses));
        listitem.add(new setting_object(5,getResources().getString(R.string.library),R.drawable.library));
        if(Integer.parseInt(studuentInfo.getLevel())!=5)
        listitem.add(new setting_object(6,getResources().getString(R.string.examp),R.drawable.exams));
    listitem.add(new setting_object(8,getString(R.string.groups),R.drawable.groups));
        listitem.add(new setting_object(9,getResources().getString(R.string.Inbox),R.drawable.ic_notifications_active_black_24dp));

        if(Integer.parseInt(studuentInfo.getLevel())==5)
       listitem.add(new setting_object(10,getResources().getString(R.string.RequestAcertificate),R.drawable.gr));
        listitem.add(new setting_object(11,getResources().getString(R.string.compition),R.drawable.compition));
        listitem.add(new setting_object(12,getResources().getString(R.string.programs),R.drawable.programs));
        listitem.add(new setting_object(13,getResources().getString(R.string.Articles),R.drawable.articles));
        listitem.add(new setting_object(14,getResources().getString(R.string.map),R.drawable.map));
        listitem.add(new setting_object(15,getResources().getString(R.string.signout),R.drawable.signout));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new SettingAdapter(listitem,getContext());
        recyclerView.setAdapter(adapter);
    }

}
