package com.nine.childcare.views.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nine.childcare.R;
import com.nine.childcare.adapters.VideoAdapter;
import com.nine.childcare.databinding.VideoFragmentBinding;
import com.nine.childcare.interfaces.VideoAdapterListener;
import com.nine.childcare.model.ItemYoutube;
import com.nine.childcare.viewmodel.VideoViewModel;

import java.util.ArrayList;

public class VideoFragment extends BaseFragment<VideoFragmentBinding, VideoViewModel> {

    private VideoAdapter videoAdapter;
    private String searchTerm = "";
    private YoutubePlayerFragment youtubePlayerFragment;

    @Override
    protected Class<VideoViewModel> getViewModelClass() {
        return VideoViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_fragment;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        binding.videoRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.videoRecycleView.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(mViewModel.getItemYoutubeLiveData().getValue());
        binding.videoRecycleView.setAdapter(videoAdapter);
        videoAdapter.setListener(new VideoAdapterListener() {
            @Override
            public void onClick(ItemYoutube youtubeVideo) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                // if first time play video, add new fragment
                //second time just change video
                if (youtubePlayerFragment == null) {
                    youtubePlayerFragment = new YoutubePlayerFragment();
                    youtubePlayerFragment.setCurrentVideo(youtubeVideo);
                    fragmentTransaction.add(R.id.youtube_player_container, youtubePlayerFragment, "tag");
                    fragmentTransaction.addToBackStack("add");
                } else {
                    youtubePlayerFragment.changeVid(youtubeVideo);
                    fragmentTransaction.show(youtubePlayerFragment);
                }
                fragmentTransaction.commit();

            }
        });


        // if scroll at bottom of recycle view -> get new video
        binding.viewNestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    mViewModel.getVideoList(searchTerm, false);
                    binding.videoProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnVideoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTerm = binding.edtVideoSearch.getText().toString().trim();
                mViewModel.getVideoList(searchTerm, true);
                binding.videoProgressBar.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getItemYoutubeLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ItemYoutube>>() {
            @Override
            public void onChanged(ArrayList<ItemYoutube> itemYoutubes) {
                binding.videoProgressBar.setVisibility(View.GONE);
                videoAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::makeToast);

    }



}
