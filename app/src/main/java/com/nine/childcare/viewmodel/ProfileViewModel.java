package com.nine.childcare.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nine.childcare.model.User;

public class ProfileViewModel extends BaseViewModel{
    private MutableLiveData<User> currentUser = new MutableLiveData<>();

    public void signOut() {
        firebaseAuth.signOut();
        loggedOut.postValue(true);
    }

    // get user data from firebase
    public void getUserData() {
        DatabaseReference reference = firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    User user = snapshot.getValue(User.class);
                    currentUser.setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue(error.getMessage());
            }
        });

    }

    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<Boolean> getLoggedOut() {
        return loggedOut;
    }
}
