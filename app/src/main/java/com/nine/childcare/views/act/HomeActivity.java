package com.nine.childcare.views.act;

import com.nine.childcare.R;
import com.nine.childcare.databinding.HomeActivityBinding;
import com.nine.childcare.views.fragment.EnglishLearningFragment;

public class HomeActivity extends BaseActivity<HomeActivityBinding> {
    @Override
    protected void initViews() {
        EnglishLearningFragment englishLearningFragment = new EnglishLearningFragment();
        englishLearningFragment.setCallBack(this);
        showFragment(R.id.home_container_view, englishLearningFragment, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    public void callBack(String key, Object data) {

    }
}
