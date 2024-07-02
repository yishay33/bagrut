package com.example.bagrutproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long DELAY_IN_MILLISECONDS = 3000 ;
    Button btn;
    Intent intent;
    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);





        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



        Intent alarmIntent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);

// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY*7, pendingIntent);

//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + DELAY_IN_MILLISECONDS, pendingIntent);

    }

    @Override
    public void onClick(View view) {
        if (view == btn){
            intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }
}