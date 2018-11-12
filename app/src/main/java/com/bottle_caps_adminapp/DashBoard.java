package com.bottle_caps_adminapp;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import adapters.OrderAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import common.SlideButton;
import interfaces.ItemClickCallBack;
import interfaces.WebApiResponseCallback;
import model.LoginModel;
import model.OrderModel;
import model.SettingsModel;
import model.StoreModel;
import service.MyService;
import service.MyReceiver;
import utils.Util;


public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener, ItemClickCallBack, WebApiResponseCallback,SwipeRefreshLayout.OnRefreshListener  {
    ActionBarDrawerToggle toggle;

    @BindView(R.id.logout)
    View logout;
    @BindView(R.id.switchstore)
    View switchstore;
    @BindView(R.id.close)
    ImageView close;
    AppController controller;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.appDashboard)
    View dashBoard;
    RelativeLayout noOrderLayout;
    common.DetailsCustomTextView no_order;
    common.Bold_TextView orderHeading;
    ImageView  noOrderIcon;
    ImageView filter;
    View contentDashboard;
    ListView orderList;
    EditText search;
    public static ArrayList<OrderModel> ordersList=new ArrayList<>();
    Dialog dialog;
    StoreModel selectedStore=null;
    int count=1;
    ArrayList<CheckBox> checkboxlist=new ArrayList<>();
    public static SettingsModel model;
    public static  OrderAdapter adapter=null;
    boolean isApiCalled=false;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    int apiCall=0;
    int searchItem=1,getList=2,addMore=3,applyFilter=4,updateStatus=5,swipeRefresh=6,reLogin=7;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private int year1, month1, day1;
    int datetype=1;
    int totalorderCount=0;
    common.DetailsCustomTextView startDateVale ;
    common.DetailsCustomTextView endDateVale;
    int selectedPosition=-1;
    BottomSheetDialog mBottomSheetDialog=null;
    private SwipeRefreshLayout swipeRefreshLayout;
    String message="";
    common.DetailsCustomTextView heading;
    LinearLayout multiView;
    LinearLayout singleView;
    ImageView icon;
    common.Bold_TextView text;
    ImageView icon2;
    common.Bold_TextView text2;
    ImageView icon3;
    common.Bold_TextView text3;
    common.Bold_TextView messageTv;
    RelativeLayout layout;
    SlideButton button;
    public static final String FILTER_ACTION_KEY = "any_key";
    MyReceiver myReceiver;
    Intent intentService;
    boolean isServiceRunning=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeAll();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 998) {
DatePickerDialog dialog=new DatePickerDialog(this, myDateListener, year, month, day);
dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
            return dialog;
        }else{
            DatePickerDialog dialog=new DatePickerDialog(this, myDateListener, year1, month1, day1);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return  dialog;
        }

    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    setDate(arg1, arg2+1, arg3);
                }
            };
    private void setDate(int year, int month, int day) {
        switch (datetype)
        {
            case 1:
                model.setStartDate(month+"/"+day+"/"+year);
                startDateVale.setText(month+"/"+day+"/"+year);
                this.year=year;
                this. month=month;
                this.day=day;
                break;
            case 2:
                model.setEndDate(month+"/"+day+"/"+year);
                endDateVale.setText(month+"/"+day+"/"+year);
                year1=year;
                month1=month;
                day1=day;
                break;
        }

    }

    public void startService() {

        if (isServiceRunning != true) {
            intentService = new Intent(DashBoard.this, MyService.class);
            intentService.putExtra("message", message);
            startService(intentService);
            isServiceRunning = true;
        }
    }
    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (isServiceRunning) {
            stopService(intentService);
        }
        if (myReceiver != null) {

            unregisterReceiver(myReceiver);
        }
        super.onDestroy();
    }

    public void initializeAll()
    {
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        model=controller.getSettingsModel();
        selectedStore=controller.getSelectedStore();
        setSupportActionBar(toolbar);
        contentDashboard=(View) dashBoard.findViewById(R.id.contentDashboard);
        search=(EditText) contentDashboard.findViewById(R.id.search);
        swipeRefreshLayout = (SwipeRefreshLayout)contentDashboard. findViewById(R.id.swiperefresh);
        noOrderLayout=(RelativeLayout)contentDashboard.findViewById(R.id.noOrderLayout);
         no_order=( common.DetailsCustomTextView)contentDashboard.findViewById(R.id.no_order);
        orderHeading=( common.Bold_TextView)contentDashboard.findViewById(R.id.orderHeading);
        noOrderIcon=(ImageView)contentDashboard.findViewById(R.id.noOrderIcon);
        search.setImeOptions(search.getImeOptions()| EditorInfo.IME_ACTION_DONE);
        orderList=(ListView) contentDashboard.findViewById(R.id.list_View);
        filter=(ImageView) contentDashboard.findViewById(R.id.filter);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.rsz_menu_icon);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        close.setOnClickListener(this);
        logout.setOnClickListener(this);
        switchstore.setOnClickListener(this);
        filter.setOnClickListener(this);
        apiCall=getList;
        swipeRefreshLayout.setOnRefreshListener(this);
        getList();
        //orderList.setAdapter(new OrderAdapter(DashBoard.this,item));
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (search.getText().length() > 0) {
                        search();
                    } else {
                        apiCall=getList;
                       getList();
                    }
                }
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.START);
            }
        });
    orderList.setOnScrollListener(new AbsListView.OnScrollListener() {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                swipeRefreshLayout.setEnabled(true);
            } else {
                swipeRefreshLayout.setEnabled(false);
            }
            // You cat determine first and last visible items here
            final int lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
            if(lastVisibleItem==totalItemCount-1)
            {
                if(adapter!=null) {
                if((isApiCalled==false)&&(ordersList.size()>10)&&(ordersList.size()<totalorderCount)) {
                    isApiCalled=true;
                    count = count + 1;
                    apiCall=addMore;
                    getList();
                }
            }

            }
        }

        @Override
        public void onScrollStateChanged(AbsListView arg0, int arg1) {
            // TODO Auto-generated method stub
        }
    });
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar = Calendar.getInstance();
        year1=calendar.get(Calendar.YEAR);
        month1= calendar.get(Calendar.MONTH);
        day1=calendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    protected void onStart() {
       registerReceiver(myReceiver);
        super.onStart();
    }

    private void registerReceiver(MyReceiver myReceiver) {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }



    public void getList() {
        selectedStore = controller.getSelectedStore();
        if (Util.isNetworkAvailable(DashBoard.this)) {
            if(apiCall!=swipeRefresh) {
                if (dialog != null) {
                    dialog.cancel();
                }
                dialog = Util.showPogress(DashBoard.this);
            }
           String statusId = "0" ;
            if (model == null) {
                model = new SettingsModel();
            }
            if (model.getStatus().size() != 0) {
                for (int i = 0; i < model.getStatus().size(); i++) {
                    if (i == 0) {
                        statusId = Integer.toString(model.getStatus().get(0));
                    } else {
                        statusId += "," + model.getStatus().get(i);
                    }
                }
               // statusId = Integer.toString(model.getStatus().get(0));
            }
            search.setText("");
            controller.getApicall().getData(Common.orderListUrl, Util.getRequestString(Common.orderlistKeys, new String[]{selectedStore.getStoreId(), Integer.toString(controller.getLoginmodel().getUserId()), Util.getDeviceID(DashBoard.this), "A", controller.getLoginmodel().getSessionId(), Integer.toString(count), "50", Integer.toString(model.getType()), statusId, search.getText().toString(),model.getStartDate(),model.getEndDate()}), this);
        }
    }

    public void setUpdateStatus() {
        OrderModel model = ordersList.get(selectedPosition);
        apiCall = updateStatus;
        if (Util.isNetworkAvailable(DashBoard.this)) {
            dialog = Util.showPogress(DashBoard.this);
            controller.getApicall().getData(Common.updateStatusUrl, Util.getRequestString(Common.updateStatusKeys, new String[]{selectedStore.getStoreId(), Integer.toString(controller.getLoginmodel().getUserId()), Util.getDeviceID(DashBoard.this), "A", controller.getLoginmodel().getSessionId(), Integer.toString(model.getOrderId()), Integer.toString(model.getOrderStatusId()), Integer.toString(Util.getTargetId(model.getOrderTypeId(), model.getOrderStatusId())), "0"}), this);
        }
    }


    public void search() {
        apiCall=searchItem;
        selectedStore = controller.getSelectedStore();
        if (Util.isNetworkAvailable(DashBoard.this)) {
            dialog = Util.showPogress(DashBoard.this);
            int statusId = 0;
            isApiCalled=true;
            if (search.getText().length() > 0) {
                model = new SettingsModel();
            }
            controller.getApicall().getData(Common.orderListUrl, Util.getRequestString(Common.orderlistKeys, new String[]{selectedStore.getStoreId(), Integer.toString(controller.getLoginmodel().getUserId()), Util.getDeviceID(DashBoard.this), "A", controller.getLoginmodel().getSessionId(), "0", "100", "0", "0", search.getText().toString().toUpperCase(),"",""}), this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.close:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.filter:
                showAlert();
                break;
            case R.id.logout:
                controller.logout();
                Util.Logout(DashBoard.this);
                break;
            case R.id.switchstore:

                drawer.closeDrawer(GravityCompat.START);
                Intent in=new Intent(DashBoard.this,SelectStoreActivity.class);
                in.putExtra("isSwitchStoreRequested",true);
              startActivityForResult(in,1);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = null;
                    controller.updateSettingModel(new SettingsModel());
                    model = new SettingsModel();
                    count = 1;
                    apiCall=getList;
                    getList();
                }
            });

        } else if ((requestCode == 3) && (resultCode == RESULT_OK)) {
            if (adapter != null) {
                OrderModel model = OrderDetails.model;
                ordersList.set(selectedPosition, model);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }


    @Override
    public void OnItemClick(OrderModel model,int selectedPosition) {
        OrderDetails.model=model;
        this.selectedPosition=selectedPosition;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent in=new Intent(DashBoard.this,OrderDetails.class);
                startActivityForResult(in,3);
              //  overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }

    @Override
    public void OnStatusClicked(final OrderModel model,int selectedPosition) {
        this.selectedPosition=selectedPosition;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showStatusAlert(model);
            }
        });
    }


    public void showdonePopUp(String message) {
        layout.setVisibility(View.GONE);
        multiView.setVisibility(View.GONE);
        singleView.setVisibility(View.VISIBLE);
        text3.setText("Done");
        messageTv.setText(message);
        icon3.setImageResource(R.drawable.status_complete_icon);
        heading.setText("");
        button.setVisibility(View.GONE);
        text3.setTextColor(getResources().getColor(R.color.green));
    }

    public void showStatusAlert(OrderModel model) {
        mBottomSheetDialog = new BottomSheetDialog(DashBoard.this);
        View sheetView = getLayoutInflater().inflate(R.layout.set_status_alert, null);
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);
        heading = (common.DetailsCustomTextView) sheetView.findViewById(R.id.heading);
        multiView = (LinearLayout) sheetView.findViewById(R.id.multiView);
        singleView = (LinearLayout) sheetView.findViewById(R.id.singleView);
        icon = (ImageView) sheetView.findViewById(R.id.icon);
        text = (common.Bold_TextView) sheetView.findViewById(R.id.text);
        icon2 = (ImageView) sheetView.findViewById(R.id.icon2);
        text2 = (common.Bold_TextView) sheetView.findViewById(R.id.text2);
        icon3 = (ImageView) sheetView.findViewById(R.id.icon3);
        text3 = (common.Bold_TextView) sheetView.findViewById(R.id.text3);
        messageTv = (common.Bold_TextView) sheetView.findViewById(R.id.message);
        layout = (RelativeLayout) sheetView.findViewById(R.id.lSlideButton);
        button = (SlideButton) sheetView.findViewById(R.id.swipe);
        if ((model.getOrderStatusId() == 3) || (model.getOrderStatusId() == 4) || (model.getOrderStatusId() == 6)) {
            layout.setVisibility(View.GONE);
            multiView.setVisibility(View.GONE);
            singleView.setVisibility(View.VISIBLE);
            text3.setText(model.getOrderStatus());
            messageTv.setText(model.getMessage());
            Util.setIcon(DashBoard.this, model.getOrderTypeId(), model.getOrderStatusId(), icon3, text3);
            if (model.getOrderStatusId() == 4) {
                heading.setText("");
            } else {
                heading.setText("");

            }
        } else {
            multiView.setVisibility(View.VISIBLE);
            singleView.setVisibility(View.GONE);
            text2.setText(Util.getOrderStatusString(controller.getSelectedStore(), model.getOrderTypeId(), Util.getTargetId(model.getOrderTypeId(), model.getOrderStatusId())));
            Util.setIcon(DashBoard.this, model.getOrderTypeId(), Util.getTargetId(model.getOrderTypeId(), model.getOrderStatusId()), icon2, text2);
            layout.setVisibility(View.VISIBLE);
        }
        Util.setIcon(DashBoard.this, model.getOrderTypeId(), model.getOrderStatusId(), icon, text);
        text.setText(model.getOrderStatus());

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
                setUpdateStatus();

            }
        });
        FrameLayout bottomSheet = (FrameLayout) mBottomSheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.drawable.alert_bg);
        mBottomSheetDialog.show();
    }

    public void showAlert() {
        model=controller.getSettingsModel();
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(DashBoard.this);
        View sheetView = getLayoutInflater().inflate(R.layout.rounded_corner_filter_popup, null);
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);
        final common.DetailsCustomTextView status = (common.DetailsCustomTextView) sheetView.findViewById(R.id.status);
        final common.DetailsCustomTextView type = (common.DetailsCustomTextView) sheetView.findViewById(R.id.type);
        final common.DetailsCustomTextView date = (common.DetailsCustomTextView) sheetView.findViewById(R.id.date);
         startDateVale = (common.DetailsCustomTextView) sheetView.findViewById(R.id.startDateValue);
        endDateVale = (common.DetailsCustomTextView) sheetView.findViewById(R.id.endDateValue);
        final View startDateDate=(View)sheetView.findViewById(R.id.startDate);
        final View endDate=(View)sheetView.findViewById(R.id.endDate);
        final LinearLayout status_value = (LinearLayout) sheetView.findViewById(R.id.status_value);
        final LinearLayout type_value = (LinearLayout) sheetView.findViewById(R.id.type_value);
        final LinearLayout date_value = (LinearLayout) sheetView.findViewById(R.id.date_value);
        final CheckBox pickupType=(CheckBox) sheetView.findViewById(R.id.pickup);
        final CheckBox deliveryType=(CheckBox) sheetView.findViewById(R.id.delivery);
         final View saveSettings=(View)  sheetView.findViewById(R.id.saveSettings);
        final View clearSettings=(View)  sheetView.findViewById(R.id.clearSettings);
        pickupType.setTypeface(controller.getHeadingFont());
        deliveryType.setTypeface(controller.getHeadingFont());
        Button close = (Button) sheetView.findViewById(R.id.close);
        updateType( pickupType, deliveryType);
        updateStatus(status_value);
        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCall=applyFilter;
                controller.updateSettingModel(model);
                mBottomSheetDialog.cancel();
                count=1;
             getList();
            }
        });
        clearSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCall=applyFilter;
                model=null;
                controller.updateSettingModel(new SettingsModel());
                mBottomSheetDialog.cancel();
                getList();
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status_value.setVisibility(View.VISIBLE);
                status.setBackgroundDrawable(getResources().getDrawable(R.drawable.alert_selected));
                type_value.setVisibility(View.GONE);
                date_value.setVisibility(View.GONE);
                type.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                date.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                type.setTextColor(getResources().getColor(R.color.black_color));
                date.setTextColor(getResources().getColor(R.color.black_color));
                updateStatus(status_value);
            }
        });
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_value.setVisibility(View.VISIBLE);
                type.setBackgroundDrawable(getResources().getDrawable(R.drawable.alert_selected));
                status_value.setVisibility(View.GONE);
                date_value.setVisibility(View.GONE);
                status.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                date.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                status.setTextColor(getResources().getColor(R.color.black_color));
                date.setTextColor(getResources().getColor(R.color.black_color));
                updateStatus(status_value);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_value.setVisibility(View.VISIBLE);
                date.setBackgroundDrawable(getResources().getDrawable(R.drawable.alert_selected));
                status_value.setVisibility(View.GONE);
                type_value.setVisibility(View.GONE);
                status.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                type.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                status.setTextColor(getResources().getColor(R.color.black_color));
                type.setTextColor(getResources().getColor(R.color.black_color));
                updateStatus(status_value);
            }
        });
        deliveryType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    deliveryType.setTextColor(getResources().getColor(R.color.red));
                    model.setType(2);
                    if (pickupType.isChecked()) {
                        model.setType(0);
                    }
                    controller.updateSettingModel(model);
                } else {
                    deliveryType.setTextColor(getResources().getColor(R.color.black_color));
                    if (pickupType.isChecked()) {
                        model.setType(1);
                        controller.updateSettingModel(model);
                    }else{
                        model.setType(0);
                    }

                }
            }
        });
        pickupType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true) {
                    pickupType.setTextColor(getResources().getColor(R.color.red));
                    model.setType(1);

                    if( deliveryType.isChecked())
                    {
                        model.setType(0);
                    }
                    controller.updateSettingModel(model);
                }else{
                  pickupType.setTextColor(getResources().getColor(R.color.black_color));
                    if(     deliveryType.isChecked())
                    {
                        model.setType(2);
                        controller.updateSettingModel(model);
                    }else{
                        model.setType(0);
                    }
                }
            }
        });

        startDateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=1;
                showDialog(998);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datetype=2;
                showDialog(999);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.cancel();
            }
        });
        FrameLayout bottomSheet = (FrameLayout)mBottomSheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.drawable.alert_bg);
        mBottomSheetDialog.show();
    }

    public void updateType(CheckBox pickup, CheckBox delivery) {
        switch (model.getType()) {
            case 0:
//                pickup.setTextColor(getResources().getColor(R.color.red));
//                delivery.setTextColor(getResources().getColor(R.color.red));
//                pickup.setChecked(true);
//                delivery.setChecked(true);
                break;
            case 1:
                pickup.setTextColor(getResources().getColor(R.color.red));
                delivery.setTextColor(getResources().getColor(R.color.filterby));
                pickup.setChecked(true);
                break;
            case 2:
                delivery.setChecked(true);
                pickup.setTextColor(getResources().getColor(R.color.filterby));
                delivery.setTextColor(getResources().getColor(R.color.red));
                break;
        }
    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Util.getStatus(value) == true) {
                    JSONObject jsonObject=null;
                    if(apiCall==reLogin)
                    {
                        updateCredentials(value);
                        apiCall=getList;
                        getList();
                    }else {
                            if (apiCall == updateStatus) {
                            int id = Util.getTargetStatusId(value);
                            OrderModel model = ordersList.get(selectedPosition);
                            model.setOrderStatusId(id);
                            model.setOrderStatus(Util.getOrderStatusString(selectedStore, model.getOrderTypeId(), id));
                            model.setMessage(Util.getMessage(value));
                            ordersList.set(selectedPosition, model);
                            //Util.showToast(DashBoard.this, Util.getMessage(value));
                            showdonePopUp(model.getMessage());
                        } else {
                            jsonObject = Util.getJsonObject(value);

                            if (count == 1) {
                                ordersList.clear();
                            }
                            try {
                                totalorderCount = jsonObject.getInt("TotalOrders");
                                JSONArray orders = jsonObject.getJSONArray("Orders");

                                if ((apiCall == searchItem) || (apiCall == getList) || (apiCall == swipeRefresh)) {
                                    ordersList.clear();
                                }
                                for (int i = 0; i < orders.length(); i++) {
                                    OrderModel model = new OrderModel(orders.getJSONObject(i));
                                    ordersList.add(model);
                                }

                            } catch (Exception ex) {
                                ex.fillInStackTrace();
                            }
                        }
                        if (ordersList.size() > 0) {

                            if (adapter != null) {
                                adapter.notifyDataSetChanged();

                            } else {
                                adapter = new OrderAdapter(DashBoard.this, ordersList);
                                orderList.setAdapter(adapter);
                            }

                            if (dialog != null) {
                                dialog.cancel();
                            }
                            if (apiCall == swipeRefresh) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            orderList.setVisibility(View.VISIBLE);
                            noOrderLayout.setVisibility(View.GONE);
                            isApiCalled = false;
                            startService();
                        } else {

                            if (apiCall == getList) {
                                no_order.setText("You have not received any order.");
                                noOrderIcon.setImageResource(R.drawable.no_orders);
                                orderHeading.setText("No Orders!");
                            } else if (apiCall == searchItem) {
                                noOrderIcon.setImageResource(R.drawable.no_results);
                                no_order.setText("Sorry, there are no result for this search,Please try another phrase.");
                                orderHeading.setText("No Results!");
                            } else if (apiCall == applyFilter) {
                                no_order.setText("Sorry, no result found with selected filters.");
                                noOrderIcon.setImageResource(R.drawable.no_results);
                                orderHeading.setText("No Results!");
                            }
                            orderList.setVisibility(View.GONE);
                            noOrderLayout.setVisibility(View.VISIBLE);
                            if (dialog != null) {
                                dialog.cancel();
                            }
                            if (apiCall == swipeRefresh) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                } else {
                    if(apiCall==swipeRefresh)
                    {
                        swipeRefreshLayout.setRefreshing(false);
                        Util.showToast(DashBoard.this, Util.getMessage(value));
                        isApiCalled = false;
                        if (dialog != null) {
                            dialog.cancel();
                        }
                    }

                    if (Util.getMessage(value).contains(Common.sessionExpireMessage) || Util.getMessage(value).equalsIgnoreCase("null")) {
                        if (dialog != null) {
                            dialog.cancel();
                        }
                        reLogin();
                    }



                }
            }
        });
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

    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
Util.showToast(DashBoard.this,Util.getMessage(value));
    }


    public void updateStatus(LinearLayout status_value) {
        checkboxlist.clear();
        status_value.removeAllViews();
        if (selectedStore != null) {
            startDateVale.setText(model.getStartDate());
            endDateVale.setText(model.getEndDate());
            if (model.getType() == 0) {
                for (int i = 0; i < selectedStore.getFilters().getPickup().size(); i++) {
                   final  AppCompatCheckBox checkbox = new AppCompatCheckBox(new ContextThemeWrapper(DashBoard.this, R.style.CheckboxStyle));
                    checkbox.setId(selectedStore.getFilters().getPickup().get(i).getOrderStatusId());
                    checkbox.setText(selectedStore.getFilters().getPickup().get(i).getOrderStatusName());
                    checkbox.setTypeface(controller.getHeadingFont());

                    if(Util.isTablet(DashBoard.this))
                    {
                        checkbox.setTextSize(18);
                    }else {
                        checkbox.setTextSize(12);
                    }


//                    if (selectedStore.getFilters().getPickup().get(i).isSelected()) {
//                        checkbox.setChecked(true);
//                    }
                    if (model.getStatus().contains(checkbox.getId())) {
                        checkbox.setChecked(true);
                        checkbox.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        checkbox.setTextColor(getResources().getColor(R.color.filterby));
                    }
                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b==true)
                            {
                                checkbox.setTextColor(getResources().getColor(R.color.red));
                            }else{
                                checkbox.setTextColor(getResources().getColor(R.color.filterby));
                            }
                            model.updateStatus(compoundButton.getId(), b);
                        }
                    });
                    if (!checkboxlist.contains(checkbox)) {
                        checkboxlist.add(checkbox);
                        status_value.addView(checkbox);
                    }
                }
                for (int i = 0; i < selectedStore.getFilters().getDelivery().size(); i++) {
                    final  AppCompatCheckBox checkbox = new AppCompatCheckBox(new ContextThemeWrapper(DashBoard.this, R.style.CheckboxStyle));
                    checkbox.setId(selectedStore.getFilters().getDelivery().get(i).getOrderStatusId());
                    checkbox.setText(selectedStore.getFilters().getDelivery().get(i).getOrderStatusName());
                    checkbox.setTypeface(controller.getHeadingFont());
                    if(Util.isTablet(DashBoard.this))
                    {
                        checkbox.setTextSize(18);
                    }else {
                        checkbox.setTextSize(12);
                    }


                    if (model.getStatus().contains(checkbox.getId())) {
                        checkbox.setChecked(true);
                        checkbox.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        checkbox.setTextColor(getResources().getColor(R.color.filterby));
                    }
//                    if (selectedStore.getFilters().getDelivery().get(i).isSelected()) {
//                        checkbox.setChecked(true);
//                    }
                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b==true)
                            {
                                checkbox.setTextColor(getResources().getColor(R.color.red));
                            }else{
                                checkbox.setTextColor(getResources().getColor(R.color.filterby));
                            }
                            model.updateStatus(compoundButton.getId(), b);
                        }
                    });
                    if (!isCheckBoxAlreadyAdded(checkbox.getId())) {
                        checkboxlist.add(checkbox);
                        status_value.addView(checkbox);
                    }

                }
            } else if (controller.getSettingsModel().getType() == 1) {
                for (int i = 0; i < selectedStore.getFilters().getPickup().size(); i++) {
                    final  AppCompatCheckBox checkbox = new AppCompatCheckBox(new ContextThemeWrapper(DashBoard.this, R.style.CheckboxStyle));
                    checkbox.setId(selectedStore.getFilters().getPickup().get(i).getOrderStatusId());
                    checkbox.setText(selectedStore.getFilters().getPickup().get(i).getOrderStatusName());
                    checkbox.setTypeface(controller.getHeadingFont());
                    if(Util.isTablet(DashBoard.this))
                    {
                        checkbox.setTextSize(18);
                    }else {
                        checkbox.setTextSize(12);
                    }
                    if (model.getStatus().contains(checkbox.getId())) {
                        checkbox.setChecked(true);
                        checkbox.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        checkbox.setTextColor(getResources().getColor(R.color.filterby));
                    }
//                    if (selectedStore.getFilters().getPickup().get(i).isSelected()) {
//                        checkbox.setChecked(true);
//                    }
                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b==true)
                            {
                                checkbox.setTextColor(getResources().getColor(R.color.red));
                            }else{
                                checkbox.setTextColor(getResources().getColor(R.color.filterby));
                            }
                            model.updateStatus(compoundButton.getId(), b);
                        }
                    });
                    if (!isCheckBoxAlreadyAdded(checkbox.getId())) {
                        checkboxlist.add(checkbox);
                        status_value.addView(checkbox);
                    }
                }
            } else {
                for (int i = 0; i < selectedStore.getFilters().getDelivery().size(); i++) {
                    final  AppCompatCheckBox checkbox = new AppCompatCheckBox(new ContextThemeWrapper(DashBoard.this, R.style.CheckboxStyle));
                    checkbox.setId(selectedStore.getFilters().getDelivery().get(i).getOrderStatusId());
                    checkbox.setText(selectedStore.getFilters().getDelivery().get(i).getOrderStatusName());
                    checkbox.setTypeface(controller.getHeadingFont());
                    if(Util.isTablet(DashBoard.this))
                    {
                        checkbox.setTextSize(18);
                    }else {
                        checkbox.setTextSize(12);
                    }
                    if (model.getStatus().contains(checkbox.getId())) {
                        checkbox.setChecked(true);
                        checkbox.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        checkbox.setTextColor(getResources().getColor(R.color.filterby));
                    }

                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b==true)
                            {
                                checkbox.setTextColor(getResources().getColor(R.color.red));
                            }else{
                                checkbox.setTextColor(getResources().getColor(R.color.filterby));
                            }
                            model.updateStatus(compoundButton.getId(), b);
                        }
                    });
                    if (isCheckBoxAlreadyAdded(checkbox.getId()) == false) {
                        checkboxlist.add(checkbox);
                        status_value.addView(checkbox);
                    }
                }
            }
        }
    }
    public void reLogin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (controller.getPrefManager().getRememberId().length() > 0) {
                    isApiCalled=true;
                    apiCall = reLogin;
                    dialog = Util.showPogress(DashBoard.this);
                    Util.showToast(DashBoard.this, "Refreshing session token...");
                    controller.getApicall().getData(Common.loginUrl, Util.getRequestString(Common.loginKeys, new String[]{"0", "0", controller.getPrefManager().getRememberId().toString(), controller.getPrefManager().getRememberPassword().toString(), Util.getDeviceID(DashBoard.this), "A", ""}), DashBoard.this);
                }
            }
        });

    }
    public boolean isCheckBoxAlreadyAdded(int checkboxId) {
        boolean result = false;
        for (int i = 0; i < checkboxlist.size(); i++) {
            if (checkboxlist.get(i).getId() == checkboxId) {
                result = true;
                break;
            }
        }
        return result;

    }

    @Override
    public void onRefresh() {
        apiCall=swipeRefresh;
            getList();

    }

}
