package com.example.myproject.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myproject.Constants;
import com.example.myproject.R;
import com.example.myproject.data.MusicAPI;
import com.example.myproject.presentation.model.Music;
import com.example.myproject.presentation.model.RestMusicResponse;
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

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://www.theaudiodb.com/";
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //showList();
        sharedPreferences = getSharedPreferences("Music_Application", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Music> musicList = getDataFromCache();
        if(musicList !=null){
            showList(musicList);
        } else {
            makeAPICall();
        }
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

    private void showList(List<Music>musicList){
        recyclerView = (RecyclerView) findViewById(R.id.Recycle_View);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(musicList);
        recyclerView.setAdapter(mAdapter);
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
                    showList(musicList);
                }
                else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestMusicResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<Music> musicList) {
        String jsonString= gson.toJson(musicList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_MUSIC_LIST, jsonString)
                .apply();
        Toast.makeText(this, "List saved", Toast.LENGTH_SHORT).show();
    }

    private void showError(){
        Toast.makeText(this, "API error", Toast.LENGTH_SHORT).show();
    }
}
