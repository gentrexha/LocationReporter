package com.grasslever.gentrexha.locationreporter;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by GRexha on 25-Jul-17.
 */

public class InformationFragment extends Fragment {

    private String mLatitudeText = "n/a";
    private String mLongitudeText = "n/a";
    private String mAltitude = "n/a";
    private TextView textViewInformation;
    private static final String openweatherAPIURL = "http://api.openweathermap.org/data/2.5/weather?lat=";
    private static final String openweatherAPIKey = "45031aee347ff5ce623d388389a709a1";
    private String mTemp = "n/a";
    private static final String elevationAPIURUL = "https://maps.googleapis.com/maps/api/elevation/json?locations=";
    private static final String elevationAPIKey = "AIzaSyBvs_wbNGAqayC9V5sApoDzCi0gwfYszlQ";
    private String mPlace = "n/a";
    private Database db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information,container,false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewInformation = (TextView)view.findViewById(R.id.textview_information);
        db = new Database(getActivity());
        Bundle bundle = getArguments();
        mLatitudeText = bundle.getString("latitude");
        mLongitudeText = bundle.getString("longitude");
//        textViewInformation.setText(mLatitudeText + " and " + mLongitudeText);

        if (isNetworkAvailable()) {
            new RetrieveElevation().execute();
            new RetrieveWeather().execute();
        }
        else {
            textViewInformation.setText("Placeholder");
        }
    }

    private class RetrieveWeather extends AsyncTask<Void,Void,JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }

    private class RetrieveElevation extends AsyncTask<Void,Void,JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }
}
