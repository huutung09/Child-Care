package com.nine.childcare.views.fragment;

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
    protected void initViews() {
        mViewModel.changeState();
        mViewModel.getSplashState().observe(getViewLifecycleOwner(), aBoolean -> gotoSignAct());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void gotoSignAct(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_ACT, null);
    }
}
