package model;

import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Honey Singh on 6/24/2018.
 */

public class OrderCancelType {
  int OrderCancelReasonId;
  String OrderCancelReason;
public OrderCancelType(JSONObject job)
{
    try{
        OrderCancelReasonId=job.getInt("OrderCancelReasonId");
        OrderCancelReason=job.getString("OrderCancelReason");
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
}

    public int getOrderCancelReasonId() {
        return OrderCancelReasonId;
    }

    public String getOrderCancelReason() {
        return OrderCancelReason;
    }
}
