package com.nine.childcare.views.act;

import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.SignActivityBinding;
import com.nine.childcare.views.fragment.MapsFragment;
import com.nine.childcare.views.fragment.SignInFragment;
import com.nine.childcare.views.fragment.SignUpFragment;

public class SignActivity extends BaseActivity<SignActivityBinding> {
    private final SignInFragment signInFragment = new SignInFragment();
    private final SignUpFragment signUpFragment = new SignUpFragment();
    private final MapsFragment mapsFragment = new MapsFragment();


    @Override
    public void callBack(String key, Object data) {
        switch (key) {
            case Constants.KEY_SHOW_SIGN_IN:
                signInFragment.setCallBack(this);
                replaceFragment(R.id.sign_container_view, signInFragment, false);
                break;
            case Constants.KEY_SHOW_SIGN_UP:
                signUpFragment.setCallBack(this);
                if (data != null) {
                    signUpFragment.setLatLng((LatLng) data);
                }
                replaceFragment(R.id.sign_container_view, signUpFragment, true);
                break;
            case Constants.KEY_SHOW_MAP:
                mapsFragment.setCallBack(this);
                replaceFragment(R.id.sign_container_view, mapsFragment, true);
                break;
            case Constants.KEY_SHOW_HOME_ACT:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
        }
    }

    @Override
    protected void initViews() {
        signInFragment.setCallBack(this);
        signUpFragment.setCallBack(this);
        mapsFragment.setCallBack(this);
        replaceFragment(R.id.sign_container_view, signInFragment, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sign_activity;
    }
}
