package model;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 06-06-2018.
 */

public class OrderModel {
    int OrderId;
    String OrderNo;
    Double TotalAmount;
    String OrderDate;
    String OrderTime;
    int OrderTypeId;
    String OrderType;
    int OrderStatusId;
    String OrderStatus;
    String Email;
    String Phone;
    String TotalAmountString;
    String message="";

    public OrderModel(JSONObject jsonObject) {
        try {
            OrderId = jsonObject.isNull("OrderId") ? -1 : jsonObject.getInt("OrderId");
            OrderNo = jsonObject.isNull("OrderNo") ? "0" : jsonObject.getString("OrderNo");
            TotalAmount = jsonObject.isNull("TotalAmount") ? 0.0 : jsonObject.getDouble("TotalAmount");
            OrderDate = jsonObject.isNull("OrderDate") ? "" : jsonObject.getString("OrderDate");
            OrderTime = jsonObject.isNull("OrderTime") ? "" : jsonObject.getString("OrderTime");
            OrderTypeId = jsonObject.isNull("OrderTypeId") ? -1 : jsonObject.getInt("OrderTypeId");
            OrderType = jsonObject.isNull("OrderType") ? "" : jsonObject.getString("OrderType");
            OrderStatusId = jsonObject.isNull("OrderStatusId") ? -1 : jsonObject.getInt("OrderStatusId");
            OrderStatus = jsonObject.isNull("OrderStatus") ? "" : jsonObject.getString("OrderStatus");
            Email = jsonObject.isNull("Email") ? "" : jsonObject.getString("Email");
            Phone = jsonObject.isNull("Phone") ? "" : jsonObject.getString("Phone");

            TotalAmountString = jsonObject.isNull("TotalAmountString") ? "" : jsonObject.getString("TotalAmountString");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return Email;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public int getOrderId() {
        return OrderId;
    }

    public int getOrderStatusId() {
        return OrderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        OrderStatusId = orderStatusId;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }


    public int getOrderTypeId() {
        return OrderTypeId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public String getOrderType() {
        return OrderType;
    }

    public String getPhone() {
        return Phone;
    }

    public String getTotalAmountString() {
        return TotalAmountString;
    }

    public String getOrderTime() {
        return OrderTime;
    }
}
