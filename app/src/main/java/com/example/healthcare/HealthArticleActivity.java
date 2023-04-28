package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.example.healthcare.databinding.ActivityHealthArticleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.proto.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HealthArticleActivity extends AppCompatActivity {
    List<String> imageUrls;
    ActivityHealthArticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://health-care-center-e51e6.appspot.com/Health Articles");

        storageRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        int numImages = listResult.getItems().size();
                        imageUrls = new ArrayList<>();

                        for (StorageReference item : listResult.getItems()) {
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    imageUrls.add(imageUrl);

                                    if (imageUrls.size() == numImages) {
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthArticleActivity.this, R.layout.health_article_list, R.id.health_article_image_view, imageUrls) {
                                            @Override
                                            public View getView(int position, View convertView, ViewGroup parent) {
                                                if (convertView == null) {
                                                    convertView = getLayoutInflater().inflate(R.layout.health_article_list, parent, false);
                                                }

                                                ImageView imageView = convertView.findViewById(R.id.health_article_image_view);
                                                String imageUrl = getItem(position);

                                                // Load the image from the URL into the ImageView using Glide
                                                Glide.with(HealthArticleActivity.this)
                                                        .load(imageUrl)
                                                        .into(imageView);

                                                return convertView;
                                            }
                                        };

                                        binding.healthArticleList.setAdapter(adapter);
                                    }
                                }
                            });
                        }
                    }
                });




        //view on full screen

        binding.healthArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imageUrl = imageUrls.get(position);

                Intent intent = new Intent(HealthArticleActivity.this, FullScreenImageActivity.class);
                intent.putExtra("image_url", imageUrl);
                startActivity(intent);
            }
        });

    }
}

