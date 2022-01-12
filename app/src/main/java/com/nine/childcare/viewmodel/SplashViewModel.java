package com.nine.childcare.viewmodel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

public class SplashViewModel extends BaseViewModel{
    private MutableLiveData<Boolean> splashState;

    public SplashViewModel() {
        splashState = new MutableLiveData<>();
        changeState();
    }

    private void changeState() {
        new Handler().postDelayed(() -> splashState.setValue(true), 2000);
    }

    public MutableLiveData<Boolean> getSplashState() {
        return splashState;
    }
}
