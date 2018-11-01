package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 05-06-2018.
 */

public class FiltersModel {
    ArrayList<StatusModel> delivery = new ArrayList<>();
    ArrayList<StatusModel> pickup = new ArrayList<>();

    public FiltersModel(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("Type");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                int orderType = jsonObject1.isNull("OrderTypeId") ? 0 : jsonObject1.getInt("OrderTypeId");
                JSONArray jsonArray2 = jsonObject1.getJSONArray("Status");
                for (int j = 0; j < jsonArray2.length(); j++) {
                    JSONObject status = jsonArray2.getJSONObject(j);
                    StatusModel model = new StatusModel(status);
                    switch (orderType) {
                        case 1:
                            pickup.add(model);
                            break;
                        case 2:
                            delivery.add(model);
                            break;
                    }
                }

            }
        } catch (Exception ex) {
        }
    }

    public ArrayList<StatusModel> getDelivery() {
        return delivery;
    }

    public ArrayList<StatusModel> getPickup() {
        return pickup;
    }
}
