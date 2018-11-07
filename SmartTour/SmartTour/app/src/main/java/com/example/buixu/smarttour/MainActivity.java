package com.example.buixu.smarttour;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap googleMap;
    Bundle packageFromCaller;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        Intent callerIntent = getIntent();
        packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
        };

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            if (googleMap == null) {googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();

            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            LatLng mapCenter = new LatLng(packageFromCaller.getDouble("latitude"), packageFromCaller.getDouble("longitude"));
            Log.i("aaaaa", "" + packageFromCaller.getDouble("latitude"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 15));

            googleMap.addMarker(new MarkerOptions().title("Sydney").snippet("The most populous city in Australia.").position(mapCenter));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent myIntent = new Intent(MainActivity.this, Viewinfo.class);
                myIntent.putExtra("MyPackage", packageFromCaller);
                startActivity(myIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        googleMaps = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        LatLng mapCenter = new LatLng(packageFromCaller.getDouble("latitude"), packageFromCaller.getDouble("longitude"));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 15));

        googleMaps.addMarker(new MarkerOptions().title("Sydney").snippet("The most populous city in Australia.").position(mapCenter));
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(21, -125);
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(21,-125)).title("Marker in Sydney"));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(21,-125)));
    }
}

