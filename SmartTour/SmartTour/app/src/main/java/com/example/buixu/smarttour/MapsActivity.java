package com.example.buixu.smarttour;

import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Bundle packageFromCaller;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent myIntent = new Intent(MapsActivity.this, Viewinfo.class);
                myIntent.putExtra("MyPackage", packageFromCaller);
                startActivity(myIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent callerIntent = getIntent();
        packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
        };
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        LatLng mapCenter = new LatLng(packageFromCaller.getDouble("latitude"), packageFromCaller.getDouble("longitude"));
        mMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 15));
        // Add a marker in Sydney and move the camera
     //   LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().title("Sydney").snippet("Điểm đến của bạn.").position(mapCenter));

        mMap.addMarker(new MarkerOptions().position(mapCenter).title("Marker in mapcenter"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapCenter));
    }
}
