package com.example.firebaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView imageView = findViewById(R.id.fullScreenImageView);
        String imageURL = getIntent().getStringExtra("imageURL");

        // Load ảnh từ URL và hiển thị vào ImageView
        Glide.with(this)
                .load(imageURL)
                .into(imageView);
    }
}