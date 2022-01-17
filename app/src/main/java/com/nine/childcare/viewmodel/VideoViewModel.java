package com.nine.childcare.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nine.childcare.Constants;
import com.nine.childcare.api.YoutubeApiClient;
import com.nine.childcare.model.FullYoutube;
import com.nine.childcare.model.ItemYoutube;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoViewModel extends BaseViewModel{
    private MutableLiveData<ArrayList<ItemYoutube>> itemYoutubeLiveData = new MutableLiveData<>();
    private ArrayList<ItemYoutube> itemYoutubeArrayList = new ArrayList<>();

    public VideoViewModel() {
        itemYoutubeLiveData.setValue(itemYoutubeArrayList);
    }

    public void getVideoList(String searchTerm) {
        itemYoutubeArrayList.clear();
        YoutubeApiClient.getYoutubeApiInterface().getYoutubeData(Constants.API_KEY, "snippet", searchTerm)
                .enqueue(new Callback<FullYoutube>() {
                    @Override
                    public void onResponse(Call<FullYoutube> call, Response<FullYoutube> response) {
                        FullYoutube fullYoutube = response.body();
                        if (fullYoutube != null) {
                            itemYoutubeArrayList.addAll(fullYoutube.getItems());
                        }
                        itemYoutubeLiveData.setValue(itemYoutubeArrayList);
                    }

                    @Override
                    public void onFailure(Call<FullYoutube> call, Throwable t) {
                        Log.e("out", "onFailure: ");
                    }
                });
    }

    public MutableLiveData<ArrayList<ItemYoutube>> getItemYoutubeLiveData() {
        return itemYoutubeLiveData;
    }
}
