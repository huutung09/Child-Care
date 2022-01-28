package com.nine.childcare.views.act;

import android.content.Intent;

import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.ActivityMainBinding;
import com.nine.childcare.receivers.AlarmReceiver;
import com.nine.childcare.views.fragment.SplashFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void initViews() {
        SplashFragment splashFragment = new SplashFragment();
        splashFragment.setCallBack(this);
        replaceFragment(R.id.container_view, splashFragment, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void callBack(String key, Object data) {
        if (key.equals(Constants.KEY_SHOW_SIGN_ACT)) {
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
            finish();
        }
    }
}