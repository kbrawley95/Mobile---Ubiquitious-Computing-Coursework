package com.example.thedevelopmentbuild.vergerss;

import android.*;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;


/*Tabbed Menu implemented to enable navigation between fragmented views*/
public class TabOverview extends AppCompatActivity {

    public static int mapType = 1;

    //Widget Declarations
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    //Viewpager (Enables swiping between fragment activities)
    ViewPagerAdapter viewPagerAdapter;
    FloatingActionButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        infoButton = (FloatingActionButton) findViewById(R.id.info);
        infoButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color
                .colorPrimary)));
        if (infoButton == null) throw new AssertionError();
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        /*Adding Fragments to the viewPagerAdapter. This handles how the fragments are displayed
        on screen. i.e. order of swipable activities/Tab titles.
         */
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new MainActivity(), "News");
        viewPagerAdapter.addFragments(new CoffeeActivity(), "Coffee Shops");
        viewPagerAdapter.addFragments(new MainActivity(), "Other");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    /*Used to display the about dialog box on screen*/
    private void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(TabOverview.this).create();
        alertDialog.setTitle("About");
        alertDialog.setMessage("This is a news app for tech enthusiasts with a caffeine addiction");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    /*Used to display items list menu when the user taps the floating action button*/
    public void showPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.map_options_menu, popup.getMenu());
        popup.show();
        setUpPopEventManger(popup);
    }

    /*Manages the appropriate behaviour of the items within menu when clicked/tapped*/
    public void setUpPopEventManger(PopupMenu pop) {
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        showDialog();
                        return true;
                    case R.id.hybrid:
                        CoffeeActivity.mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        return true;
                    case R.id.normal:
                        CoffeeActivity.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        return true;
                    case R.id.satellite:
                        CoffeeActivity.mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        return true;
                    case R.id.terrain:
                        CoffeeActivity.mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        return true;
                    case R.id.none:
                        CoffeeActivity.mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                        return true;
                    case R.id.currentLocation:
                        return true;


                    default:
                        return false;
                }
            }
        });
    }
}

