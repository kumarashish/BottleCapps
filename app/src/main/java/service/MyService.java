package service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bottle_caps_adminapp.DashBoard;

import java.util.Timer;
import java.util.TimerTask;

import common.AppController;
import common.Common;
import utils.Util;

/**
 * Created by ashish.kumar on 12-11-2018.
 */

public class MyService extends Service {
   Intent in;
    public static final int notify = 20000;  //interval between two services(Here Service run every 3 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;
    //timer handling
    AppController controller;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        controller=(AppController)getApplicationContext();
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        Log.d("service is ", "Destroyed");
    }


    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String statusId="0";
                    if (DashBoard.model.getStatus().size() != 0) {
                        for (int i = 0; i < DashBoard.model.getStatus().size(); i++) {
                            if (i == 0) {
                                statusId = Integer.toString(DashBoard.model.getStatus().get(0));
                            } else {
                                statusId += "," + DashBoard.model.getStatus().get(i);
                            }
                        }
                        // statusId = Integer.toString(model.getStatus().get(0));
                    }
                  String response=  controller.getApicall().getData(Common.orderListUrl, Util.getRequestString(Common.orderlistKeys, new String[]{controller.getSelectedStore().getStoreId(), Integer.toString(controller.getLoginmodel().getUserId()), Util.getDeviceID(getApplicationContext()), "A", controller.getLoginmodel().getSessionId(), Integer.toString(1), "50", Integer.toString(DashBoard.model.getType()), statusId,"",DashBoard.model.getStartDate(),DashBoard.model.getEndDate()}));
                    Intent local = new Intent();
                    local.setAction(DashBoard.FILTER_ACTION_KEY);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(local.putExtra("broadcastMessage",  response));
                }
            });
        }
    }


}
