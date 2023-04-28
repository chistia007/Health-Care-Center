package com.example.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityDcotorDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

//1:56
public class DoctorDetailsActivity extends AppCompatActivity {
    ActivityDcotorDetailsBinding binding;
    HashMap<String, String> items;
    private String doctorName;
    private String hospitalAddress ;
    private String doctorFees;
    private String appointmentTime ;
    private FirebaseAuth mAuth;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    private  int documentCount;


    private List<List<String>> doctor_details;
    ArrayList  list;

    SimpleAdapter sa;
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDcotorDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        String title= intent.getStringExtra("title");
        binding.doctorName.setText(title);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        doctor_details = new ArrayList<List<String>>();

        //number of document under collection of firestore
        CollectionReference usersCollectionRef = db.collection(title);
        usersCollectionRef.get().addOnSuccessListener(querySnapshot -> {
            documentCount = querySnapshot.size();

            for(int i=0;i<documentCount;i++){
                final int index = i; // create a final variable to hold the current value of i
                docRef = db.collection(title).document("doctor"+(i+1));
                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        doctor_details.add(Arrays.asList(documentSnapshot.getString("doctor_name"),documentSnapshot.getString("hospital_address"),documentSnapshot.getString("Experience"),documentSnapshot.getString("mobile_number"),documentSnapshot.getString("fees"),documentSnapshot.getString("time")));

                        list = new ArrayList();
                        for (int j = 0; j < doctor_details.size(); j++) {
                            HashMap<String, String> items = new HashMap<String, String>();
                            items.put("line1", "Doctor name : "+doctor_details.get(j).get(0));
                            items.put("line2", "Hospital Address : "+doctor_details.get(j).get(1));
                            items.put("line3", "Experience : "+doctor_details.get(j).get(2));
                            items.put("line4", "Phone : "+doctor_details.get(j).get(3));
                            items.put("line5", "Consultant fees : " + doctor_details.get(j).get(4) + " TK");
                            items.put("line6", "Appointment date : " + doctor_details.get(j).get(5));
                            list.add(items);
                        }

                        sa = new SimpleAdapter(this, list, R.layout.doctor_list,
                                new String[]{"line1", "line2", "line3", "line4", "line5","line6"},
                                new int[]{R.id.dctName, R.id.hptlAddress, R.id.exp, R.id.phoneNumber, R.id.doctFees,R.id.time});

                        binding.txtDoctorList.setAdapter(sa);
                    }
                });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(DoctorDetailsActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
        });


        binding.txtDoctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) parent.getItemAtPosition(position);
                doctorName = selectedItem.get("line1");
                hospitalAddress = selectedItem.get("line2");
                String exp = selectedItem.get("line3");
                String phoneNumber = selectedItem.get("line4");
                doctorFees = selectedItem.get("line5");
                appointmentTime = selectedItem.get("line6");

                AlertDialog.Builder builder=new AlertDialog.Builder(DoctorDetailsActivity.this);
                builder.setTitle("Make Appointment");
                builder.setMessage(doctorName +  "\n"+
                        hospitalAddress +"\n"+
                        doctorFees+"\n"+
                        appointmentTime);
                builder.setNegativeButton("Cancel", (dialog, which) -> {

                });

                builder.setPositiveButton("Proceed to Payment", (dialog, which) -> {
                    Intent intent1=new Intent(DoctorDetailsActivity.this, PaymentGateway.class);
                    intent1.putExtra("doctorName",doctorName);
                    intent1.putExtra("appointmentTime",appointmentTime);
                    startActivity(intent1);

                });
                builder.create().show();

            }
        });






    }
}