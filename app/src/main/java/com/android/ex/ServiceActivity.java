package com.android.ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.ex.databinding.ActivityServiceBinding;

public class ServiceActivity extends AppCompatActivity {

    ActivityServiceBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = new Intent(this, MyService.class);
        binding.startBtn.setOnClickListener(view -> {
            intent.putExtra("name", "diep");
            startService(intent);
        });

        binding.stopBtn.setOnClickListener(view -> {
            stopService(intent);
        });
    }
}