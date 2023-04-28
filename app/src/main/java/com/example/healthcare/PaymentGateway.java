package com.example.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityPaymentGatewayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PaymentGateway extends AppCompatActivity {
    ActivityPaymentGatewayBinding binding;
    private FirebaseAuth mAuth;
    private String  doctorName;
    private String  appointmentTime;

    private FirebaseFirestore db;
    int documentCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentGatewayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent=getIntent();
        doctorName= intent.getStringExtra("doctorName");
        appointmentTime= intent.getStringExtra("appointmentTime");

        binding.paymentBkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(PaymentGateway.this);
                builder.setTitle("Cancel");
                builder.setMessage("Waiting on Bkash to send their api");
                builder.setNegativeButton("Cancel", (dialog, which) -> {

                });

                builder.setPositiveButton("Confirm Purchase", (dialog, which) -> {

                    CollectionReference usersCollectionRef = db.collection("Order Details");
                    usersCollectionRef.get().addOnSuccessListener(querySnapshot -> {
                        documentCount = querySnapshot.size();

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String,String> orders=new HashMap<>();
                        orders.put("doctorName",doctorName);
                        orders.put("appointmentTime",appointmentTime);
                        orders.put("status","true");

                        CollectionReference col = db.collection("Order Details");

                        col.document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()+"order"+(documentCount+1)).set(orders).addOnSuccessListener(aVoid -> {
                                    Toast.makeText(PaymentGateway.this, "Payment successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PaymentGateway.this, OrderDetails.class));
                                })
                                .addOnFailureListener(e -> Toast.makeText(PaymentGateway.this, e.getMessage(), Toast.LENGTH_SHORT).show());

                    });



            })
                ;builder.create().show();

            };

        });
    }
}