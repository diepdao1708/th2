package com.android.ex;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ex.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            LoginData loginData = new LoginData(
                    binding.usernameEdit.getText().toString(),
                    binding.passwordEdit.getText().toString()
            );
            intent.putExtra("loginData", loginData);
            setResult(RESULT_OK, intent);
            finish();
        });

        binding.cancelBtn.setOnClickListener(view -> {
            setResult(RESULT_OK, null);
            finish();
        });
    }
}