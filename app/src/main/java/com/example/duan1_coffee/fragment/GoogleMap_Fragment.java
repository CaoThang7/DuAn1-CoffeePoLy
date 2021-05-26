package com.example.duan1_coffee.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_coffee.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMap_Fragment extends androidx.fragment.app.Fragment {
    ImageView backinfo;
    Button btnCS1,btnCS2,btnCS3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ggmap, container, false);

        //Anh xa
        backinfo=view.findViewById(R.id.backinfo);
        btnCS1=view.findViewById(R.id.CS1);
        btnCS2=view.findViewById(R.id.CS2);
        btnCS3=view.findViewById(R.id.CS3);

        //Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        isPermissionGranted();

        backinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Info_Fragment llf = new Info_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });



        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                btnCS1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toa do fpoly cs3
                        LatLng toado = new LatLng(10.790955379100097, 106.68180949657004);
                        Marker danang = googleMap.addMarker(
                                new MarkerOptions()
                                        .position(toado)
                                        .title("Coffee Fpoly")
                                        .snippet("Welcome! My coffee")
                                        .icon(BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_YELLOW)));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toado, 17));

                        // Cai dat map
                        UiSettings uisetting = googleMap.getUiSettings();
                        uisetting.setCompassEnabled(true);
                        uisetting.setZoomControlsEnabled(true);
                        uisetting.setScrollGesturesEnabled(true);
                        uisetting.setTiltGesturesEnabled(true);

                        uisetting.setMyLocationButtonEnabled(true);

                        // Mo chuc nang My Location
                        googleMap.setMyLocationEnabled(true);

                    }
                });
                btnCS2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toa do fpoly cs3
                        LatLng toado = new LatLng(10.811969474411589, 106.67990511241427);
                        Marker danang = googleMap.addMarker(
                                new MarkerOptions()
                                        .position(toado)
                                        .title("Coffee Fpoly")
                                        .snippet("Welcome! My coffee")
                                        .icon(BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_YELLOW)));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toado, 17));

                        // Cai dat map
                        UiSettings uisetting = googleMap.getUiSettings();
                        uisetting.setCompassEnabled(true);
                        uisetting.setZoomControlsEnabled(true);
                        uisetting.setScrollGesturesEnabled(true);
                        uisetting.setTiltGesturesEnabled(true);

                        uisetting.setMyLocationButtonEnabled(true);

                        // Mo chuc nang My Location
                        googleMap.setMyLocationEnabled(true);

                    }
                });
                btnCS3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toa do fpoly cs3
                        LatLng toado = new LatLng(10.853055, 106.629585);
                        Marker danang = googleMap.addMarker(
                                new MarkerOptions()
                                        .position(toado)
                                        .title("Coffee Fpoly")
                                        .snippet("Welcome! My coffee")
                                        .icon(BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_YELLOW)));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toado, 17));

                        // Cai dat map
                        UiSettings uisetting = googleMap.getUiSettings();
                        uisetting.setCompassEnabled(true);
                        uisetting.setZoomControlsEnabled(true);
                        uisetting.setScrollGesturesEnabled(true);
                        uisetting.setTiltGesturesEnabled(true);

                        uisetting.setMyLocationButtonEnabled(true);

                        // Mo chuc nang My Location
                        googleMap.setMyLocationEnabled(true);

                    }
                });
            }
        });
        return view;
    }


    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 2: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    //do your specific task after read phone state granted
                } else {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
