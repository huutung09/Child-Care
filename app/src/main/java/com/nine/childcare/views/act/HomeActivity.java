package com.nine.childcare.views.act;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nine.childcare.R;
import com.nine.childcare.databinding.HomeActivityBinding;
import com.nine.childcare.views.fragment.EnglishLearningFragment;
import com.nine.childcare.views.fragment.QuizFragment;

public class HomeActivity extends BaseActivity<HomeActivityBinding> {
    @Override
    protected void initViews() {
        EnglishLearningFragment englishLearningFragment = new EnglishLearningFragment();
        englishLearningFragment.setCallBack(this);
        showFragment(R.id.home_container_view, englishLearningFragment, false);
        BottomNavigationView bnv = findViewById(R.id.navigation);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_learn:
                        EnglishLearningFragment englishLearningFragment1 = new EnglishLearningFragment();
                        showFragment(R.id.home_container_view, englishLearningFragment, true);
                        break;
                    case R.id.navigation_quiz:
                        QuizFragment quizFragment = new QuizFragment();
                        showFragment(R.id.home_container_view, quizFragment, true);
                        break;
                }
                return true;
            }
        });
    }



    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    public void callBack(String key, Object data) {

    }
}
