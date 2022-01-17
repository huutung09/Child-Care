package com.nine.childcare.viewmodel;

import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends BaseViewModel{

    public void signOut() {
        firebaseAuth.signOut();
        loggedOut.postValue(true);
    }

    public MutableLiveData<Boolean> getLoggedOut() {
        return loggedOut;
    }
}
