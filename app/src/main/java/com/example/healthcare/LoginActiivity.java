package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.databinding.ActivityLoginActiivityBinding;

public class LoginActiivity extends AppCompatActivity {
    ActivityLoginActiivityBinding binding;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginActiivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=new Database(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=binding.edtUsername.getText().toString();
                String password=binding.edtPassword.getText().toString();
                if (username.length()==0 || password.length()==0){
                    Toast.makeText(LoginActiivity.this, "Empty field!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.login(username,password)=="1"){
                        Toast.makeText(LoginActiivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("username",username);
                        // to save our data with key and value
                        editor.apply();
                        startActivity(new Intent(LoginActiivity.this, HomeActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActiivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }

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
}