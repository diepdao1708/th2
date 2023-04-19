package com.android.ex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.ex.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public final static int REQUEST_CODE = 10000;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerBtn.setOnClickListener(view -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivityForResult(registerIntent, REQUEST_CODE);
        });

        binding.loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            LoginData loginData = new LoginData(
                    binding.usernameEdit.getText().toString(),
                    binding.passwordEdit.getText().toString()
            );
            intent.putExtra("loginData", loginData);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                LoginData loginData = (LoginData) data.getSerializableExtra("loginData");
                binding.usernameEdit.setText(loginData.getUsername());
                binding.passwordEdit.setText(loginData.getPassword());
            } else {
                binding.usernameEdit.setText("");
                binding.passwordEdit.setText("");
                Toast.makeText(getApplicationContext(), "Ban da huy dang ki", Toast.LENGTH_LONG).show();
            }
        }
    }
}
