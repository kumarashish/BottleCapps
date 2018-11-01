package model;

import org.json.JSONObject;

import java.util.logging.Filter;

/**
 * Created by ashish.kumar on 05-06-2018.
 */

public class StoreModel {
    String storeName;
    String storeId="";
    String Location;
    String StoreImageUrl;
    String Address;
    FiltersModel filters;

    public StoreModel(JSONObject jsonObject) {
        try {
            storeName=jsonObject.isNull("StoreName")?"":jsonObject.getString("StoreName");
            storeId=jsonObject.isNull("StoreId")?"":jsonObject.getString("StoreId");
            Location=jsonObject.isNull("Location")?"":jsonObject.getString("Location");
            StoreImageUrl=jsonObject.isNull("StoreImageUrl")?"":jsonObject.getString("StoreImageUrl");
            Address=jsonObject.isNull("Address")?"":jsonObject.getString("Address");
            filters=new FiltersModel(jsonObject.getJSONObject("Filters"));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public FiltersModel getFilters() {
        return filters;
    }

    public String getAddress() {
        return Address;
    }

    public String getLocation() {
        return Location;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreImageUrl() {
        return StoreImageUrl;
    }

    public String getStoreName() {
        return storeName;
    }
}
