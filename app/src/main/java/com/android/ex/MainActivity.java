package com.android.ex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ex.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        LoginData loginData = (LoginData) getIntent().getSerializableExtra("loginData");
        binding.welcomeTxt.setText(loginData.getUsername());

        setContentView(binding.getRoot());
    }
}