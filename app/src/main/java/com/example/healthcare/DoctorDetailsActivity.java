package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityDcotorDetailsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//1:56
public class DoctorDetailsActivity extends AppCompatActivity {
    ActivityDcotorDetailsBinding binding;
    HashMap<String, String> items;

    private String [][] docotrDetails1={
            {"Doctor name : Chisthia Khan","Hospital address : Dhaka 1207","Exp : 7 years", "Mobile Number : 017382682", "1200", "05-05-2023 15:30"},
            {"Doctor name : Samsung Khan","Hospital address : Chittagong 1207","Exp : 8 years", "Mobile Number : 015782682", "1000","06-05-2023 15:30"},
            {"Doctor name : Rajesh Khan","Hospital address : Dhaka 1207","Exp : 3 years", "Mobile Number : 018382682", "700","07-05-2023 15:30"},
            {"Doctor name : Alex Khan","Hospital address : Dhaka 1207","Exp : 4 years", "Mobile Number : 013382682", "1200","06-05-2023 15:30"},
            {"Doctor name : Nishita Khan","Hospital address : Chittagong 1207","Exp : 13 years", "Mobile Number : 016382682", "2000","06-05-2023 15:30"},

    };
    private String [][] docotrDetails2={
            {"Doctor name : Chisthia Khan","Hospital address : Dhaka 1207","Exp : 7 years", "Mobile Number : 017382682", "1200","05-05-2023 15:30"},
            {"Doctor name : Samsung Khan","Hospital address : Chittagong 1207","Exp : 8 years", "Mobile Number : 015782682", "1000","05-07-2023 15:30"},
            {"Doctor name : Rajesh Khan","Hospital address : Dhaka 1207","Exp : 3 years", "Mobile Number : 018382682", "700","05-05-2023 15:30"},
            {"Doctor name : Alex Khan","Hospital address : Dhaka 1207","Exp : 4 years", "Mobile Number : 013382682", "1200","05-05-2023 15:30"},
            {"Doctor name : Nishita Khan","Hospital address : Chittagong 1207","Exp : 13 years", "Mobile Number : 016382682", "2000","05-05-2023 15:30"},

    };
    private String [][] docotrDetails3={
            {"Doctor name : Chisthia Khan","Hospital address : Dhaka 1207","Exp : 7 years", "Mobile Number : 017382682", "1200","16-05-2023 15:30"},
            {"Doctor name : Samsung Khan","Hospital address : Chittagong 1207","Exp : 8 years", "Mobile Number : 015782682", "1000","06-05-2023 15:30"},
            {"Doctor name : Rajesh Khan","Hospital address : Dhaka 1207","Exp : 3 years", "Mobile Number : 018382682", "700","08-05-2023 15:30"},
            {"Doctor name : Alex Khan","Hospital address : Dhaka 1207","Exp : 4 years", "Mobile Number : 013382682", "1200","07-05-2023 15:30"},
            {"Doctor name : Nishita Khan","Hospital address : Chittagong 1207","Exp : 13 years", "Mobile Number : 016382682", "2000","09-05-2023 15:30"},

    };
    private String [][] docotrDetails4={
            {"Doctor name : Chisthia Khan","Hospital address : Dhaka 1207","Exp : 7 years", "Mobile Number : 017382682", "1200","06-05-2023 15:30"},
            {"Doctor name : Samsung Khan","Hospital address : Chittagong 1207","Exp : 8 years", "Mobile Number : 015782682", "1000","07-05-2023 15:30"},
            {"Doctor name : Rajesh Khan","Hospital address : Dhaka 1207","Exp : 3 years", "Mobile Number : 018382682", "700","05-06-2023 15:30"},
            {"Doctor name : Alex Khan","Hospital address : Dhaka 1207","Exp : 4 years", "Mobile Number : 013382682", "1200","05-05-2023 15:30"},
            {"Doctor name : Nishita Khan","Hospital address : Chittagong 1207","Exp : 13 years", "Mobile Number : 016382682", "2000","05-05-2023 15:30"},

    };
    private String [][] docotrDetails5={
            {"Doctor name : Chisthia Khan","Hospital address : Dhaka 1207","Exp : 7 years", "Mobile Number : 017382682", "1200","08-05-2023 15:30"},
            {"Doctor name : Samsung Khan","Hospital address : Chittagong 1207","Exp : 8 years", "Mobile Number : 015782682", "1000","06-05-2023 15:30"},
            {"Doctor name : Rajesh Khan","Hospital address : Dhaka 1207","Exp : 3 years", "Mobile Number : 018382682", "700","08-05-2023 15:30"},
            {"Doctor name : Alex Khan","Hospital address : Dhaka 1207","Exp : 4 years", "Mobile Number : 013382682", "1200","4-05-2023 15:30"},
            {"Doctor name : Nishita Khan","Hospital address : Chittagong 1207","Exp : 13 years", "Mobile Number : 016382682", "2000","24-05-2023 15:30"},

    };
    String[][] doctor_details={};
    ArrayList list;
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

        if (title.compareTo("Family Physicians")==0){
            doctor_details=docotrDetails1;
        }
        else
        if (title.compareTo("Dietitian")==0){
            doctor_details=docotrDetails2;
        }
        else
        if (title.compareTo("Dentist")==0){
            doctor_details=docotrDetails3;
        }
        else
        if (title.compareTo("Surgeon")==0){
            doctor_details=docotrDetails4;
        }
        else doctor_details=docotrDetails5;



//            binding.btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DoctorDetailsActivity.this,FindDcotorActivity.class));
//            }
//        });

        list = new ArrayList();
        for (int i = 0; i < doctor_details.length; i++) {
            HashMap<String, String> items = new HashMap<String, String>();
            items.put("line1", doctor_details[i][0]);
            items.put("line2", doctor_details[i][1]);
            items.put("line3", doctor_details[i][2]);
            items.put("line4", doctor_details[i][3]);
            items.put("line5", "Consultant fees : " + doctor_details[i][4] + " TK");
            items.put("line6", "Appointment date : " + doctor_details[i][5]);
            list.add(items);
        }

        sa = new SimpleAdapter(this, list, R.layout.doctor_list,
                new String[]{"line1", "line2", "line3", "line4", "line5","line6"},
                new int[]{R.id.dctName, R.id.hptlAddress, R.id.exp, R.id.phoneNumber, R.id.doctFees,R.id.time});

        binding.txtDoctorList.setAdapter(sa);


        binding.txtDoctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) parent.getItemAtPosition(position);
                String dctName = selectedItem.get("line1");
                String hptlAddress = selectedItem.get("line2");
                String exp = selectedItem.get("line3");
                String phoneNumber = selectedItem.get("line4");
                String doctFees = selectedItem.get("line5");
                String time = selectedItem.get("line6");
                Toast.makeText(DoctorDetailsActivity.this, hptlAddress+" "+time, Toast.LENGTH_SHORT).show();
            }
        });



    }
}