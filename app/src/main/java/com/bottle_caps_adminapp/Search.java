package com.bottle_caps_adminapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import adapters.SearchableAdapter;
import adapters.StoreAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import interfaces.OnStoreSelect;
import model.StoreModel;

/**
 * Created by Ashish.Kumar on 23-05-2018.
 */

public class Search extends Activity implements View.OnClickListener , OnStoreSelect{
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.list_View)
    ListView storeList;
    AppController controller;
    SearchableAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        initializeAll();
    }

    public void initializeAll() {
        ButterKnife.bind(this);
        controller=(AppController) getApplicationContext();
        clear.setOnClickListener(this);
        adapter=new SearchableAdapter(Search.this, controller.getLoginmodel().getStoreList());
        storeList.setAdapter(adapter);
        storeList.setVisibility(View.VISIBLE);
        search.setTypeface(controller.getHeadingFont());
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear:
                search.setText("");
                break;
        }

    }

    @Override
    public void onStoreSelect(StoreModel model) {
        controller.setSelectedStore(model,Integer.toString(controller.getLoginmodel().getUserId()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent();
                setResult(RESULT_OK, in);
                finish();
            }
        });
    }
}
