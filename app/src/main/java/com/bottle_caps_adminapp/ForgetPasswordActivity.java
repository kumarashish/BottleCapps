package com.bottle_caps_adminapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import model.SettingsModel;
import utils.Util;
import utils.Validation;

/**
 * Created by Ashish.Kumar on 17-05-2018.
 */

public class ForgetPasswordActivity extends Activity implements View.OnClickListener, WebApiResponseCallback {
    @BindView(R.id.submit_btn)
    Button submit;
    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.back)
    ImageView back;
    AppController controller;
    Dialog dialog;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        initializeAll();
    }

    public void initializeAll() {
        ButterKnife.bind(this);
        controller = (AppController) getApplicationContext();
        submit.setOnClickListener(this);
        submit.setTypeface(controller.getHeadingFont());
        emailId.setTypeface(controller.getHeadingFont());
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit_btn:
                if(controller.getValidation().isEmailIdValid(emailId))
                {
                    callApi();
                }
                break;
        }
    }
    public void callApi() {

        if (Util.isNetworkAvailable(ForgetPasswordActivity.this)) {
            dialog = Util.showPogress(ForgetPasswordActivity.this);
            controller.getApicall().getData(Common.forgetPasswordUrl, Util.getRequestString(Common.forgetPassword, new String[]{"0", emailId.getText().toString()}), this);
        }
    }

    @Override
    public void onSucess(String value) {
        if (Util.getStatus(value) == true) {
            Util.showToast(ForgetPasswordActivity.this, Util.getMessage(value));
            finish();
        }else{
            Util.showToast(ForgetPasswordActivity.this, Util.getMessage(value));
        }
        if (dialog != null) {
            dialog.cancel();
        }

    }

    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        Util.showToast(ForgetPasswordActivity.this,Util.getMessage(value));
    }
}
