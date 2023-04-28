package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityOrderDetailsBinding;
import com.example.healthcare.databinding.ActivityPaymentGatewayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OrderDetails extends AppCompatActivity {
    ActivityOrderDetailsBinding binding;
    private FirebaseAuth mAuth;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    private  int documentCount;
    private List<List<String>> doctor_details;
    ArrayList  list;

    SimpleAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        doctor_details = new ArrayList<List<String>>();


        //number of document under collection of firestore
        CollectionReference usersCollectionRef = db.collection("Order Details");
        usersCollectionRef.get().addOnSuccessListener(querySnapshot -> {
            documentCount = querySnapshot.size();

            for(int i=0;i<documentCount;i++){
                final int index = i; // create a final variable to hold the current value of i
                docRef = db.collection("Order Details").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()+"order"+i);
                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        doctor_details.add(Arrays.asList(documentSnapshot.getString("doctorName"),documentSnapshot.getString("appointmentTime"),documentSnapshot.getString("status")));

                        list = new ArrayList();
                        for (int j = 0; j < doctor_details.size(); j++) {
                            HashMap<String, String> items = new HashMap<String, String>();
                            items.put("line1", doctor_details.get(j).get(0));
                            items.put("line2", doctor_details.get(j).get(1));
                            //items.put("line3", doctor_details.get(j).get(2));

                            list.add(items);
                        }

                        sa = new SimpleAdapter(this, list, R.layout.order_list,
                                new String[]{"line1", "line2" },
                                new int[]{R.id.doctorName, R.id.additionalData});

                        binding.orderDetails.setAdapter(sa);
                    }
                });
            }
        });

    }
}