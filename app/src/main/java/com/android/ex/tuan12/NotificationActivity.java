package com.android.ex.tuan12;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.ex.R;
import com.android.ex.databinding.ActivityNotificationBinding;

import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyNotification.CHANNEL_ID)
                    .setContentTitle("Tin nhan tu: V")
                    .setContentText("Noi dung: ")
                    .setSmallIcon(R.drawable.icon_notifications)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            managerCompat.notify(getNotificationId(), builder.build());
        });
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}