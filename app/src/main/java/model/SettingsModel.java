package model;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 15-06-2018.
 */

public class SettingsModel {
    int type=0;
   ArrayList<Integer> statusId=new ArrayList<>();
    SettingsModel model=null;
String startDate="";
String endDate="";
    public SettingsModel() {
        model = this;
        startDate="";
        endDate="";
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public void updateStatus(int id,boolean status) {
        if (statusId.contains(id)) {
            if(status==false) {
                statusId.remove(statusId.indexOf(id));
            }
        } else {
            statusId.add(id);
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public  ArrayList<Integer>  getStatus() {

        return statusId;
    }
    public void clearAll()
    {
        statusId.clear();
    }
}
