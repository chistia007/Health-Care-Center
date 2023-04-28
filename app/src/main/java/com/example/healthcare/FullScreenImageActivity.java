package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.healthcare.databinding.ActivityFullScreenImageBinding;

public class FullScreenImageActivity extends AppCompatActivity {
    ActivityFullScreenImageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFullScreenImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String imageUrl = getIntent().getStringExtra("image_url");
        ImageView imageView = findViewById(R.id.full_screen_image_view);

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

    }
}