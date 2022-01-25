package com.nine.childcare.views.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.SplashFragmentBinding;
import com.nine.childcare.viewmodel.SplashViewModel;

public class SplashFragment extends BaseFragment<SplashFragmentBinding, SplashViewModel> {
    @Override
    protected Class<SplashViewModel> getViewModelClass() {
        return SplashViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_fragment;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        mViewModel.changeState();
        mViewModel.getSplashState().observe(getViewLifecycleOwner(), aBoolean -> gotoSignAct());
    }

    private void gotoSignAct(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_ACT, null);
    }
}
