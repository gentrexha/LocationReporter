package com.grasslever.gentrexha.locationreporter;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by GRexha on 27-Jul-17.
 */

public class HistoryMapFragment extends Fragment implements OnMapReadyCallback {



    private GoogleMap mMap;
    private String lat = null;
    private String lng = null;
    private String name = null;

    GoogleMap mGoogleMap;

    private Database db;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        /*db = new Database(getActivity());
        Cursor cursor = db.getAllLocations();

        if (cursor.moveToFirst()){
            do{
                lat = cursor.getString(cursor.getColumnIndex("lat"));
                lng = cursor.getString(cursor.getColumnIndex("lng"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).title(name));
            }while(cursor.moveToNext());
        }
        cursor.close();*/
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(42.6578, 21.1474)).title("Dumnica"));
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(52.5200, 13.4050)).title("Berlin"));
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(60.7954, 10.6916)).title("Gjøvik"));
        Toast.makeText( getActivity(), "Added markers for the places you have been!", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment mapFragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mGoogleMap == null) {
            //getMapAsync(this);
        }
    }
}
