package com.nine.childcare.views.fragment;

import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.nine.childcare.Constants;
import com.nine.childcare.R;
import com.nine.childcare.adapters.VideoAdapter;
import com.nine.childcare.databinding.VideoFragmentBinding;
import com.nine.childcare.model.ItemYoutube;
import com.nine.childcare.viewmodel.VideoViewModel;

import java.util.ArrayList;

public class VideoFragment extends BaseFragment<VideoFragmentBinding, VideoViewModel> {

    private VideoAdapter videoAdapter;
    private LinearLayoutManager linearLayoutManager;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private Boolean isScrolling = false;
    private String searchTerm = "";


    @Override
    protected Class<VideoViewModel> getViewModelClass() {
        return VideoViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_fragment;
    }

    @Override
    protected void initViews() {

        binding.videoRecycleView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.videoRecycleView.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(mViewModel.getItemYoutubeLiveData().getValue());
        binding.videoRecycleView.setAdapter(videoAdapter);

        binding.videoRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (visibleItemCount + pastVisibleItems == totalItemCount)) {
                    isScrolling = false;
                    makeToast("end");
                    mViewModel.getVideoList(searchTerm, false);
                }
            }
        });

        binding.btnVideoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTerm = binding.edtVideoSearch.getText().toString().trim();
                mViewModel.getVideoList(searchTerm, true);
            }
        });

        YouTubePlayerSupportFragmentX youTubePlayerSupportFragmentX = (YouTubePlayerSupportFragmentX) getChildFragmentManager()
                        .findFragmentById(R.id.youtube_fragment);

        youTubePlayerSupportFragmentX.initialize(Constants.API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo("iM6HcjOiDPk");
                        youTubePlayer.play();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        makeToast("failed");
                    }
                });

//        binding.youtubePlayer.initialize(Constants.API_KEY,
//                new YouTubePlayer.OnInitializedListener() {
//                    @Override
//                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                        youTubePlayer.loadVideo("iM6HcjOiDPk");
//                        youTubePlayer.play();
//                    }
//
//                    @Override
//                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                        makeToast("failed");
//                    }
//                });

        mViewModel.getItemYoutubeLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ItemYoutube>>() {
            @Override
            public void onChanged(ArrayList<ItemYoutube> itemYoutubes) {
                videoAdapter.notifyDataSetChanged();
            }
        });
    }
}
