package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import utils.Util;

/**
 * Created by ashish.kumar on 12-11-2018.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("broadcastMessage");
     // Toast.makeText(context,"Receiver called",Toast.LENGTH_SHORT).show();
    }
}
