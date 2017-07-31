package com.grasslever.gentrexha.locationreporter;

import android.database.Cursor;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HistoryMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.historyMap);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Database db = new Database(this);
        final Cursor cursor = db.getAllLocations();
        if (cursor.moveToFirst()){
            do{
                String lat = cursor.getString(cursor.getColumnIndex("lat"));
                String lng = cursor.getString(cursor.getColumnIndex("lng"));
                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                String name = cursor.getString(cursor.getColumnIndex("place"));
                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).title(name));
                if (cursor.isLast())
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            } while(cursor.moveToNext());
        }
    }
}