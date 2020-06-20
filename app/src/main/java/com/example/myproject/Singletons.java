package com.example.myproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myproject.data.MusicAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myproject.Constants.BASE_URL;

public class Singletons {
    private static Gson gsonInstance;
    private static MusicAPI musicAPIInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance== null){
            gsonInstance= new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static MusicAPI getMusicAPI(){
        if (musicAPIInstance == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            musicAPIInstance = retrofit.create(MusicAPI.class);

        }
        return musicAPIInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if (sharedPreferencesInstance == null){
            sharedPreferencesInstance= context.getSharedPreferences("Music_Application", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
