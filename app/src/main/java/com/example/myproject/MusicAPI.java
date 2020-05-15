package com.example.myproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicAPI {
    @GET("/api/v1/json/1/discography.php?s=coldplay")
    Call<RestMusicResponse> getMusicResponse();
}
