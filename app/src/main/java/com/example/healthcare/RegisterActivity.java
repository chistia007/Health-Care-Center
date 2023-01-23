package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

                db=new Database(RegisterActivity.this);

                if (username.length()==0 || email.length()==0 || password.length()==0 || confirmPassword.length()==0){
                    Toast.makeText(RegisterActivity.this, "Empty field!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.compareTo(confirmPassword) == 0) {
                        db.register(username,email,password);
                        Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}