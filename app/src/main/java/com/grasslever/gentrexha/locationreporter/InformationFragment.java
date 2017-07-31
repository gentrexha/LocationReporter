package com.grasslever.gentrexha.locationreporter;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    FloatingActionButton floatingActionButton;

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

        textViewInformation = (TextView)view.findViewById(R.id.textview_information_place);
        db = new Database(getActivity());
        Bundle bundle = getArguments();
        mLatitudeText = bundle.getString("latitude");
        mLongitudeText = bundle.getString("longitude");

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        floatingActionButton.hide();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertLocation(mPlace,mTemp,mLatitudeText, mLongitudeText, new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).format(new java.util.Date()));
                Toast.makeText(getActivity(),"Location saved and added to history.",Toast.LENGTH_LONG).show();
            }
        });

        if (isNetworkAvailable()) {
            if (mLatitudeText.equals("n/a"))
            {
                Toast.makeText(getActivity(),"Couldn't retrieve location from map.",Toast.LENGTH_LONG).show();
            }
            else {
                new RetrieveElevation().execute();
                new RetrieveWeather().execute();
            }
        }
        else {
            Toast.makeText(getActivity(),"You have no available internet connection. Please try again",Toast.LENGTH_LONG).show();
        }
    }

    private class RetrieveWeather extends AsyncTask<Void,Void,JSONObject> {

        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.mException = null;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            StringBuilder urlString = new StringBuilder();
            urlString.append(openweatherAPIURL);
            urlString.append(mLatitudeText);
            urlString.append("&lon=");
            urlString.append(mLongitudeText);
            urlString.append("&units=metric");
            urlString.append("&apiKey=");
            urlString.append(openweatherAPIKey);
            HttpURLConnection objURLConnection = null;
            URL objURL;
            JSONObject objJSON = null;
            InputStream objInStream = null;

            try {
                objURL = new URL(urlString.toString());
                objURLConnection = (HttpURLConnection) objURL.openConnection();
                objURLConnection.setRequestMethod("GET");
                objURLConnection.setDoOutput(true);
                objURLConnection.setDoInput(true);
                objURLConnection.connect();
                objInStream = objURLConnection.getInputStream();
                BufferedReader objBReader = new BufferedReader(new InputStreamReader(objInStream));
                String line;
                String response = "";
                while ((line = objBReader.readLine()) != null) {
                    response += line;
                }
                objJSON = (JSONObject) new JSONTokener(response).nextValue();
            }
            catch (Exception e) {
                this.mException = e;
            }
            finally {
                if (objInStream != null) {
                    try {
                        objInStream.close();
                    }
                    catch (IOException ignored) {}
                }
                if (objURLConnection != null) {
                    objURLConnection.disconnect();
                }
            }
            return (objJSON);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (this.mException != null) {}
            try {
                mPlace = result.getString("name");
                JSONObject main = result.getJSONObject("main");
                mTemp = main.getString("temp");
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            textViewInformation.setText(String.format("You are currently in %s, currently " +
                    "%s meters above sea level, and the local temperature is %s degrees celsius.", mPlace, mAltitude, mTemp));
            mTemp += " " + (char) 0x2103;
            floatingActionButton.show();
        }
    }

    private class RetrieveElevation extends AsyncTask<Void,Void,JSONObject> {

        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mException = null;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            StringBuilder urlString = new StringBuilder();
            urlString.append(elevationAPIURUL);
            urlString.append(mLatitudeText);
            urlString.append(",");
            urlString.append(mLongitudeText);
            urlString.append("&key=");
            urlString.append(elevationAPIKey);

            HttpURLConnection objURLConnection = null;
            URL objURL;
            JSONObject objJSON = null;
            InputStream objInStream = null;

            try {
                objURL = new URL(urlString.toString());
                objURLConnection = (HttpURLConnection) objURL.openConnection();
                objURLConnection.setRequestMethod("GET");
                objURLConnection.setDoOutput(true);
                objURLConnection.setDoInput(true);
                objURLConnection.connect();
                objInStream = objURLConnection.getInputStream();
                BufferedReader objBReader = new BufferedReader(new InputStreamReader(objInStream));
                String line;
                String response = "";
                while ((line = objBReader.readLine()) != null) {
                    response += line;
                }
                objJSON = (JSONObject) new JSONTokener(response).nextValue();
            }
            catch (Exception e) {
                this.mException = e;
            }
            finally {
                if (objInStream != null) {
                    try {
                        objInStream.close();
                    }
                    catch (IOException ignored) {}
                }
                if (objURLConnection != null) {
                    objURLConnection.disconnect();
                }
            }
            return (objJSON);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (this.mException != null) {
            }
            try {
                JSONArray objJSONArray = result.optJSONArray("results");
                JSONObject objJSONObject = objJSONArray.getJSONObject(0);
                int intAltitude = objJSONObject.getInt("elevation");
                mAltitude = Integer.toString(intAltitude);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
