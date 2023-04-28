package com.example.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityLabTestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LabTestActivity extends AppCompatActivity {
    ActivityLabTestBinding binding;
    private String labName;
    private String hospitalAddress ;
    private String labCost;
    private String labTestTime ;
    private FirebaseAuth mAuth;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    private  int documentCount;
    private List<List<String>>lab_details;
    ArrayList  list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLabTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        lab_details = new ArrayList<List<String>>();

        //number of document under collection of firestore
        CollectionReference usersCollectionRef = db.collection("Lab Tests");
        usersCollectionRef.get().addOnSuccessListener(querySnapshot -> {
            documentCount = querySnapshot.size();

            for(int i=0;i<documentCount;i++){
                final int index = i; // create a final variable to hold the current value of i
                docRef = db.collection("Lab Tests").document("labTest"+(i+1));
                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        lab_details.add(Arrays.asList(documentSnapshot.getString("labTestName"),documentSnapshot.getString("hospitalAddress"),documentSnapshot.getString("cost"),documentSnapshot.getString("time")));

                        list = new ArrayList();
                        for (int j = 0; j < lab_details.size(); j++) {
                            HashMap<String, String> items = new HashMap<String, String>();
                            items.put("line1", "Lab Test Name : "+lab_details.get(j).get(0));
                            items.put("line2", "Hospital Address : "+lab_details.get(j).get(1));
                            items.put("line3", "Cost : "+lab_details.get(j).get(2) +" TK");
                            items.put("line4", "Time : "+lab_details.get(j).get(3));
                            list.add(items);
                        }

                        sa = new SimpleAdapter(this, list, R.layout.lab_test_list,
                                new String[]{"line1", "line2", "line3", "line4", "line5","line6"},
                                new int[]{R.id.labTestName, R.id.hospitalAddress, R.id.cost, R.id.time});

                        binding.labTestList.setAdapter(sa);

                    }
                });
            }
        });


        binding.labTestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) parent.getItemAtPosition(position);
                labName = selectedItem.get("line1");
                hospitalAddress = selectedItem.get("line2");
                String cost = selectedItem.get("line3");
                labTestTime = selectedItem.get("line4");


                AlertDialog.Builder builder=new AlertDialog.Builder(LabTestActivity.this);
                builder.setTitle("Make Appointment");
                builder.setMessage(labName +  "\n"+
                        hospitalAddress +"\n"+
                        cost+" TK"+ "\n"+
                        labTestTime);
                builder.setNegativeButton("Cancel", (dialog, which) -> {

                });

                builder.setPositiveButton("Proceed to Payment", (dialog, which) -> {
                    Intent intent1=new Intent(LabTestActivity.this, PaymentGateway.class);
                    intent1.putExtra("doctorName",labName);
                    intent1.putExtra("appointmentTime",labTestTime);
                    startActivity(intent1);

                });
                builder.create().show();

            }
        });
    }
}