package com.example.thedevelopmentbuild.vergerss;


import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.PopupMenuCompat;
import android.util.Log;
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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.internal.PlaceEntity;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.wearable.DataMap.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoffeeActivity extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE = 1000;
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
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
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

    /*Checks whether connection to google play services (for google maps api) has been
    established. Displays toast message dependant on outcome (Success/Fail)*/
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


    /*Method called at runtime that populates the content of the google map fragment*/
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        try {
            setupLocation(mMap, 55.864237, -4.251806, 15,false);
            setupLocation(mMap, 55.865158, -4.259999, 15,false);
            setupLocation(mMap, 55.864173, -4.254602, 15,false);
            setupLocation(mMap, 55.861135, -4.260008, 15,false);
            setupLocation(mMap, 55.863773, -4.251751, 15,false);
            setupLocation(mMap, 55.862696, -4.25205, 15,false);
            setupLocation(mMap, 55.864903, -4.254551, 15,false);
            updateLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*Handles the population of location markers within map view, and their respective details.*/
    private void setupLocation(GoogleMap mMap, double lat, double lng, int zoomLevel, boolean
            animateMove)
            throws
            IOException {

        LatLng latLng = new LatLng(lat, lng);

        if(animateMove)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
        else{
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);


        Address add = addressList.get(0);



        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_icons))
                .snippet("")
                .title
                        (add.getCountryName())
                .position(latLng)
        );

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }


    protected Location getLocation() {

        try{
            Location loc = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
            return loc;
        }
        catch(SecurityException e){
            e.printStackTrace();
            return null;
        }

    }

    public void updateLocation() throws IOException {
        Location loc=getLocation();
        setupLocation(mMap, loc.getLatitude(), loc.getLongitude(), 15, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        int permCheck = ContextCompat.checkSelfPermission(getContext(),Manifest.permission
                .ACCESS_FINE_LOCATION);
        if(permCheck!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .ACCESS_FINE_LOCATION},1);
        }
        else{

        }

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

