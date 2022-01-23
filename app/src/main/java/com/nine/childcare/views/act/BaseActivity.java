package com.nine.childcare.views.act;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nine.childcare.interfaces.OnActionCallBack;

public abstract class BaseActivity<BD extends ViewDataBinding> extends AppCompatActivity
        implements OnActionCallBack {

    protected BD binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initViews();
    }


    protected abstract void initViews();

    protected abstract int getLayoutId();

    protected void replaceFragment(int layoutID, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layoutID, fragment);
        if (addToBackStack) {
            ft.addToBackStack("add");
        }
        ft.commit();
    }

    protected void showAndHideFragment(Fragment activeFragment, Fragment usingFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(activeFragment).show(usingFragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
    }
}
