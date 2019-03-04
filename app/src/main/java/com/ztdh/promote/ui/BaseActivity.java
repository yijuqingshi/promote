package com.ztdh.promote.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ztdh.promote.PromoteApp;

public class BaseActivity extends AppCompatActivity {

    protected Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new AlertDialog.Builder(this).create();
        PromoteApp.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PromoteApp.removeActivity(this);
    }
}
