package com.ehabahmed.studentcertificate;



import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;



public class Maps extends FragmentActivity implements OnMapReadyCallback {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
         GoogleMap mMap;
        mMap = googleMap;
        String type=getIntent().getExtras().getString("type");
        LatLng latLng;
        switch (type){
            case "m":
                latLng=new LatLng(30.2781649,31.4725981);
                mMap.addMarker(new MarkerOptions().position(latLng).title("المسجد"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                break;
            case "b1":
                latLng=new LatLng(30.2784954,31.472145);
                mMap.addMarker(new MarkerOptions().position(latLng).title("B1"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                break;
            case "b2":


                latLng=new LatLng(30.278625,31.4715061);
                mMap.addMarker(new MarkerOptions().position(latLng).title("B2"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));



                break;
            case "b3":
                latLng=new LatLng(30.2786777,31.4713346);
                mMap.addMarker(new MarkerOptions().position(latLng).title("B3"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

                break;

            case "b5":
                latLng=new LatLng(30.2789793,31.4718336);
                mMap.addMarker(new MarkerOptions().position(latLng).title("B5"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

                break;
            case "b7":
                latLng=new LatLng(30.2782895,31.4724272);
                mMap.addMarker(new MarkerOptions().position(latLng).title("B7(الشؤن)"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                break;
            case "ATEC":
                latLng=new LatLng(30.2785478,31.472727593);
                mMap.addMarker(new MarkerOptions().position(latLng).title("ATEC"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                break;


        }


    }




}