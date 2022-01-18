package com.nine.childcare.api;

import com.nine.childcare.Constants;
import com.nine.childcare.model.FullYoutube;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApiInterface {
    @GET(Constants.SEARCH)
    Call<FullYoutube> getYoutubeData(@Query("key") String key,
                                     @Query("part") String part,
                                     @Query("q") String q,
                                    @Query("pageToken") String pageToken);
}
