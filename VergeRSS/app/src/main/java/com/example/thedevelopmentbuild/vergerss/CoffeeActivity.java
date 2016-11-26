package com.example.thedevelopmentbuild.vergerss;


import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.PopupMenuCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Console;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoffeeActivity extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;

    private GoogleApiClient mLocationClient;

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

            //Instantiate Client Object for location detecting
            mLocationClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mLocationClient.connect();

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

        mMap = googleMap;

        setupLocation(mMap, 55.864237, -4.251806, 15, "Costa Coffee");
        setupLocation(mMap, 55.865158, -4.259999, 15, "Costa Coffee");
        setupLocation(mMap, 55.864173, -4.254602, 15, "Starbucks");
        setupLocation(mMap, 55.861135, -4.260008, 15, "Starbucks");
        setupLocation(mMap, 55.863773, -4.251751, 15, "Cafe Nero");
        setupLocation(mMap, 55.862696, -4.25205, 15, "AMT Coffee");
        setupLocation(mMap, 55.864903, -4.254551, 15, "Coffee Republic");

    }


    private void setupLocation(GoogleMap mMap, double lat, double lng, int zoomLevel, String
            markerTitle) {

        LatLng latLng = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_icons))
                .title
                (markerTitle)
                .position
                (latLng));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void showCurrentLocation() {


        TabOverview tab = new TabOverview();
        Location currentLocation =tab.checkPermission(mLocationClient);


        if(currentLocation==null){
            Toast.makeText(getContext(), "Couldn't connect!", Toast.LENGTH_SHORT).show();
        }
        else{
            LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude
                    ());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(update);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(), "Connection Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Connection Unsuccessful", Toast.LENGTH_SHORT).show();

    }
}

