package com.example.myproject.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myproject.Constants;
import com.example.myproject.data.MusicAPI;
import com.example.myproject.presentation.model.Music;
import com.example.myproject.presentation.model.RestMusicResponse;
import com.example.myproject.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myproject.Constants.BASE_URL;

public class MainController {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity view, Gson gson, SharedPreferences sharedPreferences) {
        this.view = view;
        this.gson= gson;
    this.sharedPreferences= sharedPreferences;
    }

    public void onStart(){

        List<Music> musicList = getDataFromCache();
        if(musicList !=null){
            view.showList(musicList);
        } else {
            makeAPICall();
        }
    }
    private void makeAPICall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MusicAPI musicAPI = retrofit.create(MusicAPI.class);

        Call<RestMusicResponse> call = musicAPI.getMusicResponse();
        call.enqueue(new Callback<RestMusicResponse>() {
            @Override
            public void onResponse(Call<RestMusicResponse> call, Response<RestMusicResponse> response) {
                if (response.isSuccessful()&& response.body()!= null){
                    List<Music> musicList = response.body().getAlbum();
                    saveList(musicList);
                    //Toast.makeText(MainActivity.this, "API success", Toast.LENGTH_SHORT).show();
                    view.showList(musicList);
                }
                else{
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestMusicResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Music> musicList) {
        String jsonString= gson.toJson(musicList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MUSIC_LIST, jsonString)
                .apply();
        //Toast.makeText(this, "List saved", Toast.LENGTH_SHORT).show();
    }

    private List<Music> getDataFromCache() {
        String jsonMusic = sharedPreferences.getString(Constants.KEY_MUSIC_LIST, null);

        if(jsonMusic == null){
            return null;
        }
        else {
            Type listType = new TypeToken<List<Music>>() {
            }.getType();
            return gson.fromJson(jsonMusic, listType);
        }
    }
    public void onItemClick(Music music){

    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }
}

