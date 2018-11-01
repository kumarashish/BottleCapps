package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 05-06-2018.
 */

public class LoginModel {
    int userId = 0;
    String sessionId = "";
    int storeCount = 0;
ArrayList<StoreModel> storeList=new ArrayList<>();
    ArrayList<model.OrderCancelType> cancelReasonList=new ArrayList<>();
Boolean PreferredStoreAccess=false;
int PreferredStoreId=0;
    public LoginModel(JSONObject jsonObject) {
        try {
            userId = jsonObject.isNull("UserId") ? 0 : jsonObject.getInt("UserId");
            sessionId = jsonObject.isNull("SessionId") ? "" : jsonObject.getString("SessionId");
            storeCount = jsonObject.isNull("NoOfStores") ? 0 : jsonObject.getInt("NoOfStores");
            PreferredStoreAccess = jsonObject.isNull("PreferredStoreAccess") ? false : jsonObject.getBoolean("PreferredStoreAccess");
                    PreferredStoreId= jsonObject.isNull("PreferredStoreId") ? 0 : jsonObject.getInt("PreferredStoreId");
            if (storeCount > 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("StoreList");
                storeList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    StoreModel model = new StoreModel(jsonObject1);
                    storeList.add(model);
                }
            }
            JSONArray OrderCancelType=jsonObject.getJSONArray("OrderCancelType");
            for(int i=0;i<OrderCancelType.length();i++)
            {
                model.OrderCancelType model=new model.OrderCancelType(OrderCancelType.getJSONObject(i));
                cancelReasonList.add(model);
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public ArrayList<OrderCancelType> getCancelReasonList() {
        return cancelReasonList;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<StoreModel> getStoreList() {
        return storeList;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public Boolean getPreferredStoreAccess() {
        return PreferredStoreAccess;
    }

    public int getPreferredStoreId() {
        return PreferredStoreId;
    }
}
