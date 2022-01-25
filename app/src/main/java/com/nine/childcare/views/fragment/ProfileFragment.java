package com.nine.childcare.views.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.ProfileFragmentBinding;
import com.nine.childcare.model.User;
import com.nine.childcare.viewmodel.ProfileViewModel;


public class ProfileFragment extends BaseFragment<ProfileFragmentBinding, ProfileViewModel> {
    @Override
    protected Class<ProfileViewModel> getViewModelClass() {
        return ProfileViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.profile_fragment;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        mViewModel.getUserData();
        binding.btnLogout.setOnClickListener(v -> mViewModel.signOut());
        mViewModel.getLoggedOut().observe(getViewLifecycleOwner(), aBoolean -> gotoSignAct());
        mViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.profileUserName.setText(user.getName());
                binding.profileEmail.setText(user.getEmail());
                binding.profileAddress.setText(user.getAddress());
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::makeToast);
    }

    private void gotoSignAct() {
        callBack.callBack(Constants.KEY_SHOW_SIGN_ACT, null);
    }
}
