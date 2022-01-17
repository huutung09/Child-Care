package com.nine.childcare.viewmodel;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends BaseViewModel {
    private MutableLiveData<FirebaseUser> userMutableLiveData = new MutableLiveData<>();

    public SignInViewModel() {
        if (firebaseAuth.getCurrentUser() != null) {
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOut.postValue(false);
        }
    }

    public void signIn(String email, String password) {
        if (email.isEmpty()){
            errorMessage.postValue("Please enter email!");
        } else if (password.isEmpty()){
            errorMessage.postValue("Please enter password!");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            errorMessage.postValue("Please enter valid email");
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                            } else {
                                errorMessage.postValue(task.getException().toString());
                            }
                        }
                    });
        }
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
