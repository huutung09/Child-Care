package com.nine.childcare.views.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.nine.childcare.R;
import com.nine.childcare.databinding.YoutubePlayerFragmentBinding;
import com.nine.childcare.viewmodel.VideoViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

public class YoutubePlayerFragment extends BaseFragment<YoutubePlayerFragmentBinding, VideoViewModel> {
    private String youtubeVideoId;
    private YouTubePlayerSupportFragmentX youTubePlayerSupportFragmentX;
    private YouTubePlayer mYouTubePlayer;

    @Override
    protected Class<VideoViewModel> getViewModelClass() {
        return VideoViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.youtube_player_fragment;
    }

    @Override
    protected void initViews() {
        getLifecycle().addObserver(binding.youtubeFragment);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYouTubePlayer.pause();
                YoutubePlayerFragment.this.getParentFragmentManager().beginTransaction().hide(YoutubePlayerFragment.this).commit();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        youTubePlayerSupportFragmentX = (YouTubePlayerSupportFragmentX) getChildFragmentManager()
//                .findFragmentById(R.id.youtube_fragment);
//        youTubePlayerSupportFragmentX.initialize(Constants.API_KEY,
//                new YouTubePlayer.OnInitializedListener() {
//                    @Override
//                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                        youTubePlayer.loadVideo(youtubeVideoId);
//                        youTubePlayer.play();
//                    }
//
//                    @Override
//                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                        makeToast("failed");
//                    }
//                });
        binding.youtubeFragment.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                mYouTubePlayer = youTubePlayer;
                youTubePlayer.loadVideo(youtubeVideoId, 0);
            }
        });
        binding.youtubeMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                MotionLayout containerMotionLayout = getParentFragment().getView().findViewById(R.id.video_motion_layout);
                containerMotionLayout.setProgress(progress);
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
//        binding.youtubeMotionLayout.transitionToEnd();
    }

    public static YoutubePlayerFragment newInstance() {
        return new YoutubePlayerFragment();
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public void changeVid(String youtubeVideoId) {
        mYouTubePlayer.loadVideo(youtubeVideoId, 0);
        binding.youtubeMotionLayout.transitionToStart();
    }
}
