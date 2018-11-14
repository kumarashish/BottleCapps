package com.bottle_caps_adminapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import adapters.OrderDetails_adapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import common.SlideButton;
import common.TooltipWindow;
import interfaces.WebApiResponseCallback;
import model.LoginModel;
import model.OrderCancelType;
import model.OrderDetailsModel;
import model.OrderModel;
import model.ProductDetails;
import model.StoreModel;
import utils.Util;

/**
 * Created by Ashish.Kumar on 18-05-2018.
 */

public class OrderDetails extends Activity implements View.OnClickListener, WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.updateOrder)
    Button updateOrder;
    @BindView(R.id.cancelOrder)
    Button cancelOrder;
    @BindView(R.id.dropdown)
    ImageView dropdown;
    @BindView(R.id.userinfo)
    LinearLayout userInfo;
    AppController controller;
    boolean isUserInfoShown = false;
    boolean isViewAllClicked=false;
    @BindView(R.id.orderId)
    common.DetailsCustomTextView orderId;
    @BindView(R.id.amount)
    common.Bold_TextView amount;
    @BindView(R.id.date)
    common.Bold_TextView date;
    @BindView(R.id.time)
    common.Bold_TextView time;
    @BindView(R.id.status)
    common.DetailsCustomTextView status;
    @BindView(R.id.customerName)
    common.DetailsCustomTextView customerName;
    @BindView(R.id.customeraddress)
    common.DetailsCustomTextView customeraddress;
    @BindView(R.id.customeraddress2)
    common.DetailsCustomTextView customeraddress2;
    @BindView(R.id.listView)
    ListView orderItemsList;
    @BindView(R.id.subtotal)
    common.Bold_TextView subtotal;
    @BindView(R.id.delivery)
    common.Bold_TextView delivery;
    @BindView(R.id.tip)
    common.Bold_TextView tip;
    @BindView(R.id.tax)
    common.Bold_TextView tax;
    @BindView(R.id.total)
    common.Bold_TextView total;
    @BindView(R.id.savings)
    common.Bold_TextView savings;
    @BindView(R.id.tot_before_tax)
    common.Bold_TextView tot_before_tax;
    @BindView(R.id.coupon_discount)
    common.Bold_TextView coupon_discount;
    @BindView(R.id.taxLabel)
    common.Bold_TextView taxLabel;
    @BindView(R.id.typeIcon)
    ImageView typeIcon;
    @BindView(R.id.statusIcon)
    ImageView statusIcon;
    @BindView(R.id.email)
    ImageView email;
    @BindView(R.id.call)
    ImageView call;
    @BindView(R.id.emailicon)
    ImageView emailicon;
    @BindView(R.id.callicon)
    ImageView callicon;
    @BindView(R.id.paymentId)
    common.DetailsCustomTextView paymentId;
    @BindView(R.id.specialinstruction)
    common.DetailsCustomTextView  specialinstruction;
    @BindView(R.id.imageButton)
    View viewAllButton;
    @BindView(R.id.subtotalLabel)
    common.Bold_TextView subtotalLabel;
    @BindView(R.id.savingsLabel)
    common.Bold_TextView savingsLabel;
    @BindView(R.id.totalbeforetaxLabel)
    common.Bold_TextView totalbeforetaxLabel;
    @BindView(R.id.coupondiscountLabel)
    common.Bold_TextView coupondiscountLabel;
@BindView(R.id.updateRow)
LinearLayout updateRow;
    @BindView(R.id.specialinstructionLayout)
    LinearLayout specialinstructionLayout;
    @BindView(R.id.delieveryCharge_label)
    common.Bold_TextView  delieveryCharge_label;
    @BindView(R.id.tip_label)
    common.Bold_TextView tip_label;
    @BindView(R.id.payment_layout)
    LinearLayout payment_layout;
    @BindView(R.id.viewAll)
    ImageView viewAll;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.footer)
    LinearLayout footer;
    @BindView(R.id.type)
    common.Bold_TextView typeValue;
    OrderDetails_adapter adapter=null;
    TooltipWindow tipWindow;
    Dialog dialog;
    StoreModel selectedStore;
    OrderDetailsModel orderDetailsModel = null;
    ArrayList<ProductDetails> productList=new ArrayList<>();
    BottomSheetDialog mBottomSheetDialog=null;
    int apiCall=-1;
    int getOrderDetailsApiCall=1,updateStatusApiCall=2,reLogin=3;
    public static OrderModel model=null;
    int index=0;
    ImageView icon;
    common.Bold_TextView text;
    LinearLayout multiView;
    LinearLayout singleView;
    ImageView icon2;
    common.Bold_TextView text2;
    ImageView icon3;
    common.Bold_TextView text3;
    common.Bold_TextView message;
    RelativeLayout layout;
    common.DetailsCustomTextView heading;
    SlideButton button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetails);
        initializeAll();
    }

    public void clearAll() {
        orderId.setText("");
        amount.setText("");
        date.setText("");
        time.setText("");
        status.setText("");
        customerName.setText("");
        customeraddress.setText("");
        customeraddress2.setText("");
        subtotal.setText("");
        delivery.setText("");
        tip.setText("");
        tax.setText("");
        total.setText("");
        specialinstruction.setText("");
        paymentId.setText("");
        savings.setText("");
        coupon_discount.setText("");
        tot_before_tax.setText("");
    }

    public void initializeAll() {
        ButterKnife.bind(this);
        controller = (AppController) getApplicationContext();
        selectedStore = controller.getSelectedStore();
        clearAll();
        back.setOnClickListener(this);
        updateOrder.setOnClickListener(this);
        cancelOrder.setOnClickListener(this);
        dropdown.setOnClickListener(this);
        email.setOnClickListener(this);
        call.setOnClickListener(this);
        emailicon.setOnClickListener(this);
        callicon.setOnClickListener(this);
        cancelOrder.setTypeface(controller.getHeadingFont());
        updateOrder.setTypeface(controller.getHeadingFont());
        savings.setOnClickListener(this);
        viewAllButton.setOnClickListener(this);
        getOrderDetails();

    }

    public void setUpdateStatus(int orderStatusId,int cancelId) {
        if (Util.isNetworkAvailable(OrderDetails.this)) {
            apiCall = updateStatusApiCall;
            dialog = Util.showPogress(OrderDetails.this);
            controller.getApicall().getData(Common.updateStatusUrl, Util.getRequestString(Common.updateStatusKeys, new String[]{selectedStore.getStoreId(), Integer.toString(controller.getLoginmodel().getUserId()), Util.getDeviceID(OrderDetails.this), "A", controller.getLoginmodel().getSessionId(), orderDetailsModel.getOrderId(), Integer.toString(orderDetailsModel.getOrderStatusId()),Integer.toString(orderStatusId),Integer.toString(cancelId)}), this);
        }
    }

    public void  getOrderDetails() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Util.isNetworkAvailable(OrderDetails.this)) {
                    apiCall=getOrderDetailsApiCall;
                    if(dialog!=null)
                    {
                        dialog.cancel();
                    }
                    dialog = Util.showPogress(OrderDetails.this);
                    controller.getApicall().getData(Common.orderDetailsUrl, Util.getRequestString(Common.orderDetailsKeys, new String[]{selectedStore.getStoreId(), Integer.toString(controller.getLoginmodel().getUserId()), Util.getDeviceID(OrderDetails.this), "A", controller.getLoginmodel().getSessionId(), Integer.toString(model.getOrderId())}), OrderDetails.this);
                }
            }
        });

    }

    public void sendEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Detail");
        intent.putExtra(Intent.EXTRA_TEXT, "Type your message");
        intent.setData(Uri.parse("mailto:"+email+"")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
    }
    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            initiateCall(orderDetailsModel.getPhone());
        }else {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                initiateCall(orderDetailsModel.getPhone());
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            initiateCall(orderDetailsModel.getPhone());
        }else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public void initiateCall(String number) {
        if ((number != null) && (number.length() > 0))
        {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
    }else{
            Util.showToast(OrderDetails.this,"Customer Number not available.");
        }
}
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
                break;
            case R.id.updateOrder:
                showStatusAlert();
                break;
            case R.id.cancelOrder:
                showCancelOrderAlert();
                break;
            case R.id.email:
            case    R.id.emailicon:
                sendEmail(orderDetailsModel.getEmailId());
                break;

            case R.id.call:
             case    R.id.callicon:
                 onCallBtnClick();
                break;
            case R.id.dropdown:
                if(isUserInfoShown==true)
                {
                    isUserInfoShown=false;
                    userInfo.setVisibility(View.GONE);
                    dropdown.setImageResource(R.drawable.dropdown);
                }else{
                    isUserInfoShown=true;
                    userInfo.setVisibility(View.VISIBLE);
                    dropdown.setImageResource(R.drawable.dropup);
                }
                break;
            case R.id.savings:
                if (!tipWindow.isTooltipShown()) {
                    tipWindow.showToolTip(view);
                } else {
                    tipWindow.dismissTooltip();
                }
                break;
            case R.id.imageButton:
                    if(isViewAllClicked==true)
                    {

                        isViewAllClicked=false;
                        viewAll.setImageResource(R.drawable.drop_down_black);
                        productList.clear();
                        productList.add(orderDetailsModel.getProductList().get(0));
                        if(orderDetailsModel.getProductList().size()>1)
                        {
                            productList.add(orderDetailsModel.getProductList().get(1));
                        }
                        if(orderDetailsModel.getProductList().size()>2)
                        {
                            productList.add(orderDetailsModel.getProductList().get(2));
                        }

                        setListViewHeightBasedOnChildren(orderItemsList);
                        adapter.notifyDataSetChanged();
                    }else{
                        if(isUserInfoShown==true)
                        {
                            isUserInfoShown=false;
                            userInfo.setVisibility(View.GONE);
                            dropdown.setImageResource(R.drawable.dropdown);
                        }
                        isViewAllClicked=true;
                        productList.clear();
                        productList.addAll(orderDetailsModel.getProductList());
                       setListViewHeightBasedOnChildren(orderItemsList);
                        adapter.notifyDataSetChanged();
                        viewAll.setImageResource(R.drawable.drop_up_black);
                    }
                break;
        }
    }
 public void updateStatus()
 {
     if ((orderDetailsModel.getOrderStatusId() == 3) || (orderDetailsModel.getOrderStatusId() == 4) || (orderDetailsModel.getOrderStatusId() == 6)) {
         layout.setVisibility(View.GONE);
         multiView.setVisibility(View.GONE);
         singleView.setVisibility(View.VISIBLE);
         text3.setText(model.getOrderStatus());
         message.setText(model.getMessage());
         Util.setIcon(OrderDetails.this,model.getOrderTypeId(),model.getOrderStatusId(), icon3, text3);
         if (orderDetailsModel.getOrderStatusId() == 4) {
             heading.setText("");
         } else {
             heading.setText("");
         }
     } else {
         multiView.setVisibility(View.VISIBLE);
         singleView.setVisibility(View.GONE);
         text2.setText(Util.getOrderStatusString(controller.getSelectedStore(),model.getOrderTypeId(),Util.getTargetId(model.getOrderTypeId(),model.getOrderStatusId())));
         Util.setIcon(OrderDetails.this,model.getOrderTypeId(),Util.getTargetId(model.getOrderTypeId(),model.getOrderStatusId()), icon2, text2);
         layout.setVisibility(View.VISIBLE);
     }
      button.setProgress(0);
     Util.setIcon(OrderDetails.this,orderDetailsModel.getOrderTypeId(),orderDetailsModel.getOrderStatusId(), icon, text);
     text.setText(orderDetailsModel.getOrderStatus());
 }

    public void donePopUp() {
        mBottomSheetDialog = new BottomSheetDialog(OrderDetails.this);
        View sheetView = getLayoutInflater().inflate(R.layout.set_status_alert, null);
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);
        heading = (common.DetailsCustomTextView) sheetView.findViewById(R.id.heading);
        icon = (ImageView) sheetView.findViewById(R.id.icon);
        text = (common.Bold_TextView) sheetView.findViewById(R.id.text);
        multiView = (LinearLayout) sheetView.findViewById(R.id.multiView);
        singleView = (LinearLayout) sheetView.findViewById(R.id.singleView);
        icon2 = (ImageView) sheetView.findViewById(R.id.icon2);
        text2 = (common.Bold_TextView) sheetView.findViewById(R.id.text2);
        icon3 = (ImageView) sheetView.findViewById(R.id.icon3);
        text3 = (common.Bold_TextView) sheetView.findViewById(R.id.text3);
        message = (common.Bold_TextView) sheetView.findViewById(R.id.message);
        layout = (RelativeLayout) sheetView.findViewById(R.id.lSlideButton);
        button = (SlideButton) sheetView.findViewById(R.id.swipe);
        layout.setVisibility(View.GONE);
        multiView.setVisibility(View.GONE);
        singleView.setVisibility(View.VISIBLE);
        text3.setText("Done");
        message.setText(model.getMessage());
        icon3.setImageResource(R.drawable.status_complete_icon);
        heading.setText("");
        button.setVisibility(View.GONE);
        text3.setTextColor(getResources().getColor(R.color.green));
        Button close = (Button) sheetView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.cancel();
            }
        });
        FrameLayout bottomSheet = (FrameLayout) mBottomSheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.drawable.alert_bg);
        mBottomSheetDialog.show();
    }

    public void showStatusAlert() {
        mBottomSheetDialog = new BottomSheetDialog(OrderDetails.this);
        View sheetView = getLayoutInflater().inflate(R.layout.set_status_alert, null);
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);
        heading = (common.DetailsCustomTextView) sheetView.findViewById(R.id.heading);
        icon = (ImageView) sheetView.findViewById(R.id.icon);
        text = (common.Bold_TextView) sheetView.findViewById(R.id.text);
        multiView = (LinearLayout) sheetView.findViewById(R.id.multiView);
        singleView = (LinearLayout) sheetView.findViewById(R.id.singleView);
        icon2 = (ImageView) sheetView.findViewById(R.id.icon2);
        text2 = (common.Bold_TextView) sheetView.findViewById(R.id.text2);
        icon3 = (ImageView) sheetView.findViewById(R.id.icon3);
        text3 = (common.Bold_TextView) sheetView.findViewById(R.id.text3);
        message = (common.Bold_TextView) sheetView.findViewById(R.id.message);
        layout = (RelativeLayout) sheetView.findViewById(R.id.lSlideButton);
        if ((orderDetailsModel.getOrderStatusId() == 3) || (orderDetailsModel.getOrderStatusId() == 4) || (orderDetailsModel.getOrderStatusId() == 6)) {
            layout.setVisibility(View.GONE);
            multiView.setVisibility(View.GONE);
            singleView.setVisibility(View.VISIBLE);
            text3.setText(model.getOrderStatus());
            message.setText(model.getMessage());
            Util.setIcon(OrderDetails.this, model.getOrderTypeId(), model.getOrderStatusId(), icon3, text3);
            if (orderDetailsModel.getOrderStatusId() == 4) {
                heading.setText("");
            } else {
                heading.setText("");
            }
        } else {
            multiView.setVisibility(View.VISIBLE);
            singleView.setVisibility(View.GONE);
            text2.setText(Util.getOrderStatusString(controller.getSelectedStore(), model.getOrderTypeId(), Util.getTargetId(model.getOrderTypeId(), model.getOrderStatusId())));
            Util.setIcon(OrderDetails.this, model.getOrderTypeId(), Util.getTargetId(model.getOrderTypeId(), model.getOrderStatusId()), icon2, text2);
            layout.setVisibility(View.VISIBLE);
        }
        Util.setIcon(OrderDetails.this, orderDetailsModel.getOrderTypeId(), orderDetailsModel.getOrderStatusId(), icon, text);
        text.setText(orderDetailsModel.getOrderStatus());
        button = (SlideButton) sheetView.findViewById(R.id.swipe);
        mBottomSheetDialog.setCancelable(false);
        Button close = (Button) sheetView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.cancel();
            }
        });
        button.setSlideButtonListener(new SlideButton.SlideButtonListener() {
            @Override
            public void handleSlide() {
                button.setSaveEnabled(true);
                setUpdateStatus(Util.getTargetId(orderDetailsModel.getOrderTypeId(), orderDetailsModel.getOrderStatusId()), 0);
            }
        });
        FrameLayout bottomSheet = (FrameLayout) mBottomSheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.drawable.alert_bg);
        mBottomSheetDialog.show();
    }

    public void showCancelOrderAlert() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.cancel_order_popup, null);
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);
        RadioGroup radioGroup=(RadioGroup) sheetView.findViewById(R.id.radiogp);
        for (int i = 0; i < controller.getLoginmodel().getCancelReasonList().size(); i++) {
            OrderCancelType model= controller.getLoginmodel().getCancelReasonList().get(i);
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(model.getOrderCancelReasonId());
            rdbtn.setText(model.getOrderCancelReason());
            if (Util.isTablet(OrderDetails.this)) {
                rdbtn.setTextSize(18);
            } else {
                rdbtn.setTextSize(12);
            }
            rdbtn.setTextColor(getResources().getColor(R.color.filterby));
            radioGroup.addView(rdbtn);
        }
        Button close=(Button) sheetView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.cancel();
            }
        });
       final SlideButton button = (SlideButton) sheetView.findViewById(R.id.swipe);
        mBottomSheetDialog.setCancelable(false);

        button.setSlideButtonListener(new SlideButton.SlideButtonListener() {
            @Override
            public void handleSlide() {
                button.setSaveEnabled(true);
                setUpdateStatus(4, index);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                index = i;
                Log.d("id :", "onCheckedChanged:"+i);

            }
        });
        radioGroup.check(controller.getLoginmodel().getCancelReasonList().get(0).getOrderCancelReasonId());
        FrameLayout bottomSheet = (FrameLayout)mBottomSheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.drawable.alert_bg);
        mBottomSheetDialog.show();
    }

    @Override
    public void onSucess(final String value) {

        if (Util.getStatus(value) == true) {
            if (apiCall == updateStatusApiCall) {
                int id = Util.getTargetStatusId(value);
                orderDetailsModel.setOrderStatusId(id);
                orderDetailsModel.setOrderStatus(Util.getOrderStatusString(controller.getSelectedStore(), orderDetailsModel.getOrderTypeId(), id));
                model.setOrderStatusId(id);
                model.setOrderStatus(Util.getOrderStatusString(controller.getSelectedStore(), orderDetailsModel.getOrderTypeId(), id));
                model.setMessage(Util.getMessage(value));
                if(mBottomSheetDialog!=null)
                {
                    mBottomSheetDialog.cancel();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                        donePopUp();
                    }
                });

            } else if(apiCall==getOrderDetailsApiCall) {
                JSONObject jsonObject = Util.getJsonObject(value);
                orderDetailsModel = new OrderDetailsModel(jsonObject);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        updateUI();
                    }
                });

            }else if(apiCall==reLogin)
            { Util.showToast(OrderDetails.this,"Refresh");
                updateCredentials(value);
                getOrderDetails();
            }


        }else{
            if (Util.getMessage(value).contains(Common.sessionExpireMessage)||Util.getMessage(value).equalsIgnoreCase("null")) {
                if (dialog != null) {
                    dialog.cancel();
                }
                if(controller.getPrefManager().getRememberId().length()>0) {
                    reLogin();
                }else{
                    controller.logout();
                    Util.Logout(OrderDetails.this);
                    Util.showToast(OrderDetails.this, Util.getMessage(value));
                }
            }else{
                Util.showToast(OrderDetails.this,Util.getMessage(value));
            }

            if (dialog != null) {
                dialog.cancel();
            }
        }
    }
    public void updateCredentials(String value)
    {
        final LoginModel model = new LoginModel(Util.getJsonObject(value));
        controller.setLogin(true);
        controller.setLoginmodel(model);
        String selectedstore=controller.getPrefManager().getSelectedStore(Integer.toString(model.getUserId()));
        if(selectedstore.length()>0)
        {
            Gson gson = new Gson();
            StoreModel  selectedStore = gson.fromJson(selectedstore, StoreModel.class);
            controller.setSelectedStore(selectedStore,Integer.toString(model.getUserId()));
        }
    }
    public void reLogin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (controller.getPrefManager().getRememberId().length() > 0) {
                    apiCall = reLogin;
                    dialog = Util.showPogress(OrderDetails.this);
                    Util.showToast(OrderDetails.this, "Refreshing session token...");
                    controller.getApicall().getData(Common.loginUrl, Util.getRequestString(Common.loginKeys, new String[]{"0", "0", controller.getPrefManager().getRememberId().toString(), controller.getPrefManager().getRememberPassword().toString(), Util.getDeviceID(OrderDetails.this), "A", ""}), OrderDetails.this);
                }
            }
        });

    }

    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        Util.showToast(OrderDetails.this,Util.getMessage(value));
    }

    public void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (orderDetailsModel != null) {
                    if(orderDetailsModel.getOrderTypeId()==1)
                    {
                        dropdown.setVisibility(View.GONE);
                        callicon.setVisibility(View.VISIBLE);
                        emailicon.setVisibility(View.VISIBLE);
                    }else{
                        dropdown.setVisibility(View.VISIBLE);
                        callicon.setVisibility(View.GONE);
                        emailicon.setVisibility(View.INVISIBLE);
                    }
                    if ((orderDetailsModel.getOrderStatusId() == 3) || (orderDetailsModel.getOrderStatusId() == 4) || (orderDetailsModel.getOrderStatusId() == 6)) {
                        updateRow.setVisibility(View.GONE);
                    }

                    if(orderDetailsModel.getSpecialIntruction().length()==0)
                    {
                        specialinstructionLayout.setVisibility(View.GONE);
                    }else {
                        specialinstruction.setText(orderDetailsModel.getSpecialIntruction());
                        specialinstructionLayout.setVisibility(View.VISIBLE);
                    }
                    if(orderDetailsModel.getPaymentConfirmationNo().length()==0)
                    {
                        payment_layout.setVisibility(View.GONE);
                    }else{
                        paymentId.setText(orderDetailsModel.getPaymentConfirmationNo());
                        payment_layout.setVisibility(View.VISIBLE);
                    }

                    tipWindow = new TooltipWindow(OrderDetails.this,new String[]{orderDetailsModel.getClubSaving(),orderDetailsModel.getSaleSaving(),orderDetailsModel.getCaseDiscount()});
                    tot_before_tax.setText(orderDetailsModel.getTotalBeforeTax());
                    orderId.setText("("+orderDetailsModel.getOrderNo()+")");
                    amount.setText(orderDetailsModel.getOrderTotal());
                    date.setText(orderDetailsModel.getOrderDate());
                    time.setText(orderDetailsModel.getOrderTime() );
                    status.setText(orderDetailsModel.getOrderStatus());
                    customerName.setText(orderDetailsModel.getCustomerFirstName() + " " + orderDetailsModel.getCustomerLastName());
                   if(orderDetailsModel.isCancelable()==false){
                       cancelOrder.setVisibility(View.INVISIBLE);
                   }else{
                       cancelOrder.setVisibility(View.VISIBLE);
                   }
                    if( customerName.getText().toString().length()<3)
                    {
                        customerName.setText("** Name Not Available");
                        customerName.setTextColor(getResources().getColor(R.color.grey));
                    }else {
                        customerName.setTextColor(getResources().getColor(R.color.red));
                    }
                    customeraddress.setText(orderDetailsModel.getAddress());
                    customeraddress2.setText("");
                    if (orderDetailsModel.getSubTotal().length() > 0) {
                        subtotal.setText(orderDetailsModel.getSubTotal());
                        subtotalLabel.setVisibility(View.VISIBLE);
                        subtotal.setVisibility(View.VISIBLE);
                    }
                    if (orderDetailsModel.getTotalSaving().length() > 0) {
                        savingsLabel.setVisibility(View.VISIBLE);
                        savings.setVisibility(View.VISIBLE);
                        savings.setText(orderDetailsModel.getTotalSaving());
                    }
                    if (orderDetailsModel.getTotalBeforeTax().length() > 0) {
                        totalbeforetaxLabel.setVisibility(View.VISIBLE);
                      tot_before_tax.setVisibility(View.VISIBLE);
                        tot_before_tax.setText(orderDetailsModel.getTotalBeforeTax());
                    }
                    if (orderDetailsModel.getCouponDiscount().length() > 0) {
                        coupondiscountLabel.setVisibility(View.VISIBLE);
                       coupon_discount.setVisibility(View.VISIBLE);
                        coupon_discount.setText(orderDetailsModel.getCouponDiscount());
                    }
                    if (orderDetailsModel.getDeliveryCharge().length() > 0) {
                        delivery.setText(orderDetailsModel.getDeliveryCharge());
                        delieveryCharge_label.setVisibility(View.VISIBLE);
                        delivery.setVisibility(View.VISIBLE);
                    }
                    if (orderDetailsModel.getTax().length() > 0) {
                        tax.setText(orderDetailsModel.getTax());
                        taxLabel.setVisibility(View.VISIBLE);
                        tax.setVisibility(View.VISIBLE);
                    }
                    if (orderDetailsModel.getTipsForDriver().length() > 0) {
                        tip.setText(orderDetailsModel.getTipsForDriver());
                        tip_label.setVisibility(View.VISIBLE);
                        tip.setVisibility(View.VISIBLE);
                    }
                    total.setText(orderDetailsModel.getOrderTotal());
                    typeValue.setText(orderDetailsModel.getOrderType());
                    if (orderDetailsModel.getOrderType().equalsIgnoreCase("Delivery")) {
                        typeIcon.setImageResource(R.drawable.type_delivery_icon);

                    } else {
                        typeIcon.setImageResource(R.drawable.type_pickup_icon);
                    }


                      Util.setStatusIcon( statusIcon,orderDetailsModel.getOrderStatusId());

                }
                productList.addAll(orderDetailsModel.getProductList());
                if(orderDetailsModel.getProductList().size()>3)
                {   isViewAllClicked=true;
                    viewAll.setImageResource(R.drawable.drop_up_black);
                    viewAll.setVisibility(View.VISIBLE);
                }else{
                    viewAll.setVisibility(View.GONE);
                }
                adapter=new OrderDetails_adapter(OrderDetails.this,productList);
                orderItemsList.setAdapter(adapter);
                setListViewHeightBasedOnChildren(orderItemsList);
                if (dialog != null) {
                    dialog.cancel();
                }
                footer.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.VISIBLE);

            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();
            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }




}
