package com.example.alarammanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText eT_time;
    RadioGroup radioGroup;
    Button setalarm, cancelalarm;
    RadioButton radioButton;

    private Intent alarmintent;
    private PendingIntent pendingIntent;
    private static final int Alarm_RequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eT_time = findViewById(R.id.eT_time);
        radioGroup = findViewById(R.id.radiogroup);
        setalarm = findViewById(R.id.but1);
        cancelalarm = findViewById(R.id.but2);

        alarmintent = new Intent(MainActivity.this,MyReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this,Alarm_RequestCode,alarmintent,0);

        setalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String time = eT_time.getText().toString();

                if (!time.isEmpty()) {

                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(MainActivity.this, "Please select any one type", Toast.LENGTH_SHORT).show();
                    } else {

                        int Time = radioGroup.getCheckedRadioButtonId();
                        radioButton = findViewById(Time);

                        int converttime = Integer.parseInt(time);

                        int i = getCorrectInterval(converttime);

                        if (i != 0){
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.SECOND,i);

                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                            Toast.makeText(MainActivity.this, "Alarm set for"+i+" "+radioButton.getText(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "The Entered value should not be zero", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Time interval should not empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                Intent intent = new Intent(MainActivity.this,MyServices.class);
                stopService(intent);
                Toast.makeText(MainActivity.this, "Alarm Stopped", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @SuppressLint("NonConstantResourceId")
    private int getCorrectInterval(int converttime) {

        switch (radioGroup.getId()) {

            case R.id.second: {
                return converttime;
            }
            case R.id.minutes: {
                return converttime * 60;
            }
            case R.id.hours: {
                return converttime * 60 * 60;
            }
            default:{
                return converttime;
            }
        }
    }
}