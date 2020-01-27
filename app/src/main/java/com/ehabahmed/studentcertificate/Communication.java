package com.ehabahmed.studentcertificate;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;


public class Communication extends Fragment implements View.OnClickListener {
    ImageView location1, facebok;
    Intent intent;
    TextView mobile1, mobile2, email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_communication, container, false);
        location1 =view.findViewById(R.id.location);
        facebok =view.findViewById(R.id.facebook);
        mobile1 = view.findViewById(R.id.mobile1);
        mobile2 = view.findViewById(R.id.mobile2);
        email = view.findViewById(R.id.enteremail);
        mobile1.setOnClickListener(this);
        mobile2.setOnClickListener(this);
        email.setOnClickListener(this);
        location1.setOnClickListener(this);
        facebok.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:30.279105,31.473375?z=19"));

                startActivity(intent);
                break;
            case R.id.facebook:
                intent = new Intent(Intent.ACTION_VIEW);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1){
                    intent.setData(Uri.parse("https://www.facebook.com/obourinstitutes/"));
                }else{
                    intent.setData(Uri.parse("http://www.facebook.com/obourinstitutes/"));

                }

                startActivity(intent);
                break;

            case R.id.mobile1:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:" + mobile1.getText().toString()));
                startActivity(intent);
                break;

            case R.id.mobile2:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:" + mobile2.getText().toString()));
                startActivity(intent);
                break;
            case R.id.enteremail:
                intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email.getText().toString(), null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(intent, "Send email..."));
        }

    }

}
