package com.nine.childcare.views.act;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.SignActivityBinding;
import com.nine.childcare.views.fragment.MapsFragment;
import com.nine.childcare.views.fragment.SignInFragment;
import com.nine.childcare.views.fragment.SignUpFragment;

public class SignActivity extends BaseActivity<SignActivityBinding> {

    @Override
    public void callBack(String key, Object data) {
        switch (key) {
            case Constants.KEY_SHOW_SIGN_IN:
                SignInFragment signInFragment = new SignInFragment();
                signInFragment.setCallBack(this);
                showFragment(R.id.sign_container_view, signInFragment, false);
                break;
            case Constants.KEY_SHOW_SIGN_UP:
                SignUpFragment signUpFragment = new SignUpFragment();
                signUpFragment.setCallBack(this);
                if (data != null) {
                    signUpFragment.setLatLng((LatLng) data);
                }
                showFragment(R.id.sign_container_view, signUpFragment, false);
                break;
            case Constants.KEY_SHOW_MAP:
                MapsFragment mapsFragment = new MapsFragment();
                mapsFragment.setCallBack(this);
                showFragment(R.id.sign_container_view, mapsFragment, false);
                break;
            case Constants.KEY_SHOW_HOME_ACT:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                break;
        }
    }

    @Override
    protected void initViews() {
        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setCallBack(this);
        showFragment(R.id.sign_container_view, signInFragment, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sign_activity;
    }
}
