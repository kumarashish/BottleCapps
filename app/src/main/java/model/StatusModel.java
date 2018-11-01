package model;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 05-06-2018.
 */

public class StatusModel {
    int OrderStatusId;
    boolean selected;
    String OrderStatusName;

    public StatusModel(JSONObject jsonObject) {
        try {
            OrderStatusId = jsonObject.isNull("OrderStatusId") ? 0 : jsonObject.getInt("OrderStatusId");
            selected = jsonObject.isNull("selected") ? false : jsonObject.getBoolean("selected");
            OrderStatusName = jsonObject.isNull("OrderStatusName") ? "" : jsonObject.getString("OrderStatusName");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public int getOrderStatusId() {
        return OrderStatusId;
    }

    public String getOrderStatusName() {
        return OrderStatusName;
    }

    public boolean isSelected() {
        return selected;
    }
}
