package com.nine.childcare.viewmodel;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.nine.childcare.model.User;

public class SignUpViewModel extends BaseViewModel{
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private DatabaseReference databaseReference;

    public SignUpViewModel() {
        userMutableLiveData = new MutableLiveData<>();
    }

    public void signUp(String name, String email, String password, String cPassword, double latitude, double longitude, String address) {
        if (!password.equals(cPassword)){
            errorMessage.setValue("Password and confirm password must be the same");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            errorMessage.setValue("Please enter valid email");
        } else  if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            errorMessage.setValue("Please fill out the form");
        } else if (address.isEmpty()) {
            errorMessage.setValue("Please allow location!");
        }else {
            handleSignUpUser(name, email,password, latitude, longitude, address);
        }
    }

    private void handleSignUpUser(String name, String email, String password, double latitude, double longitude, String address) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            databaseReference = firebaseDatabase.getReference("users").child(firebaseAuth.getUid());
                            User user = new User(firebaseAuth.getUid(), name, email, address, longitude, latitude, 0);
                            databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                                    } else {
                                        errorMessage.postValue(task.getException().toString());
                                    }
                                }
                            });
                        } else {
                            errorMessage.postValue(task.getException().toString());
                        }
                    }
                });
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
