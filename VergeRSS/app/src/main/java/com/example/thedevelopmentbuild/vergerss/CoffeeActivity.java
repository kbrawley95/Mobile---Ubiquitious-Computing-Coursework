package com.example.thedevelopmentbuild.vergerss;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoffeeActivity extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public CoffeeActivity() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        if (checkServices()) {
            view = inflater.inflate(R.layout.fragment_coffee, container, false);
            SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id
                        .map);
            fragment.getMapAsync(this);
            Toast.makeText(getContext(), "Ready to map!", Toast.LENGTH_SHORT).show();

            } else {
            view = inflater.inflate(R.layout.activity_main, container, false);

        }

        // Inflate the layout for this fragment
        return view;

    }

    public boolean checkServices() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());


        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(isAvailable, getActivity(), ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getContext(), "Could not connect to Google Play Services", Toast
                    .LENGTH_SHORT).show();

        }
        return false;

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        setupLocation(googleMap, 55.864237, -4.251806, 15, "Costa Coffee");
        setupLocation(googleMap, 55.865158, -4.259999, 15, "Costa Coffee");
        setupLocation(googleMap, 55.864173, -4.254602, 15, "Starbucks");
        setupLocation(googleMap, 55.861135, -4.260008, 15, "Starbucks");
        setupLocation(googleMap, 55.863773, -4.251751, 15, "Cafe Nero");
        setupLocation(googleMap, 55.862696, -4.25205, 15, "AMT Coffee");
        setupLocation(googleMap, 55.864903, -4.254551, 15, "Coffee Republic");

    }



    private void setupLocation(GoogleMap mMap, double lat, double lng, int zoomLevel, String
            markerTitle){

        LatLng latLng =new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        mMap.addMarker(new MarkerOptions().title(markerTitle).position(latLng));

    }
}

