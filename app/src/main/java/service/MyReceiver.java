package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bottle_caps_adminapp.DashBoard;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Common;
import model.OrderModel;
import utils.Util;

/**
 * Created by ashish.kumar on 12-11-2018.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String response = intent.getStringExtra("broadcastMessage");
        if (Util.getStatus(response)==true) {
            int orderCount = Util.getOrderCount(response);
            if (orderCount > DashBoard.totalorderCount) {
                try {
                    JSONObject jsonObject = Util.getJsonObject(response);
                    DashBoard.totalorderCount = jsonObject.getInt("TotalOrders");
                    JSONArray orders = jsonObject.getJSONArray("Orders");
                    DashBoard.ordersList.clear();

                    for (int i = 0; i < orders.length(); i++) {
                        OrderModel model = new OrderModel(orders.getJSONObject(i));
                        DashBoard.ordersList.add(model);
                    }
                    if (DashBoard.adapter != null) {
                        DashBoard.adapter.notifyDataSetChanged();
                    }

                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
                int difference = orderCount - DashBoard.totalorderCount;
                Toast.makeText(context, "You have " + difference + " new orders", Toast.LENGTH_SHORT).show();
                DashBoard.orderList.smoothScrollToPosition(0);
            }
        }else if (Util.getMessage(response).contains(Common.sessionExpireMessage) || Util.getMessage(response).equalsIgnoreCase("null")){
            MyService.isSessionExpired=true;
        }
    }
}
