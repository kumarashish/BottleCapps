package utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bottle_caps_adminapp.LoginActivity;
import com.bottle_caps_adminapp.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import model.OrderModel;
import model.StatusModel;
import model.StoreModel;


/**
 * Created by Ashish.Kumar on 18-01-2018.
 */

public class Util {

    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(act, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean isNetworkAvailable(Context act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(act, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static String getDefaultStartDate() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;

        String sDate = month + "/"
                + "01"
                + "/" + c.get(Calendar.YEAR);
        return sDate;
    }

    public static String getMatchDate(String date) {
        String[] datee = date.split("/");
        return datee[1];
    }

    public static String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return Integer.toString(year);
    }

    public static String getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String s = formatter.format(date);
        return s;
    }

    public static String getCurrentDateWithoutHHMMSS() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String s = formatter.format(date);
        return s;
    }

    public static String getYesterdayDate() {

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);

    }

    public static String getTommorowDate() {

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);

    }

    public static String getCurrentDateWithoutTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String s = formatter.format(date);
        return s;
    }

    public static Spannable getMulticolorTextView(String string, Integer[] color, Integer[] value) {
        Spannable wordtoSpan = new SpannableString(string);
        wordtoSpan.setSpan(new ForegroundColorSpan(color[0]), value[0], value[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(color[1]), value[2], value[3], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return wordtoSpan;
    }

    public static String getDateinMMDDYY(String Date) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyy");
        try {
            Date date = formatter.parse(Date);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String s = format.format(date);
            return s;
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static String[] getDateFromString(String datee) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyy hh:mm:ss");
        try {
            Date date = formatter.parse(datee);
            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat time = new SimpleDateFormat("h:mm aa");
            return new String[]{formatt.format(date), time.format(date)};
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static void makeFolder(String path, String folder) {
        File directory = new File(path, folder);
        if (directory.exists() == false) {
            directory.mkdirs();
        }
    }

    public static Dialog showPogress(Activity act) {
        final Dialog dialog = new Dialog(act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }

    public static void showToast(final Activity act, final String message) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void cancelDialog(final Activity act, final Dialog dialog) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        });
    }

    public static void showDialog(final Activity act, final Dialog dialog) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    public static void startActivityCommon(final Activity act, final Intent in) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.startActivity(in);
            }
        });

    }

    public static void startActivityForResultCommon(final Activity act, final Intent in) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.startActivityForResult(in, 1);
            }
        });

    }

    public static String getDeviceID(Activity act) {
        String deviceId = "";
        deviceId = Settings.Secure.getString(act.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }
    public static String getDeviceID(Context act) {
        String deviceId = "";
        deviceId = Settings.Secure.getString(act.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static boolean getStatus(String data) {
        boolean status = false;
        try {
            JSONObject jsonObject1 = new JSONObject(data);
            JSONObject jsonObject = null;
            if (!jsonObject1.isNull("OrderList")) {
                jsonObject = jsonObject1.getJSONObject("OrderList");
            } else if (!jsonObject1.isNull("LoginStoreUser")) {
                jsonObject = jsonObject1.getJSONObject("LoginStoreUser");
            } else if (!jsonObject1.isNull("OrderDetail")) {
                jsonObject = jsonObject1.getJSONObject("OrderDetail");
            } else if (!jsonObject1.isNull("Message")) {
                jsonObject = jsonObject1;
            }
            String message = jsonObject.getString("Message");
            if (message.equalsIgnoreCase("SUCCESS") || message.equalsIgnoreCase("Sucess") || message.equalsIgnoreCase("Status updated successfully.") || message.contains("Status updated successfully")|| message.contains("Order has been canceled")) {
                status = true;
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return status;
    }

    public static JSONObject getJsonObject(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (!jsonObject.isNull("OrderList")) {
                return jsonObject.getJSONObject("OrderList");
            } else if (!jsonObject.isNull("LoginStoreUser")) {
                return jsonObject.getJSONObject("LoginStoreUser");
            } else if (!jsonObject.isNull("OrderDetail")) {
                return jsonObject.getJSONObject("OrderDetail");
            }
            return null;

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static Integer getTargetStatusId(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            return jsonObject.getInt("TargetStatusId");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return -1;
    }

    public static String getMessage(String data) {
        String message = "";
        try {
            JSONObject jsonObject1 = new JSONObject(data);
            JSONObject jsonObject = null;
            if (!jsonObject1.isNull("OrderList")) {
                jsonObject = jsonObject1.getJSONObject("OrderList");
            } else if (!jsonObject1.isNull("LoginStoreUser")) {
                jsonObject = jsonObject1.getJSONObject("LoginStoreUser");
            } else if (!jsonObject1.isNull("OrderDetail")) {
                jsonObject = jsonObject1.getJSONObject("OrderDetail");
            } else if (!jsonObject1.isNull("Message")) {
                jsonObject = jsonObject1;
            }
            message = jsonObject.getString("Message");

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return message;
    }


    public static String getAppVersion(Activity activity) {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static JSONObject getResultJson(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            return jsonObject.getJSONObject("Result");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static String getCamelCase(String value) {
        return String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1, value.length());
    }

    public static JSONArray getResultJsonArray(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            return jsonObject.getJSONArray("Result");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static String getRequestString(String[] keys, String[] values) {
        String value = "";
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                value = keys[0] + "=" + values[0];
            } else {
                value += "&" + keys[i] + "=" + values[i];
            }
        }
        return value;
    }

    public static void Logout(Activity act) {
        Intent intent = new Intent(act, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
    }

    public static void setStatusIcon(ImageView icon, int statusId) {
        switch (statusId) {
            case 1:
                icon.setImageResource(R.drawable.status_pending_icon);
                break;
            case 2:
                icon.setImageResource(R.drawable.status_ready);
                break;
            case 3:
                icon.setImageResource(R.drawable.status_complete_icon);
                break;
            case 4:
                icon.setImageResource(R.drawable.status_cancel);
                break;
            case 5:
                icon.setImageResource(R.drawable.status_deliver_icon);
                break;
            case 6:
                icon.setImageResource(R.drawable.status_complete_icon);
                break;

        }
    }
    public static int getTargetId(int typeId, int currentId) {
        int targetId = -1;
        if (typeId == 1) {
            switch (currentId) {
                case 1:
                    targetId = 2;
                    break;
                case 2:
                    targetId = 3;
                    break;
            }

        } else {
            switch (currentId) {
                case 1:
                    targetId = 5;
                    break;
                case 5:
                    targetId = 6;
                    break;
            }

        }
        return targetId;
    }
    public static void  setIcon(Activity act,int typeId,int statusId, ImageView icon, common.Bold_TextView text) {
        {
            if (typeId == 1) {
                switch (statusId) {
                    case 1:
                        icon.setImageResource(R.drawable.status_pending_icon);
                        text.setTextColor(act.getResources().getColor(R.color.red));
                        break;
                    case 2:
                        icon.setImageResource(R.drawable.status_ready);
                        text.setTextColor(act.getResources().getColor(R.color.yellow));
                        break;
                    case 3:
                        icon.setImageResource(R.drawable.status_complete_icon);
                        text.setTextColor(act.getResources().getColor(R.color.green));
                        break;
                    case 4:
                        icon.setImageResource(R.drawable.status_cancel);
                        text.setTextColor(act.getResources().getColor(R.color.red));
                        break;
                }
            } else {
                switch (statusId) {
                    case 1:
                        icon.setImageResource(R.drawable.status_pending_icon);
                        text.setTextColor(act.getResources().getColor(R.color.red));
                        break;
                    case 4:
                        icon.setImageResource(R.drawable.status_cancel);
                        text.setTextColor(act.getResources().getColor(R.color.red));
                        break;
                    case 5:
                        icon.setImageResource(R.drawable.status_deliver_icon);
                        text.setTextColor(act.getResources().getColor(R.color.skyblue));
                        break;
                    case 6:
                        icon.setImageResource(R.drawable.status_complete_icon);
                        text.setTextColor(act.getResources().getColor(R.color.green));
                        break;
                }

            }
        }
    }
    public static String getOrderStatusString(StoreModel selectedStore, int typeId, int StatusId) {
        String value = "";
        if (typeId == 1) {
            for (int i = 0; i < selectedStore.getFilters().getPickup().size(); i++) {
                if (StatusId == selectedStore.getFilters().getPickup().get(i).getOrderStatusId()) {
                    value = selectedStore.getFilters().getPickup().get(i).getOrderStatusName();
                    break;
                }
            }
        } else {
            for (int i = 0; i < selectedStore.getFilters().getDelivery().size(); i++) {
                if (StatusId == selectedStore.getFilters().getDelivery().get(i).getOrderStatusId()) {
                    value = selectedStore.getFilters().getDelivery().get(i).getOrderStatusName();
                    break;
                }
            }

        }
        return value;
    }

    public static  boolean isTablet(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.2) {
            return true;
        } else {
            return false;
        }
    }

    public static int getOrderCount(String response) {
        try {
            JSONObject jsonObject = Util.getJsonObject(response);
            return jsonObject.isNull("TotalOrders")?0:jsonObject.getInt("TotalOrders");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return 0;
    }
}
