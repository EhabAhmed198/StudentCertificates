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


public class Sudentguide extends Fragment {
 RecyclerView recyclerView;
 ArrayList<String> listitems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_sudentguide, container, false);
        recyclerView=view.findViewById(R.id.recycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listitems=new ArrayList<String>();
        listitems.add(getResources().getString(R.string.land1));
        listitems.add(getResources().getString(R.string.land2));
        listitems.add(getResources().getString(R.string.land3));
        listitems.add(getResources().getString(R.string.land4));

studentguide_Adapter adapter=new studentguide_Adapter(listitems,getContext());
recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       getActivity().setTitle(getResources().getString(R.string.guide));
    }

}
