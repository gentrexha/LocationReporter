package com.grasslever.gentrexha.locationreporter;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by GRexha on 25-Jul-17.
 */

public class HistoryFragment extends Fragment {


    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Database db = new Database(getActivity());
        final Cursor objCursor = db.getAllLocations();

        String [] columns = new String[] {
                Database.LOCATION_COLUMN_PLACE,
                Database.LOCATION_COLUMN_WEATHER,
                Database.LOCATION_COLUMN_TIME
        };

        int [] widgets = new int[] {
                R.id.place,
                R.id.weather,
                R.id.time
        };

        ListView listView = (ListView) view.findViewById(R.id.list);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.list_history,objCursor,columns,widgets,0);
        listView.setAdapter(cursorAdapter);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.showAllPlaces);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(getActivity(),HistoryMapActivity.class);
                startActivity(myint);
            }
        });
    }
}
