package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth= FirebaseAuth.getInstance();

        binding.txtAlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActiivity.class));
            }
        });

        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=binding.edtUsername.getText().toString();
                String email=binding.edtEmail.getText().toString();
                String password=binding.edtPassword.getText().toString();
                String confirmPassword=binding.edtComfirmPassword.getText().toString();


                if (username.length()==0 || email.length()==0 || password.length()==0 || confirmPassword.length()==0){
                    Toast.makeText(RegisterActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.compareTo(confirmPassword) == 0) {

                        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                            //saving user data
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String,String> user=new HashMap<>();
                            user.put("userName",binding.edtUsername.getText().toString());
                            user.put("email",binding.edtEmail.getText().toString());
                            user.put("password",binding.edtPassword.getText().toString());


                            CollectionReference col = db.collection("Users");

                            col.document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).set(user).addOnSuccessListener(aVoid -> {

                                    })
                                    .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                            //startActivity(new Intent(RegisterActivity.this,HomeActivity.class));

                        }).addOnFailureListener(e -> {

                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }

                }
            }
        });
    }
}