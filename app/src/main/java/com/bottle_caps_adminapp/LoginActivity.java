package com.bottle_caps_adminapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import model.LoginModel;
import model.StoreModel;
import utils.Util;

/**
 * Created by Ashish.Kumar on 17-05-2018.
 */


public class LoginActivity extends Activity implements View.OnClickListener, WebApiResponseCallback{
    @BindView(R.id.login_btn)
    Button login;
    @BindView(R.id.forgetPassword)
    ImageView forgetPassword;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    AppController controller;
    Dialog dialog;
    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        setContentView(R.layout.login);
        initializeAll();
    }


    public void initializeAll() {
        ButterKnife.bind(this);
        controller = (AppController) getApplicationContext();
        login.setOnClickListener(this);
        login.setTypeface(controller.getHeadingFont());
        emailId.setTypeface(controller.getHeadingFont());
        password.setTypeface(controller.getHeadingFont());
        checkbox.setTypeface(controller.getHeadingFont());
        checkbox.setChecked(true);
        forgetPassword.setOnClickListener(this);
        emailId.setText(controller.getPrefManager().getRememberId());
        password.setText(controller.getPrefManager().getRememberPassword());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.forgetPassword:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.login_btn:
               if( isFieldValidated())
               {
                   dialog= Util.showPogress(LoginActivity.this);
                   controller.getApicall().getData(Common.loginUrl,Util.getRequestString(Common.loginKeys,new String[]{"0","0",emailId.getText().toString(),password.getText().toString(),Util.getDeviceID(LoginActivity.this),"A",""}),this);
               }

                break;
        }

    }

    public boolean isFieldValidated() {
        boolean status = false;
        if (controller.getValidation().isEmailIdValid(emailId)==true) {
            if(password.getText().length()>2){
                status = true;
            }else{
                Util.showToast(LoginActivity.this,"Password is required");
            }

        }
        return status;
    }

    public boolean isStoreSelected(LoginModel model) {
        boolean status = false;
        for (int i = 0; i < model.getStoreList().size(); i++) {
            if (model.getStoreList().get(i).getStoreId().equalsIgnoreCase(Integer.toString(model.getPreferredStoreId()))) {
                status = true;
                controller.setSelectedStore(model.getStoreList().get(i),Integer.toString(model.getUserId()));
                break;
            }
        }
        return status;
    }
    @Override
    public void onSucess(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.getStatus(value)) {
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


            if (checkbox.isChecked()) {
                controller.getPrefManager().setRememberId(emailId.getText().toString(),password.getText().toString());
            }else{
                controller.getPrefManager().setRememberId("","");
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

if(model!=null) {

    if ((isStoreSelected(model) == true) ||(controller.getPrefManager().getSelectedStore(Integer.toString(model.getUserId())).length()>1)) {
        startActivity(new Intent(LoginActivity.this, DashBoard.class));
    } else {
        startActivity(new Intent(LoginActivity.this, SelectStoreActivity.class));
    }
}else{
    startActivity(new Intent(LoginActivity.this, SelectStoreActivity.class));
}
                    finish();
                }
            });

        }else{
            Util.showToast(LoginActivity.this,Util.getMessage( value));
        }

    }

    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }
        Util.showToast(LoginActivity.this,value);
    }


}
