package com.ehabahmed.studentcertificate;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SenderInbox extends Fragment {


    public SenderInbox() {
        // Required empty public constructor
    }

    private InboxAdabter mAdapter;
    ProgressBar bar;
    Info info;
    List<SendForm> list;
    RecyclerView recyclerView;
    TextView noavalible;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sender_inbox, container, false);

        info = (Info) getContext().getApplicationContext();
        noavalible=view.findViewById(R.id.noavalible);
        bar = view.findViewById(R.id.B_bar_sender);

        if(info.getType().equals("student")) {
            id=info.getId();

        }else if(info.getType().equals("doctor")){
            id=info.getDoctor_id();

        }else if(info.getType().equals("certificate")){
            id=info.getId();
        }
        getData(id);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(id).child("sender");
     list = new ArrayList<>();
         recyclerView = (RecyclerView) view.findViewById(R.id.Inbox_sender);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                bar.setVisibility(ProgressBar.VISIBLE);
                list.add(dataSnapshot.getValue(SendForm.class));
                mAdapter = new InboxAdabter(list, getContext());
                recyclerView.setAdapter(mAdapter);
                bar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        reference.addChildEventListener(childEventListener);
        return view;
    }
    private void getData(String id) {
        FirebaseDatabase.getInstance().getReference().child(id).child("sender")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChildren()){

                            noavalible.setVisibility(View.INVISIBLE);


                        }else{
                            noavalible.setVisibility(View.VISIBLE);
                            bar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
