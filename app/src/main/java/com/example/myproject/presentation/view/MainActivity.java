package com.example.myproject.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myproject.Singletons;
import com.example.myproject.R;
import com.example.myproject.presentation.controller.MainController;
import com.example.myproject.presentation.model.Music;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private MainController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
);
        controller.onStart();
        //showList();
    }


    public void showList(List<Music>musicList){
        recyclerView = (RecyclerView) findViewById(R.id.Recycle_View);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mAdapter = new ListAdapter(musicList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Music item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void showError(){
        Toast.makeText(this, "API error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(Music music) {
        Toast.makeText(this, "navigate", Toast.LENGTH_SHORT).show();

    }
}
