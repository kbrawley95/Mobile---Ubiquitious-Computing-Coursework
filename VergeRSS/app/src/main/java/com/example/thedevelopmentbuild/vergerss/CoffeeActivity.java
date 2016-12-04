package com.example.thedevelopmentbuild.vergerss;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thedevelopmentbuild.vergerss.GoogleMapLocations.Coordinates;
import com.example.thedevelopmentbuild.vergerss.Model.DataItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.thedevelopmentbuild.vergerss.Database.DbDataSource;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoffeeActivity extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE = 1000;
    private static final int PLACE_AUTOCOMPLETE_REQUEST = 1;
    private static final int REQUEST_PLACE_PICKER =2 ;
    public static GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;

    private boolean permissionGranted=false;

    protected Location currentLocation;
    private GoogleApiClient mLocationClient;
    private FloatingActionButton placepicker;
    private LatLng autoStartLocation;

    private DbDataSource mDataSource;
    List<DataItem> dataItemList = Coordinates.dataItemList;
    List<DataItem>dbList;
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

//            placepicker=(FloatingActionButton)view.findViewById(R.id
//                    .place_autocomplete_search_button);
//
//            placepicker.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mLocationClient.isConnected()){
//                        try {
//                            activatePicker(autoStartLocation);
//                        } catch (GooglePlayServicesNotAvailableException e) {
//                            e.printStackTrace();
//                        } catch (GooglePlayServicesRepairableException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else{
//                        Toast.makeText(getContext(), "Enable GPS", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });



            mDataSource = new DbDataSource(getContext());
            mDataSource.open();
            mDataSource.seedDatabase(dataItemList);

            Toast.makeText(getContext(), "Database Acquired!", Toast.LENGTH_SHORT).show();
            dbList=mDataSource.getAllItems();


        } else {
            view = inflater.inflate(R.layout.activity_main, container, false);

        }

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onPause() {
        super.onPause();

        mDataSource.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        mDataSource.open();
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
            setupLocation(mMap,15,dbList);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    protected void activatePicker(LatLng currentLocation) throws
//            GooglePlayServicesNotAvailableException,
//            GooglePlayServicesRepairableException {
//
//        int PLACE_PICKER_REQUEST = 1;
//
//        Log.i("PlaceNew", currentLocation.toString());
//        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(currentLocation).include
//                (currentLocation).build();
//
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        Intent intent = builder.setLatLngBounds(latLngBounds).build(getActivity());
//
//        startActivityForResult(intent, PLACE_PICKER_REQUEST);
//            // ...
//        }


    /*Handles the population of location markers within map view, and their respective details.*/
    private void setupLocation(GoogleMap mMap,int zoomLevel, List<DataItem>dataItems)
            throws
            IOException {


        for(DataItem item: dataItems){

            LatLng latLng = new LatLng(item.getItemLat(), item.getItemLong());
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_icons))
                    .snippet(item.getItemAddress())
                    .title(item.getItemName())
                    .position(latLng)
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }

    private void panToCurrentLocation() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {

        if(currentLocation==null){
            Toast.makeText(getActivity(), "Couldn't Connect!", Toast.LENGTH_SHORT).show();
        }
        else{
            LatLng latLng = new LatLng(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude()
            );
            autoStartLocation=latLng;


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                permissionGranted=true;
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Toast.makeText(getContext(), "Ready to map!", Toast.LENGTH_SHORT).show();

        int permCheck = ContextCompat.checkSelfPermission(getContext(),Manifest.permission
                .ACCESS_FINE_LOCATION);
        if(permCheck!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .ACCESS_FINE_LOCATION},1);
        }
        else{
            currentLocation = LocationServices.FusedLocationApi.getLastLocation
                    (mLocationClient);
            try {
                panToCurrentLocation();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }

            mMap.setMyLocationEnabled(true);


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

