package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private FirebaseAuth mAuth;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //Welcome text

        docRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        docRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Toast.makeText(this, "welcome " + documentSnapshot.getString("userName"), Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(HomeActivity.this, "Row not found", Toast.LENGTH_SHORT).show();
                    }

                })
                    .addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show());






        binding.cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,LoginActiivity.class));

            }
        });

        binding.cardFindDcotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FindDcotorActivity.class));
            }
        });

        binding.cardOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, OrderDetails.class) );
            }
        });

        binding.cardBuyMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,BuyMedicineActivity.class));
            }
        });


        binding.cardLabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LabTestActivity.class));
            }
        });

        binding.CardHealthArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,HealthArticleActivity.class));
            }
        });
    }
}