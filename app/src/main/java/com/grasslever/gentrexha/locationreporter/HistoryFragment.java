package com.grasslever.gentrexha.locationreporter;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by GRexha on 25-Jul-17.
 */

public class HistoryFragment extends Fragment {
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
    }
}
