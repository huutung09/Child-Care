package com.nine.childcare.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.nine.childcare.App;
import com.nine.childcare.manager.DatabaseManager;

public class BaseViewModel extends ViewModel {
    protected final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    protected final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    protected final DatabaseManager databaseManager = App.getInstance().getDatabaseManager();
    protected MutableLiveData<Boolean> loggedOut = new MutableLiveData<>();
    protected MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
