package com.nine.childcare.views.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseUser;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.SignInFragmentBinding;
import com.nine.childcare.viewmodel.SignInViewModel;

public class SignInFragment extends BaseFragment<SignInFragmentBinding, SignInViewModel> {
    @Override
    protected Class<SignInViewModel> getViewModelClass() {
        return SignInViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sign_in_fragment;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        binding.tvSignInCreateAccount.setOnClickListener(v -> gotoSignUpFragment());
        binding.btnSignIn.setOnClickListener(v -> signInUser());

        mViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    gotoHomeAct();
                }
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    makeToast(s);
                }
            }
        });
    }

    private void signInUser() {
        String email = binding.edtSignInEmail.getText().toString().trim();
        String password = binding.edtSignInPassword.getText().toString().trim();
        mViewModel.signIn(email, password);
    }


    private void gotoSignUpFragment(){
        callBack.callBack(Constants.KEY_SHOW_SIGN_UP, null);
    }

    private void gotoHomeAct() {
        callBack.callBack(Constants.KEY_SHOW_HOME_ACT, null);
    }
}
