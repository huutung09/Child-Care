package com.nine.childcare.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.nine.childcare.Constants;
import com.nine.childcare.api.YoutubeApiClient;
import com.nine.childcare.model.FullYoutube;
import com.nine.childcare.model.ItemYoutube;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<ItemYoutube>> itemYoutubeLiveData = new MutableLiveData<>();
    private ArrayList<ItemYoutube> itemYoutubeArrayList = new ArrayList<>();
    private String nextPage = "";
    Call<FullYoutube> call;

    public VideoViewModel() {
        itemYoutubeLiveData.setValue(itemYoutubeArrayList);
    }

    public void getVideoList(String searchTerm, Boolean newSearch) {
        if (newSearch) {
            nextPage = "";
            itemYoutubeArrayList.clear();
        }
        call = YoutubeApiClient.getYoutubeApiInterface().getYoutubeData(Constants.API_KEY, "snippet", searchTerm, nextPage);
        call.enqueue(new Callback<FullYoutube>() {
            @Override
            public void onResponse(@NonNull Call<FullYoutube> call, @NonNull Response<FullYoutube> response) {
                FullYoutube fullYoutube = response.body();
                if (fullYoutube != null) {
                    itemYoutubeArrayList.addAll(fullYoutube.getItems());
                    nextPage = fullYoutube.getNextPageToken();
                }
                itemYoutubeLiveData.setValue(itemYoutubeArrayList);
            }

            @Override
            public void onFailure(@NonNull Call<FullYoutube> call, @NonNull Throwable t) {
                Log.e("out", "onFailure: ");
            }
        });
    }

    public MutableLiveData<ArrayList<ItemYoutube>> getItemYoutubeLiveData() {
        return itemYoutubeLiveData;
    }
}
