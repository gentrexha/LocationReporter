package com.grasslever.gentrexha.locationreporter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GoogleMapFragment.OnLocationFound{

    private int locationRequestCode;
    private String mLatitude = "n/a";
    private String mLongitude = "n/a";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    fragmentManager.beginTransaction().replace(R.id.content, new GoogleMapFragment()).commit();
                    return true;
                case R.id.navigation_history:
                    fragmentManager.beginTransaction().replace(R.id.content, new HistoryFragment()).commit();
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

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new MainFragment()).commit();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            fragmentManager.beginTransaction().replace(R.id.content, new GoogleMapFragment()).commit();
        else {
            Toast.makeText(this,"Enable Location",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationFound(String latitude, String longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }
}
