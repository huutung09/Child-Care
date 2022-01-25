package com.nine.childcare.views.act;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.HomeActivityBinding;
import com.nine.childcare.receivers.AlarmReceiver;
import com.nine.childcare.views.fragment.EnglishLearningFragment;
import com.nine.childcare.views.fragment.ProfileFragment;
import com.nine.childcare.views.fragment.QuizFragment;
import com.nine.childcare.views.fragment.VideoFragment;

public class HomeActivity extends BaseActivity<HomeActivityBinding> {
    private AlarmManager alarmManager;
    private final EnglishLearningFragment englishLearningFragment = new EnglishLearningFragment();
    private final QuizFragment quizFragment = new QuizFragment();
    private final VideoFragment videoFragment = new VideoFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private Fragment activeFragment;

    @Override
    protected void initViews() {
        setUpFragment();
        BottomNavigationView bnv = findViewById(R.id.navigation);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_learn:
                        showAndHideFragment(activeFragment, englishLearningFragment);
                        activeFragment = englishLearningFragment;
                        break;
                    case R.id.navigation_quiz:
                        showAndHideFragment(activeFragment, quizFragment);
                        activeFragment = quizFragment;
                        break;
                    case R.id.navigation_video:
                        showAndHideFragment(activeFragment, videoFragment);
                        activeFragment = videoFragment;
                        break;
                    case R.id.navigation_profile:
                        showAndHideFragment(activeFragment, profileFragment);
                        activeFragment = profileFragment;
                        break;
                }
                return true;
            }
        });

    }

    // add all fragment to container but hide it
    public void setUpFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.home_container_view, videoFragment, null).hide(videoFragment);
        ft.add(R.id.home_container_view, quizFragment, null).hide(quizFragment);
        ft.add(R.id.home_container_view, profileFragment, null).hide(profileFragment);
        ft.add(R.id.home_container_view, englishLearningFragment, null);
        profileFragment.setCallBack(this);
        activeFragment = englishLearningFragment;
        ft.commit();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    public void callBack(String key, Object data) {
        if (key.equals(Constants.KEY_SHOW_SIGN_ACT)) {
            Intent intent = new Intent(HomeActivity.this, SignActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void makeAlarmNotification() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long timeAtCurrent = System.currentTimeMillis();
        alarmManager.setInexactRepeating(AlarmManager.RTC, timeAtCurrent + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarmNotification(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
