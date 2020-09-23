package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    TextView lattitude;
    TextView longitude;
    TextView address;
    double lat=0.00000;
    double longi=0.00000;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        lattitude=findViewById(R.id.lattitude);
        longitude=findViewById(R.id.longitude);
        address=findViewById(R.id.address);
       findlocation();

      //setaddress();




    }

    private void findlocation() {

        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location=task.getResult();
                        if(location!=null)
                        {
                            try {
                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                lat = location.getLatitude();
                                longi = location.getLongitude();
                                Log.i("latitude", Double.toString(lat));
                                lattitude.setText("Latitude"+Double.toString(lat));
                                longitude.setText("Longitude:"+Double.toString(longi));

                                List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
                                String address1=addresses.get(0).getAddressLine(0);
                                address.setText("Address:"+address1);

                            }catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }



}