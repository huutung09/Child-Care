package com.nine.childcare.api;

import com.nine.childcare.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeApiClient {
    private static YoutubeApiInterface youtubeApiInterface = null;

    public static YoutubeApiInterface getYoutubeApiInterface() {
        if (youtubeApiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.YOUTUBE_BASE_URL)
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            youtubeApiInterface = retrofit.create(YoutubeApiInterface.class);
        }
        return youtubeApiInterface;
    }
}
