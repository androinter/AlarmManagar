package com.example.alarammanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm fired in broadcast receiver", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context,MyServices.class);
        context.startService(intent1);
    }
}
