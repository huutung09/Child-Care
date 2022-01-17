package com.nine.childcare.views.fragment;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.interfaces.OnActionCallBack;



public class MapsFragment extends Fragment {
    private OnActionCallBack callBack;
    private GoogleMap mMap;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private OnMapReadyCallback readyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap = googleMap;
            updateLocationUI();
            getDeviceLocation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(readyCallback);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        view.findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = mMap.getCameraPosition().target;
                gotoSignUpFragment(latLng);
            }
        });
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
//            ActivityCompat.requestPermissions(requireActivity(),
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            requestPermissions(new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                LatLng current = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(current).title("You are here"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(Constants.DEFAULT_ZOOM), 2000, null);
                            }
                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }





    private void handleCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    lastKnownLocation  = task.getResult();
                    if (lastKnownLocation != null) {
                        LatLng current = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(current).title("You are here"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }

    private void gotoSignUpFragment(LatLng latLng){
        callBack.callBack(Constants.KEY_SHOW_SIGN_UP, latLng);
    }

}