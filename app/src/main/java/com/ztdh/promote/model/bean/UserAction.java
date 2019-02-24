package com.ztdh.promote.model.bean;

import android.content.Intent;

public class UserAction {

    private String name;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String id_user_status;

    private String action;

    private Intent intent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_user_status() {
        return id_user_status;
    }

    public void setId_user_status(String id_user_status) {
        this.id_user_status = id_user_status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
