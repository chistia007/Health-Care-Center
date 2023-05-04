package com.example.healthcare;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityBuyMedicineBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class BuyMedicineActivity extends AppCompatActivity {
    ActivityBuyMedicineBinding binding;
    public Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBuyMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //animated text

        Animation animation = new TranslateAnimation(-100, 1000, 0, 0);
        animation.setDuration(10010);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        binding.animatedText.startAnimation(animation);


        FirebaseStorage storage= FirebaseStorage.getInstance("gs://health-care-center-e51e6.appspot.com");
        StorageReference storageRef = storage.getReference();


        ActivityResultLauncher<String> launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri u) {
                binding.imageView.setImageURI(u);
                uri=u;
                binding.btnUpload.setVisibility(View.VISIBLE);
                binding.imageView.setVisibility(View.VISIBLE);
            }
        });

        binding.btnChoosePrescription.setOnClickListener(view -> launcher.launch("image/*"));

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog p=new ProgressDialog(BuyMedicineActivity.this);
                p.setTitle("Uploading...");
                p.show();

                if(uri!=null){
                    StorageReference mountainsRef = storageRef.child("Medicine Requests/"+ UUID.randomUUID().toString()); //will give random name to the uploaded files
                    mountainsRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            p.dismiss();
                            startActivity(new Intent(BuyMedicineActivity.this,HomeActivity.class));
                            Toast.makeText(BuyMedicineActivity.this, "Received and we will contact soon", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });



    }
}