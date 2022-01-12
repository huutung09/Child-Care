package com.nine.childcare.views.act;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nine.childcare.interfaces.OnActionCallBack;

public abstract class BaseActivity<BD extends ViewDataBinding> extends AppCompatActivity
        implements OnActionCallBack {

    protected  BD binding;
//    protected  VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initViews();
    }

//    protected abstract Class<VM> getViewModelClass();

    protected  abstract void initViews();
    protected abstract int getLayoutId();

    protected void showFragment(int layoutID, Fragment fragment, boolean addToBackStack){
        if (!isFinishing() && !isDestroyed()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(layoutID, fragment);
            if (addToBackStack){
                ft.addToBackStack("add");
            }
            ft.commit();
        }
    }
}
