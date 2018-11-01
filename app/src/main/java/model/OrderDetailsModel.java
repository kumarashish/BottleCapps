package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 07-06-2018.
 */

public class OrderDetailsModel {
    String OrderId;
    String Message;
    String SessionId;
    int StoreId;
    int UserId;
    String OrderNo;
    String OrderDate,OrderTime;
    int OrderTypeId;
    String OrderType;
    int OrderStatusId;
    String OrderStatus;
    String CustomerFirstName;
    String CustomerLastName;
    String Address;
    String EmailId;
    String Phone;
    String PaymentConfirmationNo;
    String SpecialIntruction;
    String SubTotal;
    String TotalBeforeTax;
    String CouponDiscount;
    String DeliveryCharge;
    String TipsForDriver;
    String Tax;
    String OrderTotal;
    String ClubSaving;
    String SaleSaving;
    String CaseDiscount;
    String TotalSaving;
    boolean IsCancelable=true;

    ArrayList<ProductDetails> productList = new ArrayList<>();
   public OrderDetailsModel(JSONObject jsonObject)
   {try {
       this.OrderId=jsonObject.isNull("OrderId")?"":jsonObject.getString("OrderId");
       this.Message=jsonObject.isNull("Message")?"":jsonObject.getString("Message");
       this.SessionId=jsonObject.isNull("SessionId")?"":jsonObject.getString("SessionId");
       this.StoreId=jsonObject.isNull("StoreId")?0:jsonObject.getInt("StoreId");
       this.UserId=jsonObject.isNull("UserId")?0:jsonObject.getInt("UserId");
       this.OrderNo=jsonObject.isNull("OrderNo")?"":jsonObject.getString("OrderNo");
       this.OrderDate=jsonObject.isNull("OrderDate")?"":jsonObject.getString("OrderDate");
       this.OrderTime=jsonObject.isNull("OrderTime")?"":jsonObject.getString("OrderTime");
       this.OrderTypeId=jsonObject.isNull("OrderTypeId")?0:jsonObject.getInt("OrderTypeId");
       this.OrderType=jsonObject.isNull("OrderType")?"":jsonObject.getString("OrderType");
       this.OrderStatusId=jsonObject.isNull("OrderStatusId")?0:jsonObject.getInt("OrderStatusId");
       this.OrderStatus=jsonObject.isNull("OrderStatus")?"":jsonObject.getString("OrderStatus");
       this.CustomerFirstName=jsonObject.isNull("CustomerFirstName")?"Not Available":jsonObject.getString("CustomerFirstName");
       this.CustomerLastName=jsonObject.isNull("CustomerLastName")?"Not Available":jsonObject.getString("CustomerLastName");
       this.Address=jsonObject.isNull("Address")?"":jsonObject.getString("Address");
       this.EmailId=jsonObject.isNull("EmailId")?"":jsonObject.getString("EmailId");
       this.Phone=jsonObject.isNull("Phone")?"":jsonObject.getString("Phone");
       this.PaymentConfirmationNo=jsonObject.isNull("PaymentConfirmationNo")?"":jsonObject.getString("PaymentConfirmationNo");
       this.SpecialIntruction=jsonObject.isNull("SpecialIntruction")?"":jsonObject.getString("SpecialIntruction");
       this.SubTotal=jsonObject.isNull("SubTotal")?"":jsonObject.getString("SubTotal");
       this.TotalBeforeTax=jsonObject.isNull("TotalBeforeTax")?"":jsonObject.getString("TotalBeforeTax");
       this.CouponDiscount=jsonObject.isNull("CouponDiscount")?"":jsonObject.getString("CouponDiscount");
       this.DeliveryCharge=jsonObject.isNull("DeliveryCharge")?"":jsonObject.getString("DeliveryCharge");
       this.TipsForDriver=jsonObject.isNull("TipsForDriver")?"":jsonObject.getString("TipsForDriver");
       this.Tax=jsonObject.isNull("Tax")?"":jsonObject.getString("Tax");
       this.OrderTotal=jsonObject.isNull("OrderTotal")?"":jsonObject.getString("OrderTotal");
       this.IsCancelable=jsonObject.isNull("IsCancelable")?true:jsonObject.getBoolean("IsCancelable");
       JSONArray jsonArray=jsonObject.getJSONArray("ProductList");
       JSONObject saving=jsonObject.getJSONObject("Savings");

       productList.clear();
       for(int i=0;i<jsonArray.length();i++)
       {
           ProductDetails model=new ProductDetails(jsonArray.getJSONObject(i));
           productList.add(model);
       }
       ClubSaving = saving.isNull("ClubSaving") ? "" : saving.getString("ClubSaving");
       SaleSaving = saving.isNull("SaleSaving") ? "" : saving.getString("SaleSaving");
       CaseDiscount = saving.isNull("CaseDiscount") ? "" : saving.getString("CaseDiscount");
       TotalSaving = saving.isNull("TotalSaving") ? "" : saving.getString("TotalSaving");
   }catch (Exception ex)
   {
       ex.fillInStackTrace();
   }
   }

    public boolean isCancelable() {
        return IsCancelable;
    }

    public void setOrderStatusId(int orderStatusId) {
        OrderStatusId = orderStatusId;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }
    public String getPhone() {
        return Phone;
    }

    public String getOrderType() {
        return OrderType;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public int getOrderTypeId() {
        return OrderTypeId;
    }

    public int getOrderStatusId() {
        return OrderStatusId;
    }

    public String getAddress() {
        return Address;
    }

    public String getSessionId() {
        return SessionId;
    }

    public int getUserId() {
        return UserId;
    }

    public String getMessage() {
        return Message;
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getTax() {
        return Tax;
    }

    public ArrayList<ProductDetails> getProductList() {
        return productList;
    }

    public int getStoreId() {
        return StoreId;
    }

    public String getCouponDiscount() {
        return CouponDiscount;
    }

    public String getCustomerFirstName() {
        return CustomerFirstName;
    }

    public String getCustomerLastName() {
        return CustomerLastName;
    }

    public String getDeliveryCharge() {
        return DeliveryCharge;
    }

    public String getEmailId() {
        return EmailId;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public String getPaymentConfirmationNo() {
        return PaymentConfirmationNo;
    }

    public String getSpecialIntruction() {
        return SpecialIntruction;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public String getTipsForDriver() {
        return TipsForDriver;
    }

    public String getTotalBeforeTax() {
        return TotalBeforeTax;
    }

    public String getCaseDiscount() {
        return CaseDiscount;
    }

    public String getClubSaving() {
        return ClubSaving;
    }

    public String getSaleSaving() {
        return SaleSaving;
    }

    public String getTotalSaving() {
        return TotalSaving;
    }
}
