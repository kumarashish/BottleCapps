package common;

/**
 * Created by ashish.kumar on 04-06-2018.
 */

public class Common {
    public static String baseUrl="https://staging.liquorapps.com/WhiskeyStoreServiceV2/login.svc/";
    public static String loginUrl=baseUrl+"LoginStoreUser";
    public static String orderListUrl=baseUrl+"OrderList";
    public static String orderDetailsUrl=baseUrl+"OrderDetail";
    public static String forgetPasswordUrl=baseUrl+"AdminForgetPassword";
    public static String updateStatusUrl=baseUrl+"OrderStatusUpdate";

    public static String sessionExpireMessage="Invalid Session";

    public static String [] forgetPassword={"StoreId","UserName"};
    public static String [] loginKeys={"StoreId","UserId","UserEmail","Password","DeviceId","DeviceType","SessionId"};
    public static String [] orderlistKeys={"StoreId","UserId","DeviceId","DeviceType","SessionId","PageNumber","PageSize","TypeId","StatusId","SearchText","FromDate","ToDate"};
    public static String [] orderDetailsKeys={"StoreId","UserId","DeviceId","DeviceType","SessionId","OrderId"};
    public static String [] updateStatusKeys={"StoreId","UserId","DeviceId","DeviceType","SessionId","OrderId","CurrentStatusId","TargetStatusId","OrderCancelReasonId"};
}
