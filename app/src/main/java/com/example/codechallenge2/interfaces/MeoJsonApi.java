package com.example.codechallenge2.interfaces;

import com.example.codechallenge2.objects.Channel;
import com.example.codechallenge2.objects.ChannelList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MeoJsonApi {

    @GET()
    Call<ChannelList> getChannels(@Url String url, @Query("value") Channel a, @Query("odata.nextLink") String link);

}
