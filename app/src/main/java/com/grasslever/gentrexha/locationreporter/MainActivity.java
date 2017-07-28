package com.grasslever.gentrexha.locationreporter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity implements LocationMapFragment.OnLocationFound{

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 585;
    private String mLatitude = "n/a";
    private String mLongitude = "n/a";
    MapFragment mapFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    fragmentManager.beginTransaction().replace(R.id.content, new LocationMapFragment()).commit();
                    return true;
                case R.id.navigation_history:
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content, new HistoryFragment()).commit();
                    return true;
                case R.id.navigation_information:
                    InformationFragment newFragment = new InformationFragment();
                    Bundle args = new Bundle();
                    args.putString("latitude",mLatitude);
                    args.putString("longitude",mLongitude);
                    newFragment.setArguments(args);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_map);

        mapFragment = new MapFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new LocationMapFragment()).commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LocationMapFragment.MY_PERMISSIONS_REQUEST_LOCATION){
            mapFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onLocationFound(String latitude, String longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }
}
