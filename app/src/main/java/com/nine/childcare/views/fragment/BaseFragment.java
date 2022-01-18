package com.nine.childcare.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nine.childcare.interfaces.OnActionCallBack;

public abstract class BaseFragment<K extends ViewDataBinding, V extends ViewModel> extends Fragment {
    protected OnActionCallBack callBack;

    protected K binding;
    protected V mViewModel;

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(getViewModelClass());
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initViews();
        return binding.getRoot();
    }

    public void makeToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected abstract Class<V> getViewModelClass();

    protected abstract int getLayoutId();

    protected abstract void initViews();
}
