package com.nine.childcare.views.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.List;

public class MapsFragment extends Fragment {
    private OnActionCallBack callBack;
    private GoogleMap mMap;

    private OnMapReadyCallback readyCallback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap = googleMap;
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
        view.findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = mMap.getCameraPosition().target;
//                Geocoder geocoder = new Geocoder(requireActivity());
//                try {
//                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    if (addressList != null && addressList.size() > 0) {
//                        String locality = addressList.get(0).getAddressLine(0);
//                        if (!locality.isEmpty()){
//                            Toast.makeText(requireActivity(), locality , Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                gotoSignUpFragment(latLng);
            }
        });
        handleCurrentLocation();
    }

    private void handleCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
//                        try {
//                            Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(
//                                    location.getLatitude(), location.getLongitude(), 1
//                            );
//                            binding.edtSignUpAddress.setText(addresses.get(0).getAddressLine(0));
//                            currentLatitude = addresses.get(0).getLatitude();
//                            currentLongitude = addresses.get(0).getLongitude();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        makeToast("no location");
                        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
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