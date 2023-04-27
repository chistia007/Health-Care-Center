package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.healthcare.databinding.ActivityFindDcotorBinding;

public class FindDcotorActivity extends AppCompatActivity {
    ActivityFindDcotorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFindDcotorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindDcotorActivity.this,HomeActivity.class));
            }
        });

        binding.cardOFFamilyPhysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FindDcotorActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("title","Family Physicians");
                startActivity(intent);
            }
        });

        binding.cardOfDietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FindDcotorActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("title","Dietitian");
                startActivity(intent);
            }
        });

        binding.cardOfDentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FindDcotorActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("title","Dentist");
                startActivity(intent);
            }
        });

        binding.cardOFSurgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FindDcotorActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("title","Surgeon");
                startActivity(intent);
            }
        });

        binding.cardOfCardiologists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FindDcotorActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("title","Cardiologists");
                startActivity(intent);
            }
        });
    }
}