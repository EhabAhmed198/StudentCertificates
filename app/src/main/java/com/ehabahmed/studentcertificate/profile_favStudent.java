package com.ehabahmed.studentcertificate;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class profile_favStudent extends Fragment {
RecyclerView rc_fav;
ArrayList<FAV_object> listitems;
Info info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_profile_fav_student, container, false);
        rc_fav=view.findViewById(R.id.fav);
        rc_fav.setLayoutManager(new GridLayoutManager(getContext(),2));
        info=(Info)getContext().getApplicationContext();
        putdata();

        return view;
    }

    private void putdata() {
        listitems=new ArrayList<FAV_object>();
        listitems.add(new FAV_object("1",getResources().getString(R.string.news),R.drawable.favnews));
        listitems.add(new FAV_object("2",getResources().getString(R.string.courses),R.drawable.onlinecourse));
        listitems.add(new FAV_object("3",getResources().getString(R.string.library),R.drawable.favlibrary));
        listitems.add(new FAV_object("4",getResources().getString(R.string.compition),R.drawable.favcontest));
        if(Integer.parseInt(info.getLevel())!=5)
        listitems.add(new FAV_object("5",getResources().getString(R.string.examp),R.drawable.favexams));



        FAV_adpter adpter=new FAV_adpter(getContext(),listitems,'s');
        rc_fav.setAdapter(adpter);
    }


}