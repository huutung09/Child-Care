package com.nine.childcare.views.fragment;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nine.childcare.R;
import com.nine.childcare.adapters.VideoAdapter;
import com.nine.childcare.databinding.VideoFragmentBinding;
import com.nine.childcare.model.ItemYoutube;
import com.nine.childcare.viewmodel.VideoViewModel;

import java.util.ArrayList;

public class VideoFragment extends BaseFragment<VideoFragmentBinding, VideoViewModel> {

    private VideoAdapter videoAdapter;

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
        binding.videoRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        videoAdapter = new VideoAdapter(mViewModel.getItemYoutubeLiveData().getValue());
        binding.videoRecycleView.setAdapter(videoAdapter);

        binding.btnVideoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = binding.edtVideoSearch.getText().toString().trim();
                mViewModel.getVideoList(searchTerm);
            }
        });

        mViewModel.getItemYoutubeLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ItemYoutube>>() {
            @Override
            public void onChanged(ArrayList<ItemYoutube> itemYoutubes) {
                videoAdapter.notifyDataSetChanged();
            }
        });
    }
}
