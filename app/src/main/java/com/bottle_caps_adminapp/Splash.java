package com.bottle_caps_adminapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import common.AppController;

public class Splash extends Activity {
    final int permissionReadExternalStorage=1;
    AppController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        controller=(AppController)getApplicationContext();
        int permissionCheck = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Splash.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        permissionReadExternalStorage);
            } else {
                callThreadForNavigation();;
            }
        } else {
            callThreadForNavigation();;
        }

    }
    public void callThreadForNavigation() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                switchToNextActivity();
                // close this activity
            }
        }, 2000);
    }
    /**
     * Method which is called to switch to next activity.
     **/

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permissionReadExternalStorage: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callThreadForNavigation() ;
                } else {

                    Toast.makeText(Splash.this,"Please provide read external storage permission.",Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }

    public void switchToNextActivity() {
        if (controller.isUserLoggedIn()) {
            if (controller.getSelectedStore() != null) {
                startActivity(new Intent(this, DashBoard.class));
                overridePendingTransition(0, 0);
            } else {
                startActivity(new Intent(this, SelectStoreActivity.class));
                overridePendingTransition(0, 0);

            }
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }

    }
}
