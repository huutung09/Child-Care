package com.nine.childcare.views.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.SignUpFragmentBinding;
import com.nine.childcare.viewmodel.SignUpViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SignUpFragment extends BaseFragment<SignUpFragmentBinding, SignUpViewModel> {
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
    protected void initViews(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("name").trim();
            String email = savedInstanceState.getString("email").trim();
            String password = savedInstanceState.getString("password").trim();
            String cPassword = savedInstanceState.getString("cPassword").trim();
            binding.edtSignUpName.setText(name);
            binding.edtSignUpEmail.setText(email);
            binding.edtSignUpPassword.setText(password);
            binding.edtSignUpConfirmPassword.setText(cPassword);
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
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::makeToast);
    }


    private void signUpUser() {
        mViewModel.signUp( binding.edtSignUpName.getText().toString().trim(),
                binding.edtSignUpEmail.getText().toString().trim(),
                binding.edtSignUpPassword.getText().toString().trim(),
                binding.edtSignUpConfirmPassword.getText().toString().trim(),
                latLng.latitude, latLng.longitude,
                binding.edtSignUpAddress.getText().toString().trim());
        hideKeyBoard();
    }

    // get string location from latitude and longitude
    public void getAddress() {
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
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    private void gotoSignInFragment() {
        callBack.callBack(Constants.KEY_SHOW_SIGN_IN, null);
    }

    private void gotoMapFragment() {
        callBack.callBack(Constants.KEY_SHOW_MAP, null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("name", binding.edtSignUpName.getText().toString().trim());
        outState.putString("email", binding.edtSignUpEmail.getText().toString().trim());
        outState.putString("password", binding.edtSignUpPassword.getText().toString().trim());
        outState.putString("cPassword", binding.edtSignUpConfirmPassword.getText().toString().trim());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getAddress();
    }

}
