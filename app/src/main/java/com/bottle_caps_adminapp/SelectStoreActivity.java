package com.bottle_caps_adminapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import adapters.StoreAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.TooltipWindow;
import interfaces.OnStoreSelect;
import model.LoginModel;
import model.StoreModel;
import utils.Util;

/**
 * Created by Ashish.Kumar on 17-05-2018.
 */

public class SelectStoreActivity extends Activity implements OnStoreSelect, View.OnClickListener {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.back)
            ImageView back;
    @BindView(R.id.heading)
    common.DetailsCustomTextView heading;
    ArrayList<String> item = new ArrayList<>();
    AppController controller;
boolean isSwitchStoreRequested=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_store);
        initializeAll();
    }

    public void initializeAll() {
        ButterKnife.bind(this);
         controller=(AppController)getApplicationContext();
        search.setOnClickListener(this);
       isSwitchStoreRequested=getIntent().getBooleanExtra("isSwitchStoreRequested",false);
       if(isSwitchStoreRequested==true)
       {
           heading.setText("Switch Store");
           back.setVisibility(View.VISIBLE);
           back.setOnClickListener(this);
       }
        listView.setAdapter(new StoreAdapter(SelectStoreActivity.this, controller.getLoginmodel().getStoreList()));
    }


    @Override
    public void onStoreSelect(StoreModel model) {

        controller.setSelectedStore(model,Integer.toString(controller.getLoginmodel().getUserId()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isSwitchStoreRequested==false) {
                    startActivity(new Intent(SelectStoreActivity.this, DashBoard.class));
                    finish();
                }else
                {
                    Intent in=new Intent();
                    setResult(RESULT_OK, in);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            Intent in = new Intent();
            setResult(RESULT_OK, in);
            finish();
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                startActivityForResult(new Intent(SelectStoreActivity.this, Search.class),1);
                break;
            case R.id.back:
                onBackPressed();
                break;

        }

    }
}
