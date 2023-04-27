package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityLoginActiivityBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActiivity extends AppCompatActivity {
    ActivityLoginActiivityBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginActiivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth= FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=binding.edtEmail.getText().toString();
                String password=binding.edtPassword.getText().toString();
                if (email.length()==0 || password.length()==0){
                    Toast.makeText(LoginActiivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActiivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActiivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(LoginActiivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());


                }

            }
        });

        binding.txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActiivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}