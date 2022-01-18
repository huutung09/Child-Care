package com.nine.childcare.views.act;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.HomeActivityBinding;
import com.nine.childcare.receivers.AlarmReceiver;
import com.nine.childcare.viewmodel.HomeViewModel;
import com.nine.childcare.views.fragment.EnglishLearningFragment;
import com.nine.childcare.views.fragment.QuizFragment;
import com.nine.childcare.views.fragment.VideoFragment;

public class HomeActivity extends BaseActivity<HomeActivityBinding> {
    private HomeViewModel mViewModel;
    private AlarmManager alarmManager;
    private EnglishLearningFragment englishLearningFragment = new EnglishLearningFragment();
    private QuizFragment quizFragment = new QuizFragment();
    private VideoFragment videoFragment = new VideoFragment();
    private Fragment activeFragment;

    @Override
    protected void initViews() {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        replaceFragment(R.id.home_container_view, englishLearningFragment, false);
        setUpFragment();
        BottomNavigationView bnv = findViewById(R.id.navigation);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_learn:
//                        replaceFragment(R.id.home_container_view, englishLearningFragment, true);
                        showAndHideFragment(activeFragment, englishLearningFragment);
                        activeFragment = englishLearningFragment;
                        break;
                    case R.id.navigation_quiz:
//                        replaceFragment(R.id.home_container_view, quizFragment, true);
                        showAndHideFragment(activeFragment, quizFragment);
                        activeFragment = quizFragment;
                        break;
                    case R.id.navigation_video:
//                        replaceFragment(R.id.home_container_view, videoFragment, true);
                        showAndHideFragment(activeFragment, videoFragment);
                        activeFragment = videoFragment;
                        break;
                }
                return true;
            }
        });
        binding.btnLogout.setOnClickListener(v -> mViewModel.signOut());
        mViewModel.getLoggedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(HomeActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setUpFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.home_container_view, videoFragment, null).hide(videoFragment);
        ft.add(R.id.home_container_view, quizFragment, null).hide(quizFragment);
        ft.add(R.id.home_container_view, englishLearningFragment, null);
        activeFragment = englishLearningFragment;
        ft.commit();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    public void callBack(String key, Object data) {
    }

    public void makeAlarmNotification() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtCurrent = System.currentTimeMillis();
//        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtCurrent + Constants.ALARM_TIME * 1000, pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC, timeAtCurrent + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarmNotification(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onStart() {
        cancelAlarmNotification();
        super.onStart();
    }

    @Override
    protected void onStop() {
        makeAlarmNotification();
        super.onStop();
    }
}
