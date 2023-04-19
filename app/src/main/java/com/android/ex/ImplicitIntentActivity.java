package com.android.ex;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.ex.databinding.ActivityImplicitIntentBinding;

public class ImplicitIntentActivity extends AppCompatActivity {

    ActivityImplicitIntentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImplicitIntentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.youtubeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/"));
            startActivity(intent);
        });

        binding.smsBtn.setOnClickListener(view -> {
            Intent intentSMS = new Intent(Intent.ACTION_VIEW);
            intentSMS.setData(Uri.parse("sms:" + "0912004866"));
            intentSMS.putExtra("sms_body", "SMS");
            startActivity(intentSMS);
        });

        binding.callBtn.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:0912004866"));
            startActivity(callIntent);

        });
    }
}