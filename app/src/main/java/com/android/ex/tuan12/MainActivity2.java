package com.android.ex.tuan12;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ex.databinding.ActivityMain2Binding;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, binding.time.getCurrentHour());
        calendar.set(Calendar.MINUTE, binding.time.getCurrentMinute());

        binding.datGioBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyAlarm.class);
            intent.setAction("ABC");
            intent.putExtra("name", "V");
            intent.putExtra("content", "ND");
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        });

        binding.dungBtn.setOnClickListener(view -> {

        });
    }
}