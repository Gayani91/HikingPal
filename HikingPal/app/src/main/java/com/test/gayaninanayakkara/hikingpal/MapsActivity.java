package com.test.gayaninanayakkara.hikingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity {
    String stringID,stringTitle,stringDesc,stringElevation;

    double longitude,latitude;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private static final String LOG_TAG = "HikingPal";

    private static final String SERVICE_URL = "http://www.json-generator.com/api/json/get/cecCsDhBSG?indent=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    public void setUpMap() {

        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddLocations();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrive locations", e);
                    return;
                }
            }
        }).start();
    }

    protected void retrieveAndAddLocations() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());


            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }

    void createMarkersFromJson(String json) throws JSONException {

        JSONObject jsonObj=new JSONObject();
        int i;
        JSONArray jsonArray = new JSONArray(json);

        for (i= 0; i < jsonArray.length(); i++) {
            jsonObj = jsonArray.getJSONObject(i);
            mMap.addMarker(new MarkerOptions()
                            .title(jsonObj.getString("name"))
                            .snippet("Elevation: " + jsonObj.getString("elevation"))
                            .position(new LatLng(jsonObj.getDouble("latitude"), jsonObj.getDouble("longitude"))
                            )
            );

            stringID= jsonArray.getJSONObject(i).getString("id");
            stringTitle= jsonArray.getJSONObject(i).getString("name");
            stringDesc= jsonArray.getJSONObject(i).getString("description");
            stringElevation= jsonArray.getJSONObject(i).getString("elevation");
            longitude=jsonArray.getJSONObject(i).getDouble("longitude");
            Log.v("hh",""+longitude);
            latitude=jsonArray.getJSONObject(i).getDouble("latitude");

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent = new Intent(MapsActivity.this,DetailActivity.class);

                            intent.putExtra("compTitle",stringTitle);
                            intent.putExtra("compDesc",stringDesc);
                            intent.putExtra("compEle",stringElevation);

                            String strLon=""+longitude;
                            intent.putExtra("compLong",strLon);

                            String strLat=""+latitude;
                            intent.putExtra("compLat",strLat);

                            startActivity(intent);
                        }

                    });
                }
            });
        }
    }
}
