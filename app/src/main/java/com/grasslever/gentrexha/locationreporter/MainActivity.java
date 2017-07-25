package com.grasslever.gentrexha.locationreporter;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.grasslever.gentrexha.locationreporter.fragments.GoogleMapFragment;
import com.grasslever.gentrexha.locationreporter.fragments.HistoryFragment;
import com.grasslever.gentrexha.locationreporter.fragments.InformationFragment;
import com.grasslever.gentrexha.locationreporter.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

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
                    fragmentManager.beginTransaction().replace(R.id.content, new InformationFragment()).commit();
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
    }
}
