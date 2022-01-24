package com.nine.childcare.views.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeInitializationResult;

import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.databinding.YoutubePlayerFragmentBinding;
import com.nine.childcare.model.ItemYoutube;
import com.nine.childcare.viewmodel.VideoViewModel;

public class YoutubePlayerFragment extends BaseFragment<YoutubePlayerFragmentBinding, VideoViewModel> {
    private ItemYoutube currentVideo;
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
    protected void initViews(@Nullable Bundle savedInstanceState) {
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
        youTubePlayerSupportFragmentX = (YouTubePlayerSupportFragmentX) getChildFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        youTubePlayerSupportFragmentX.initialize(Constants.API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        mYouTubePlayer = youTubePlayer;
                        changeVid(currentVideo);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        makeToast("failed");
                    }
                });
    }


    public void setCurrentVideo(ItemYoutube currentVideo) {
        this.currentVideo = currentVideo;
    }

    public void changeVid(ItemYoutube video) {
        mYouTubePlayer.loadVideo(video.getId().getVideoId());
        mYouTubePlayer.play();
        binding.youtubeTitle.setText(video.getSnippet().getTitle());
        binding.youtubeDescription.setText(video.getSnippet().getDescription());
        binding.youtubeMotionLayout.transitionToStart();
    }
}
