package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://www.theaudiodb.com/";
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //showList();
        makeAPICall();
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

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
    private void showError(){
        Toast.makeText(this, "API error", Toast.LENGTH_SHORT).show();
    }
}
