package com.nine.childcare.views.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.SignUpFragmentBinding;
import com.nine.childcare.viewmodel.SignUpViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SignUpFragment extends BaseFragment<SignUpFragmentBinding, SignUpViewModel> {
    private double currentLongitude, currentLatitude;
    private LatLng latLng;

    @Override
    protected Class<SignUpViewModel> getViewModelClass() {
        return SignUpViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sign_up_fragment;
    }

    @Override
    protected void initViews() {
        if (latLng != null) {
            Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
            try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        if (!locality.isEmpty()){
                            binding.edtSignUpAddress.setText(locality);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        binding.tvSignUpSignIn.setOnClickListener(v -> gotoSignInFragment());
        binding.btnSignUp.setOnClickListener(v -> signUpUser());
        binding.edtSignUpAddress.setOnClickListener(v -> gotoMapFragment());
        mViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    makeToast("User " + firebaseUser.getEmail() + " has been created");
                    gotoSignInFragment();
                }
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                makeToast(s);
            }
        });
    }


    private void signUpUser() {
        String name = binding.edtSignUpName.getText().toString().trim();
        String email = binding.edtSignUpEmail.getText().toString().trim();
        String password = binding.edtSignUpPassword.getText().toString().trim();
        String cPassword = binding.edtSignUpConfirmPassword.getText().toString().trim();
        String address = binding.edtSignUpAddress.getText().toString().trim();
        mViewModel.signUp(name, email, password, cPassword, latLng.latitude, latLng.longitude, address);
    }

//    private void handleLocation() {
//        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
//            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    Location location = task.getResult();
//                    if (location != null) {
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
//                    }
//                }
//            });
//        } else {
//            ActivityCompat.requestPermissions(requireActivity(),
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//        }

//    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    private void gotoSignInFragment() {
        callBack.callBack(Constants.KEY_SHOW_SIGN_IN, null);
    }

    private void gotoMapFragment() {
        callBack.callBack(Constants.KEY_SHOW_MAP, null);
    }
}
