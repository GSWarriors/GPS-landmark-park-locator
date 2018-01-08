package com.example.sidpu.gpsapp;
//enhance to make app that finds parks near me
//to find park closest to me, need to parse through a file that has the location of all parks in california
//enter current gps location (given by current program), and then use the distance formula on all park locations in the parser,
//then print out the top 3 park's names with the smallest distances

//use reverse geocode method. Using the latitude and longitude of current location, find nearby addresses within
//5 miles (or so) of you. If the addresses have "park" in their name, then print out the entire address as a suggestion.
//only print up to 5 suggestions in the radius before breaking out of the loop.

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Button geoCode;
    private Button locButton;
    //private Button distance;
    private Button placeButton;
    private TextView textView;
    private TextView textView2;
    // private TextView distText;
    private TextView placeView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private geocodeExample geo;
    private geocodeExample geo2;
    private double lat1;
    private double lng1;
    private double lat2;
    private double lng2;
    private int PLACE_PICKER_REQUEST = 1;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        geoCode = (Button) findViewById(R.id.geoButton); //geocode button
        textView = (TextView) findViewById(R.id.locView);//geocode button textview
        geo = new geocodeExample(getApplicationContext());
        geo2 = new geocodeExample(getApplicationContext(), Locale.getDefault());
        geoCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                geo.geoCodeTest();
                //Log.i("lat/lng of the location", geo.lat + " " + geo.lng);
                //textView.append("\n " + geo.lat + " " + geo.lng);
                lat2 = geo.lat;
                lng2 = geo.lng;
                //lat2 and lng2 are the latitude and longitude of the location entered in code for finding the geocode
                //in the geocodeexample class
                textView.append("\n " + geo2.revgeoCodeTest(37.82, -122.4783));
                //appends the reverse geocoding (the current address) of the current location (lat1, lng1)
            }
        });

        placeButton = (Button) findViewById(R.id.placeButton);
        placeView = (TextView) findViewById(R.id.placeView);
        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //placeView.append
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(getApplicationContext());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

       /* locButton = (Button) findViewById(R.id.button2);    //request location button
        textView2 = (TextView) findViewById(R.id.locView2); //request location textview
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textView2.append("\n " + location.getLatitude() + " " + location.getLongitude());
                lat1 = location.getLatitude();
                lng1 = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }
            //gives gps location whenever your location is changed

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            return;

        } else {
            configureButton();
        }


        distance = (Button) findViewById(R.id.dist);
        distText = (TextView) findViewById(R.id.distView);

        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distText.append("\n " + findDistance(lat1, lng1, lat2, lng2) + " mile(s)");
            }
        });*/
    }
        //if permissions for accessing location are not yet granted, request those permissions
        //if the permissions are granted, run configureButton(), which gives you the current GPS location

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
            switch (requestCode) {
                case 10:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        configureButton();
                        return;
                    }
            }
        }
        //if the requestCode is the one that allows for the permissions to be granted, it is the right one, and should configure the button
    private void configureButton() {
        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }

        });

        //when the "Request location" button is clicked, this method gets the GPS coordinates
    }

    //finds distance between current location and location entered into geoCodeexample class
    public double findDistance(double lat1, double lng1, double lat2, double lng2) {
        double dist;
        double milesDist;

        dist = 69*(Math.sqrt(Math.pow(lat1 - lat2, 2)+Math.pow(lng1 - lng2, 2)));
        return dist;
    }


   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

            }
        }
    }















}
