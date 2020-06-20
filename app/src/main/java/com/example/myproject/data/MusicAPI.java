package com.example.myproject.data;

import com.example.myproject.presentation.model.RestMusicResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicAPI {
    @GET("/api/v1/json/1/discography.php?s=coldplay")
    Call<RestMusicResponse> getMusicResponse();
}
