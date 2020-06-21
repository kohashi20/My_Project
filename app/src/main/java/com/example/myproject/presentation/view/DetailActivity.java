package com.example.myproject.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;
import com.example.myproject.Singletons;
import com.example.myproject.presentation.controller.MainController;
import com.example.myproject.presentation.model.Music;

import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myproject.Singletons;
import com.example.myproject.R;
import com.example.myproject.presentation.controller.MainController;
import com.example.myproject.presentation.model.Music;

import java.util.List;

    public class DetailActivity extends AppCompatActivity {
        private TextView txtDetail;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            txtDetail = findViewById(R.id.detail_txt);
            Intent intent = getIntent();
            String musicJson = intent.getStringExtra("musicKeyAlbum");
            Music music = Singletons.getGson().fromJson(musicJson, Music.class);
            showDetail(music);
        }

        private void showDetail(Music music) {
            txtDetail.setText(music.getStrAlbum());
        }
    }

