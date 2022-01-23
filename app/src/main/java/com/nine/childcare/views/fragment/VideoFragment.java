package com.nine.childcare.views.fragment;

import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nine.childcare.R;
import com.nine.childcare.adapters.VideoAdapter;
import com.nine.childcare.databinding.VideoFragmentBinding;
import com.nine.childcare.interfaces.VideoAdapterListener;
import com.nine.childcare.model.ItemYoutube;
import com.nine.childcare.viewmodel.VideoViewModel;

import java.util.ArrayList;

public class VideoFragment extends BaseFragment<VideoFragmentBinding, VideoViewModel> {

    private VideoAdapter videoAdapter;
    private LinearLayoutManager linearLayoutManager;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private Boolean isScrolling = false;
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
    protected void initViews() {

        binding.videoRecycleView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(requireContext());
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
                    youtubePlayerFragment = YoutubePlayerFragment.newInstance();
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

        binding.videoRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            // if scroll at bottom of recycle view -> get new video
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (visibleItemCount + pastVisibleItems == totalItemCount)) {
                    isScrolling = false;
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
        mViewModel.getItemYoutubeLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ItemYoutube>>() {
            @Override
            public void onChanged(ArrayList<ItemYoutube> itemYoutubes) {
                videoAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::makeToast);

    }



}
