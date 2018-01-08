package com.example.sidpu.gpsapp;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by sidpu on 12/18/2016.
 */

public class geocodeExample {

    static double lat;
    static double lng;
    Context myContext;
    Locale myLocale;

    public geocodeExample(Context c) {

        myContext = c;
    }

    public geocodeExample(Context c, Locale loc) {

        myContext = c;
        myLocale = loc;

    }

    public void geoCodeTest() {
        try {
            Geocoder selected_place_geocoder = new Geocoder(myContext);
            List<Address> address;
            address = selected_place_geocoder.getFromLocationName("1111 S Figueroa St, Los Angeles, CA 90015", 5);

            if (address == null) {
                //do nothing

            } else {
                Address location = address.get(0);
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //reverse geocoding: returns the address given the latitude and longitude of a location

    public String revgeoCodeTest(double lat1, double lng1) {
        Geocoder geo2 = new Geocoder(myContext, myLocale);
        String mylocation;
        if (Geocoder.isPresent()) {
            try {
                List<Address> addresses = geo2.getFromLocation(lat1, lng1, 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String addressText = String.format("%s", "%s", "%s",
                            // If there's a street address, add it
                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                            // Locality is usually a city
                            address.getLocality(),
                            // The country of the address
                            address.getCountryName());

                           // address.getFeatureName());
                    mylocation = "Lattitude: " + lat1 + " Longitude: " + lng1 + "\nAddress: " + addressText;
                    return mylocation;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return " ";
    }



}
